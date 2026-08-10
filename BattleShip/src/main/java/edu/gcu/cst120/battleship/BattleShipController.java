package edu.gcu.cst120.battleship;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

/**
 * BattleShipController Class. Controls player-side logic, tells Computer player to do stuff, handles user input, and controls the GUI.
 * See also, battleship-view.fxml
 * @author Liam Emra
 * @see Computer
 * @see BattleShip
 */
public class BattleShipController {
	/**
	 * An object of the Computer class, runs all the Computer player's logic
	 * @see Computer
	 */
	Computer computer;
	/**
	 * playerBoard represents the Player's board.
	 * 0 = empty, 1 = miss, 2 = occupied, 3 = hit
	 */
	int[][] playerBoard = new int[10][10];
	/**
	 * cpBoard represents the Computer's board.
	 * 0 = empty, 1 = miss, 2 = occupied, 3 = hit
	 */
	int[][] cpBoard = new int[10][10];
	/**
	 * A check to see if the game is over
	 */
	private boolean gameOver = false;
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
	 * Defined in battleship-view.fxml
	 */
	@FXML
	GridPane gamePane;
	/**
	 * The label that informs the user of who's turn it is, and who (if anyone) has won
	 * Defined in battleship-view.fxml
	 */
	@FXML
	Label turnLabel;
	/**
	 * The label that tells the user what to do (or what is going on)
	 * Defined in battleship-view.fxml
	 */
	@FXML
	Label instructionLabel;
	
	// These two methods are for GUI control
	
	/**
	 * This method sets up the Button array, adds every Button to the gamePane, and sets up the Computer object
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
		computer = new Computer(this);
	}
	
	/**
	 * <b>While the game is not over, this method will work.</b> <br/>
	 * If there are no ships placed, it will have the user and Computer place a Destroyer <br/>
	 * If there is one ship placed, it will have the user and Computer place a Submarine <br/>
	 * If there are two ships placed, it will have the user and Computer place a Cruiser <br/>
	 * If all the ships are placed, it will have the user and Computer fire <br/>
	 * One side may fire until a miss, then it is the other player's turn.<br/>
	 * Relies on the placeDestroyer(), Computer.setDestroyer(), placeCruiser, Computer.setCruiser(), placeSub, Computer.setSub, fire(), display(), isWinner(), and playerMissedSequence()
	 * @param i int - the x value of the clicked cell
	 * @param j int - the y value of the clicked cell
	 * @see Computer
	 */
	public void handleClick(int i, int j) {
		if (!gameOver) {
			switch (placedShips) {
				case 0 -> {
					if (!placeDestroyer(i, j, true)) return;
					computer.setDestroyer();
					placedShips++;
					instructionLabel.setText("Place Your Submarine");
					display(true);
				}
				case 1 -> {
					if (!placeSub(i, j, getDirection(true), true)) return;
					computer.setSub();
					placedShips++;
					instructionLabel.setText("Place Your Cruiser");
					display(true);
				}
				case 2 -> {
					if (!placeCruiser(i, j, getDirection(false), true)) return;
					computer.setCruiser();
					placedShips++;
					instructionLabel.setText("FIRE!");
					display(false);
				}
				case 3 -> {
					boolean hit = fire(i, j, true);
					display(false);
					if (isWinner(true)) onWin(true);
					if (hit) return;
					Timeline timeline = playerMissedSequence();
					timeline.setOnFinished(e -> {if (isWinner(false)) onWin(false);});
					timeline.play();
				}
			}
		}
	}
	

	
	// The following are helper methods
	
