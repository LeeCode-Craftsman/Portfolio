package com.leemra.battleship.classic;

import com.leemra.battleship.IController;
import com.leemra.battleship.core.BattleShipCore;
import com.leemra.battleship.core.Computer;
import com.leemra.battleship.core.Launcher;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 * KtorGameController Class. Controls player-side logic, tells Computer player to do stuff, handles user input, and controls the GUI.
 * See also, battleship-classic.fxml
 *
 * @author Liam Emra
 * @see Computer
 * @see ClassicBattleShip
 * @see BattleShipCore
 */
public class ClassicGameController implements IController {
    Application app;
    BattleShipCore core;
    /**
     * An object of the Computer class, runs all the Computer player's logic
     *
     * @see Computer
     */
    Computer computer;
    /**
     * Keeps track of whether to place another ship or to let the user fire
     */
    int placedShips = 0;
    /**
     * A Button[][] that contains the display board. Can represent either player's board depending on the situation.
     */
    Button[][] tiles = new Button[10][10];

    /**
     * The pane the tiles array is printed onto
     * Defined in battleship-classic.fxml
     */
    @FXML
    GridPane gamePane;
    /**
     * The label that informs the user of who's turn it is, and who (if anyone) has won
     * Defined in battleship-classic.fxml
     */
    @FXML
    Label turnLabel;
    /**
     * The label that tells the user what to do (or what is going on)
     * Defined in battleship-classic.fxml
     */
    @FXML
    Label instructionLabel;

    // These two methods are for GUI control

    /**
     * This method sets up the Button array, adds every Button to the gamePane, and sets up the Computer object
     *
     * @see Computer
     */
    public void initialize() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Button();
                tiles[i][j].setPrefHeight(50);
                tiles[i][j].setPrefWidth(50);
                int finalI = i;
                int finalJ = j;
                tiles[i][j].setOnAction(actionEvent -> handleClick(finalI, finalJ));
                gamePane.add(tiles[i][j], i, j);

            }
        }
        core = new BattleShipCore();
        computer = new Computer(core);
    }

    /**
     * <b>While the game is not over, this method will work.</b> <br/>
     * If there are no ships placed, it will have the user and Computer place a Destroyer <br/>
     * If there is one ship placed, it will have the user and Computer place a Submarine <br/>
     * If there are two ships placed, it will have the user and Computer place a Cruiser <br/>
     * If all the ships are placed, it will have the user and Computer fire <br/>
     * One side may fire until a miss, then it is the other player's turn.<br/>
     * Relies on the placeDestroyer(), Computer.setDestroyer(), placeCruiser, Computer.setCruiser(), placeSub, Computer.setSub, fire(), display(), isWinner(), and playerMissedSequence()
     *
     * @param i int - the x value of the clicked cell
     * @param j int - the y value of the clicked cell
     * @see Computer
     */
    public void handleClick(int i, int j) {
        if (!core.isGameOver()) {
            switch (placedShips) {
                case 0 -> {
                    if (!core.placeDestroyer(i, j, true)) return;
                    computer.setDestroyer();
                    placedShips++;
                    instructionLabel.setText("Place Your Submarine");
                    display(true);
                }
                case 1 -> {
                    if (!core.placeSub(i, j, getDirection(true), true)) return;
                    computer.setSub();
                    placedShips++;
                    instructionLabel.setText("Place Your Cruiser");
                    display(true);
                }
                case 2 -> {
                    if (!core.placeCruiser(i, j, getDirection(false), true)) return;
                    computer.setCruiser();
                    placedShips++;
                    instructionLabel.setText("FIRE!");
                    display(false);
                }
                case 3 -> {
                    boolean hit = core.fire(i, j, true);
                    display(false);
                    if (core.isWinner(true)) onWin(true);
                    if (hit) return;
                    Timeline timeline = playerMissedSequence();
                    timeline.setOnFinished(e -> {
                        if (core.isWinner(false)) onWin(false);
                    });
                    timeline.play();
                }
            }
        }
    }


    // The following are helper methods

    /**
     * <b>Prints a board to the screen.</b><br/>
     * If it is the player's board, it will print the empty tiles (" "), the misses ("*"), the ships ("~"), and the hits ("X").<br/>
     * The computer's board will display ships exactly the same way as blank tiles (" ").
     *
     * @param isPlayerBoard boolean - Is this printing the player's board?
     */
    private void display(boolean isPlayerBoard) {
        if (isPlayerBoard) {
            for (int i = 0; i < core.playerBoard.length; i++) {
                for (int j = 0; j < core.playerBoard[i].length; j++) {
                    switch (core.playerBoard[i][j]) {
                        case 0:
                            tiles[i][j].setText("");
                            break;
                        case 1:
                            tiles[i][j].setText("*");
                            break;
                        case 2:
                            tiles[i][j].setText("~");
                            break;
                        case 3:
                            tiles[i][j].setText("X");
                    }
                }
            }
        } else {
            for (int i = 0; i < core.cpBoard.length; i++) {
                for (int j = 0; j < core.cpBoard[i].length; j++) {
                    switch (core.cpBoard[i][j]) {
                        case 1:
                            tiles[i][j].setText("*");
                            break;
                        case 3:
                            tiles[i][j].setText("X");
                            break;
                        default:
                            tiles[i][j].setText("");
                    }
                }
            }
        }
    }

    private int getDirection(boolean isSub) {
        ChoiceDialog<String> choice;
        if (isSub) {
            choice = new ChoiceDialog<>("Left to Right (\\)", "Left to Right (\\)", "Right to Left (/)");
        } else {
            choice = new ChoiceDialog<>("Vertical (|)", "Vertical (|)", "Horizontal (-)");
        }

        if (choice.showAndWait().isEmpty()) {
            return 3;
        }
        return switch (choice.getSelectedItem()) {
            case "Left to Right (\\)", "Vertical (|)" -> 0;
            case "Right to Left (/)", "Horizontal (-)" -> 1;
            default -> 3;
        };
    }

    private Timeline playerMissedSequence() {
        EventHandler<Event> handler = Event::consume;
        gamePane.addEventFilter(Event.ANY, handler);
        return new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            display(true);
            turnLabel.setText("Computer's Turn");
            instructionLabel.setText("Computer is firing");
        }), new KeyFrame(Duration.seconds(2), e -> {
            while (computer.fire()) {
                display(true);
            }
        }), new KeyFrame(Duration.seconds(4), e -> {
            display(true);
            instructionLabel.setText("Computer has fired");
        }), new KeyFrame(Duration.seconds(6), e -> {
            if (!core.isGameOver()) {
                display(false);
                turnLabel.setText("Your Turn");
                instructionLabel.setText("FIRE!");
                gamePane.removeEventFilter(Event.ANY, handler);
            }
        }));

    }

    private void onWin(boolean isPlayer) {
        display(!isPlayer);
        if (isPlayer) {
            turnLabel.setText("Player Wins!");
            instructionLabel.setText("Congratulations");
        } else {
            turnLabel.setText("Computer Wins!");
            instructionLabel.setText("Too bad...");
        }
    }

    @Override
    public void onStop() {
        Platform.exit();
    }

    @Override
    public Application getApp() {
        return app;
    }

    @Override
    public void setApp(Application app) {
        this.app = app;
        try {
            ((Launcher) app).controller = this;
        } catch (Exception ignored) {
        }
    }
}