package com.leemra.battleship.core;

import java.util.ArrayList;

public class BattleShipCore {
    /**
     * playerBoard represents the Player's board.
     * 0 = empty, 1 = miss, 2 = occupied, 3 = hit
     */
    public int[][] playerBoard = new int[10][10];
    /**
     * cpBoard represents the Computer's board.
     * 0 = empty, 1 = miss, 2 = occupied, 3 = hit
     */
    public int[][] cpBoard = new int[10][10];
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
     * @param board    int[][] - the board to change
     * @param isPlayer boolean - Is this the player's board?
     * @return boolean - was this action successful?
     */
    public boolean place2x2(int x, int y, int[][] board, boolean isPlayer) {
        if (isPlayer) list.clear();

        if (x >= 0 && x < board.length - 1 && y >= 0 && y < board[0].length - 1) {
            board[x][y] = 2;
            board[x + 1][y] = 2;
            board[x][y + 1] = 2;
            board[x + 1][y + 1] = 2;
            if (isPlayer) {
                list.add(new int[]{x, y});
                list.add(new int[]{x + 1, y});
                list.add(new int[]{x, y + 1});
                list.add(new int[]{x + 1, y + 1});
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
     * @param board       int[][] - the board to place on
     * @param isPlayer    boolean - Is this the player's board?
     * @return boolean - was this action successful?
     */
    public boolean place1x3(int x, int y, int orientation, int[][] board, boolean isPlayer) {
        int[] xs;
        int[] ys;
        if (isPlayer) list.clear();
        switch (orientation) {
            case 0 -> {
                xs = new int[]{x + 1, x - 1};
                ys = new int[]{y, y};
            }
            case 1 -> {
                xs = new int[]{x, x};
                ys = new int[]{y + 1, y - 1};
            }
            case 2 -> {
                xs = new int[]{x + 1, x - 1};
                ys = new int[]{y + 1, y - 1};
            }
            case 3 -> {
                xs = new int[]{x + 1, x - 1};
                ys = new int[]{y - 1, y + 1};
            }
            default -> {
                xs = new int[]{0, 0};
                ys = new int[]{0,0};
            }
        }
        boolean check = true;
        for (int i = 0; i < 2; i++) {
            if (xs[i] < 0 || xs[i] >= board.length || ys[i] < 0 || ys[i] >= board[0].length) {
                check = false;
                break;
            }
        }
        if (check) {
            if ((board[x][y] == 0) && (board[xs[0]][ys[0]] == 0) && (board[xs[1]][ys[1]] == 0)) {
                board[x][y] = 2;
                board[xs[0]][ys[0]] = 2;
                board[xs[1]][ys[1]] = 2;
                if (isPlayer) {
                    list.add(new int[]{x, y});
                    list.add(new int[]{xs[0], ys[0]});
                    list.add(new int[]{xs[1], ys[1]});
                }
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param x        int - The x coordinate of the attacked cell (-1 &lt; x &lt; 10)
     * @param y        int - The y coordinate of the attacked cell (-1 &lt; y &lt; 10)
     * @param board    int[][] - The board to fire at
     * @return did this action work?
     */
    public boolean fire(int x, int y, int[][] board) {
        if (board[x][y] == 2 || board[x][y] == 0) {
            board[x][y]++;
            return board[x][y] == 3;
        }
        return false;
    }

    public boolean isGameLost(int[][] board) {
        for (int[] line : board) {
            for (int cell : line) {
                if (cell == 2) return false;
            }
        }
        gameOver = true;
        return true;
    }
}