	/**
	 * Place Destroyer method. Will allow the Destroyer to be placed anywhere -1 &lt; x &lt; 9, and -1 &lt; y &lt; 9
	 * No further checks are necessary, as the Destroyer is the first ship to be placed.
	 * The cell denoted by x and y is the top-left corner of the Destroyer.
	 * @param x int - the x coordinate for the top-left corner of the Destroyer (-1 &lt; x &lt; 9)
	 * @param y int - the y coordinate for the top-left corner of the Destroyer (-1 &lt; y &lt; 9)
	 * @param isPlayer boolean - Should this ship be on the Player's board?
	 * @return boolean - was this action successful?
	 */
	public boolean placeDestroyer(int x, int y, boolean isPlayer) {
		if (x >= 0 && x < 9 && y >= 0 && y < 9) {
			if (isPlayer) {
				playerBoard[x][y] = 2;
				playerBoard[x+1][y] = 2;
				playerBoard[x][y+1] = 2;
				playerBoard[x+1][y+1] = 2;
			} else {
				cpBoard[x][y] = 2;
				cpBoard[x+1][y] = 2;
				cpBoard[x][y+1] = 2;
				cpBoard[x+1][y+1] = 2;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Place Submarine method. Will allow the Submarine to be placed
	 * anywhere where 0 &lt; x &lt; 9, and 0 &lt; y &lt; 9, and none of the cells in the Sub are already occupied
	 * The cell denoted by x and y is the middle cell of the Submarine
	 * @param x int - the x coordinate for the middle cell of the Sub (0 &lt; x &lt; 9)
	 * @param y int - the y coordinate for the middle cell of the Sub (0 &lt; y &lt; 9)
	 * @param orientation int - Determines the orientation of the Sub - 0 = left-to-right, 1 = right-to-left
	 * @param isPlayer boolean - Should this ship be on the Player's board?
	 * @return boolean - was this action successful?
	 */
	public boolean placeSub(int x, int y, int orientation, boolean isPlayer) {
		switch (orientation) {
			case 0 -> {
				if ((x > 0 && x < 9) && (y > 0 && y < 9)) {
					if (isPlayer) {
						if ((playerBoard[x][y] == 0) && (playerBoard[x + 1][y + 1] == 0) && (playerBoard[x - 1][y - 1] == 0)) {
							playerBoard[x][y] = 2;
							playerBoard[x + 1][y + 1] = 2;
							playerBoard[x - 1][y - 1] = 2;
							return true;
						} else {return false;}
					} else if ((cpBoard[x][y] == 0) && (cpBoard[x + 1][y + 1] == 0) && (cpBoard[x - 1][y - 1] == 0)) {
						cpBoard[x][y] = 2;
						cpBoard[x + 1][y + 1] = 2;
						cpBoard[x - 1][y - 1] = 2;
						return true;
					} else {return false;}
				}
			}
			case 1 -> {
				if ((x > 0 && x < 9) && (y > 0 && y < 9)) {
					if (isPlayer) {
						if ((playerBoard[x][y] == 0) && (playerBoard[x - 1][y + 1] == 0) && (playerBoard[x + 1][y - 1] == 0)) {
							playerBoard[x][y] = 2;
							playerBoard[x - 1][y + 1] = 2;
							playerBoard[x + 1][y - 1] = 2;
							return true;
						} else {return false;}
					} else if ((cpBoard[x][y] == 0) && (cpBoard[x - 1][y + 1] == 0) && (cpBoard[x + 1][y - 1] == 0)) {
						cpBoard[x][y] = 2;
						cpBoard[x - 1][y + 1] = 2;
						cpBoard[x + 1][y - 1] = 2;
						return true;
					} else {return false;}
				}
			}
		}
		return false;
	}
	
	/**
	 * Place Cruiser method. Will allow the Cruiser to be placed anywhere where 0 &lt; x &lt; 9,
	 * and 0 &lt; y &lt; 9, and none of the cells in the Cruiser are already occupied
	 * The cell denoted by x and y is the middle cell of the Cruiser
	 * @param x int - the x coordinate for the middle cell of the Cruiser (0 &lt; x &lt; 9)
	 * @param y int - the y coordinate for the middle cell of the Cruiser (0 &lt; y &lt; 9)
	 * @param orientation int - Determines the orientation of the Cruiser - 0 = vertical, 1 = horizontal
	 * @param isPlayer boolean - Should this ship be on the Player's board?
	 * @return boolean - was this action successful?
	 */
	public boolean placeCruiser(int x, int y, int orientation, boolean isPlayer) {
		switch (orientation) {
			case 0 -> {
				if ((x > 0 && x < 9) && (y > -1 && y < 10)) {
					if (isPlayer) {
						if (playerBoard[x][y] != 2 && playerBoard[x - 1][y] != 2 && playerBoard[x + 1][y] != 2) {
							playerBoard[x][y] = 2;
							playerBoard[x - 1][y] = 2;
							playerBoard[x + 1][y] = 2;
							return true;
						} else {return false;}
					}
					if (cpBoard[x][y] != 2 && cpBoard[x - 1][y] != 2 && cpBoard[x + 1][y] != 2) {
						cpBoard[x][y] = 2;
						cpBoard[x - 1][y] = 2;
						cpBoard[x + 1][y] = 2;
						return true;
					} else {return false;}
				}
			}
			case 1 -> {
				if ((x > -1 && x < 10) && (y > 0 && y < 9)) {
					if (isPlayer) {
						if (playerBoard[x][y] != 2 && playerBoard[x][y - 1] != 2 && playerBoard[x][y + 1] != 2) {
							playerBoard[x][y] = 2;
							playerBoard[x][y - 1] = 2;
							playerBoard[x][y + 1] = 2;
							return true;
						} else {return false;}
					}
					if ((cpBoard[x][y] != 2 && cpBoard[x][y - 1] != 2 && cpBoard[x][y + 1] != 2)) {
						cpBoard[x][y] = 2;
						cpBoard[x][y - 1] = 2;
						cpBoard[x][y + 1] = 2;
						return true;
					} else {return false;}
				}
			}
		}
		return false;
	}
	
	/**
	 *
	 * @param x int - The x coordinate of the attacked cell (-1 &lt; x &lt; 10)
	 * @param y int - The y coordinate of the attacked cell (-1 &lt; y &lt; 10)
	 * @param isPlayer boolean - Is the Player making this action?
	 * @return did this action work?
	 */
	public boolean fire(int x, int y, boolean isPlayer) {
		if (isPlayer) {
			if (cpBoard[x][y] == 2 || cpBoard[x][y] == 0) {
				cpBoard[x][y]++;
				return cpBoard[x][y] == 3;
			}
		} else {
			if (playerBoard[x][y] == 2 || playerBoard[x][y] == 0) {
				playerBoard[x][y]++;
				return playerBoard[x][y] == 3;
			}
		}
		return false;
	}
	
	/**
	 * <b>Prints a board to the screen.</b><br/>
	 * If it is the player's board, it will print the empty tiles (" "), the misses ("*"), the ships ("~"), and the hits ("X").<br/>
	 * The computer's board will display ships exactly the same way as blank tiles (" ").
	 * @param isPlayerBoard boolean - Is this printing the player's board?
	 */
	private void display(boolean isPlayerBoard) {
		if (isPlayerBoard) {
			for (int i = 0; i < playerBoard.length; i++) {
				for (int j = 0; j < playerBoard[i].length; j++) {
					switch (playerBoard[i][j]) {
						case 0 :
							tiles[i][j].setText("");
							break;
						case 1 :
							tiles[i][j].setText("*");
							break;
						case 2 :
							tiles[i][j].setText("~");
							break;
						case 3 :
							tiles[i][j].setText("X");
					}
				}
			}
		} else {
			for (int i = 0; i < cpBoard.length; i++) {
				for (int j = 0; j < cpBoard[i].length; j++) {
					switch (cpBoard[i][j]) {
						case 1 :
							tiles[i][j].setText("*");
							break;
						case 3 :
							tiles[i][j].setText("X");
							break;
						default :
							tiles[i][j].setText("");
					}
				}
			}
		}
	}
	
	private int getDirection(boolean isSub) {
		ChoiceDialog<String> choice;
		if (isSub) {
			choice = new ChoiceDialog<>("Left to Right (\\)", "Left to Right (\\)","Right to Left (/)");
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
		return new Timeline(
				new KeyFrame(Duration.seconds(1), e -> {
					display(true);
					turnLabel.setText("Computer's Turn");
					instructionLabel.setText("Computer is firing");}),
				new KeyFrame(Duration.seconds(2), e -> {
					while (computer.fire()){
						display(true);
					}
				}),
				new KeyFrame (Duration.seconds(4), e -> {
					display(true);
					instructionLabel.setText("Computer has fired");
				}),
				new KeyFrame(Duration.seconds(6), e -> {
					if (!gameOver) {
						display(false);
						turnLabel.setText("Your Turn");
						instructionLabel.setText("FIRE!");
						gamePane.removeEventFilter(Event.ANY, handler);
					}
				})
		);
		
	}
	
	private boolean isWinner(boolean isPlayer) {
		if (isPlayer) {
			for (int[] line : cpBoard) {
				for (int cell : line) {
					if (cell == 2) return false;
				}
			}
		} else {
			for (int[] line : playerBoard) {
				for (int cell : line) {
					if (cell == 2) return false;
				}
			}
		}
		gameOver = true;
		return true;
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
}