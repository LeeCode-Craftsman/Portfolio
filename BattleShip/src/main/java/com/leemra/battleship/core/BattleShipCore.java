package com.leemra.battleship.core;

import java.util.ArrayList;

public class BattleShipCore {
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

    public boolean isGameOver() {
        return gameOver;
    }

    public ArrayList<int[]> list = new ArrayList<>();

    /**
     * Place Destroyer method. Will allow the Destroyer to be placed anywhere -1 &lt; x &lt; 9, and -1 &lt; y &lt; 9
     * No further checks are necessary, as the Destroyer is the first ship to be placed.
     * The cell denoted by x and y is the top-left corner of the Destroyer.
     *
     * @param x        int - the x coordinate for the top-left corner of the Destroyer (-1 &lt; x &lt; 9)
     * @param y        int - the y coordinate for the top-left corner of the Destroyer (-1 &lt; y &lt; 9)
     * @param isPlayer boolean - Should this ship be on the Player's board?
     * @return boolean - was this action successful?
     */
    public boolean placeDestroyer(int x, int y, boolean isPlayer) {
        if (isPlayer) list.clear();

        if (x >= 0 && x < 9 && y >= 0 && y < 9) {
            if (isPlayer) {
                playerBoard[x][y] = 2;
                list.add(new int[]{x, y});
                playerBoard[x + 1][y] = 2;
                list.add(new int[]{x + 1, y});
                playerBoard[x][y + 1] = 2;
                list.add(new int[]{x, y + 1});
                playerBoard[x + 1][y + 1] = 2;
                list.add(new int[]{x + 1, y + 1});
            } else {
                cpBoard[x][y] = 2;
                cpBoard[x + 1][y] = 2;
                cpBoard[x][y + 1] = 2;
                cpBoard[x + 1][y + 1] = 2;
            }
            return true;
        }
        return false;
    }

    /**
     * Place Submarine method. Will allow the Submarine to be placed
     * anywhere where 0 &lt; x &lt; 9, and 0 &lt; y &lt; 9, and none of the cells in the Sub are already occupied
     * The cell denoted by x and y is the middle cell of the Submarine
     *
     * @param x           int - the x coordinate for the middle cell of the Sub (0 &lt; x &lt; 9)
     * @param y           int - the y coordinate for the middle cell of the Sub (0 &lt; y &lt; 9)
     * @param orientation int - Determines the orientation of the Sub - 0 = left-to-right, 1 = right-to-left
     * @param isPlayer    boolean - Should this ship be on the Player's board?
     * @return boolean - was this action successful?
     */
    public boolean placeSub(int x, int y, int orientation, boolean isPlayer) {
        if (isPlayer) list.clear();
        switch (orientation) {
            case 0 -> {
                if ((x > 0 && x < 9) && (y > 0 && y < 9)) {
                    if (isPlayer) {
                        if ((playerBoard[x][y] == 0) && (playerBoard[x + 1][y + 1] == 0) && (playerBoard[x - 1][y - 1] == 0)) {
                            playerBoard[x][y] = 2;
                            list.add(new int[]{x, y});
                            playerBoard[x + 1][y + 1] = 2;
                            list.add(new int[]{x + 1, y + 1});
                            playerBoard[x - 1][y - 1] = 2;
                            list.add(new int[]{x - 1, y - 1});
                            return true;
                        } else {
                            return false;
                        }
                    } else if ((cpBoard[x][y] == 0) && (cpBoard[x + 1][y + 1] == 0) && (cpBoard[x - 1][y - 1] == 0)) {
                        cpBoard[x][y] = 2;
                        cpBoard[x + 1][y + 1] = 2;
                        cpBoard[x - 1][y - 1] = 2;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            case 1 -> {
                if ((x > 0 && x < 9) && (y > 0 && y < 9)) {
                    if (isPlayer) {
                        if ((playerBoard[x][y] == 0) && (playerBoard[x - 1][y + 1] == 0) && (playerBoard[x + 1][y - 1] == 0)) {
                            playerBoard[x][y] = 2;
                            list.add(new int[]{x, y});
                            playerBoard[x - 1][y + 1] = 2;
                            list.add(new int[]{x - 1, y + 1});
                            playerBoard[x + 1][y - 1] = 2;
                            list.add(new int[]{x + 1, y - 1});
                            return true;
                        } else {
                            return false;
                        }
                    } else if ((cpBoard[x][y] == 0) && (cpBoard[x - 1][y + 1] == 0) && (cpBoard[x + 1][y - 1] == 0)) {
                        cpBoard[x][y] = 2;
                        cpBoard[x - 1][y + 1] = 2;
                        cpBoard[x + 1][y - 1] = 2;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Place Cruiser method. Will allow the Cruiser to be placed anywhere where 0 &lt; x &lt; 9,
     * and 0 &lt; y &lt; 9, and none of the cells in the Cruiser are already occupied
     * The cell denoted by x and y is the middle cell of the Cruiser
     *
     * @param x           int - the x coordinate for the middle cell of the Cruiser (0 &lt; x &lt; 9)
     * @param y           int - the y coordinate for the middle cell of the Cruiser (0 &lt; y &lt; 9)
     * @param orientation int - Determines the orientation of the Cruiser - 0 = vertical, 1 = horizontal
     * @param isPlayer    boolean - Should this ship be on the Player's board?
     * @return boolean - was this action successful?
     */
    public boolean placeCruiser(int x, int y, int orientation, boolean isPlayer) {
        if (isPlayer) list.clear();
        switch (orientation) {
            case 0 -> {
                if ((x > 0 && x < 9) && (y > -1 && y < 10)) {
                    if (isPlayer) {
                        if (playerBoard[x][y] != 2 && playerBoard[x - 1][y] != 2 && playerBoard[x + 1][y] != 2) {
                            playerBoard[x][y] = 2;
                            list.add(new int[]{x, y});
                            playerBoard[x - 1][y] = 2;
                            list.add(new int[]{x - 1, y});
                            playerBoard[x + 1][y] = 2;
                            list.add(new int[]{x + 1, y});
                            return true;
                        } else {
                            return false;
                        }
                    }
                    if (cpBoard[x][y] != 2 && cpBoard[x - 1][y] != 2 && cpBoard[x + 1][y] != 2) {
                        cpBoard[x][y] = 2;
                        cpBoard[x - 1][y] = 2;
                        cpBoard[x + 1][y] = 2;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            case 1 -> {
                if ((x > -1 && x < 10) && (y > 0 && y < 9)) {
                    if (isPlayer) {
                        if (playerBoard[x][y] != 2 && playerBoard[x][y - 1] != 2 && playerBoard[x][y + 1] != 2) {
                            playerBoard[x][y] = 2;
                            list.add(new int[]{x, y});
                            playerBoard[x][y - 1] = 2;
                            list.add(new int[]{x, y - 1});
                            playerBoard[x][y + 1] = 2;
                            list.add(new int[]{x, y + 1});
                            return true;
                        } else {
                            return false;
                        }
                    }
                    if ((cpBoard[x][y] != 2 && cpBoard[x][y - 1] != 2 && cpBoard[x][y + 1] != 2)) {
                        cpBoard[x][y] = 2;
                        cpBoard[x][y - 1] = 2;
                        cpBoard[x][y + 1] = 2;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    /**
     *
     * @param x        int - The x coordinate of the attacked cell (-1 &lt; x &lt; 10)
     * @param y        int - The y coordinate of the attacked cell (-1 &lt; y &lt; 10)
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

    public boolean isWinner(boolean isPlayer) {
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
}
