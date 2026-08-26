package com.leemra.battleship.core;

import java.util.ArrayList;
import java.util.Random;

public class Computer {
    /**
     * A Random object that is used many times in the system
     */
    private final Random rand;
    private final int[] center = {0, 0};
    //Distance, Direction, and Number.
    // direction (index = 1) 0 = left, 1 = up, 2 = right, 3 = down
    private final int[] dDN = {-1, 0, -1};
    private final int[][] sank = {{0, 1}, {2, 3}, {4, 5}, {6, 7}};
    /**
     * Determines whether to fire randomly or pattern-match for a ship
     */
    private boolean tracing = false;
    public ArrayList<int[]> fired = new ArrayList<>();

    BattleShipCore core;

    public Computer(BattleShipCore core) {
        rand = new Random();
        this.core = core;
    }

    /**
     * Main computer-side firing control. There are two major branches in the logic.<br/>
     * If it is tracking a ship, it:<br/>
     * &#9;Takes the knowledge of where the last shot that hit was fired<br/>
     * &#9;It fires at the ship using the logic in fireOnShip()<br/>
     * If it is not tracking a ship, it:<br/>
     * &#9;Fires at a random non-discovered cell<br/>
     * &#9;If it hits a ship, it begins the tracking sequence<br/>
     * If it did <i>not</i> actually shoot, it restarts the algorithm with the current center[0] and center[1] values -
     * which may have been changed in fireOnShips(), and if it has failed to shoot 9 times in a row, it stops tracing.<br/>
     * It also checks to see if it has sunk any of the player's ships, in which case, it resets the sank array
     *
     * @return boolean - whether it hit a ship
     * @see ClassicGameController
     */
    public boolean fire() {
        fired.clear();
        int x, y;
        boolean[] hit;
        int iterations = 0;
        do {
            if (tracing) {
                x = center[0];
                y = center[1];
                hit = fireOnShip(x, y);
            } else {
                do {
                    x = rand.nextInt(0, 10);
                    y = rand.nextInt(0, 10);
                } while ((core.playerBoard[x][y] == 1) || (core.playerBoard[x][y] == 3));
                hit = new boolean[]{core.fire(x, y, false), true};
                fired.add(new int[]{x, y});
                if (hit[0]) {
                    tracing = true;
                    center[0] = x;
                    center[1] = y;
                    dDN[1] = 0;
                    dDN[0]++;
                    dDN[2] = 0;
                    sank[dDN[2]][0] = center[0];
                    sank[dDN[2]][1] = center[1];
                }
            }
            if (iterations == 8) {
                tracing = false;
            }
            iterations++;
        } while (!hit[1]);
        checkShips();
        return hit[0];
    }

    /**
     * <b>The main logic for the pattern-trace firing.</b> Called from fire()<br>
     * Fires at a single cell in the square surrounding <i>core.playerBoard[x][y]</i>.<br/>
     * Changes direction in counterclockwise order starting from the top, using a global variable to remember the current direction.<br/>
     * After the four cardinal directions, it starts again at the north-west (top-left).<br/>
     *
     * @param x x index of the last hit
     * @param y y index of the last hit
     * @return boolean array - [0]: whether the method hit a ship, [1]: whether the method got to fire at all
     * @see ClassicGameController
     */
    private boolean[] fireOnShip(int x, int y) {
        boolean hit = false;
        switch (dDN[1]) {
            case 0 -> {
                if (x > 0 && (core.playerBoard[x - 1][y] != 1 && core.playerBoard[x - 1][y] != 3)) {
                    hit = core.fire(x - 1, y, false);
                    fired.add(new int[]{x - 1, y});
                    if (hit) {
                        center[0] = x - 1;
                        center[1] = y;
                        dDN[2]++;
                        sank[dDN[2]][0] = center[0];
                        sank[dDN[2]][1] = center[1];
                        dDN[0]++;
                    } else {
                        if (dDN[0] > 0) {
                            center[0] = x + dDN[0];
                            dDN[1] = 2;
                            dDN[0] = -1;
                        } else {
                            dDN[1] = 1;
                        }
                    }
                } else {
                    if (dDN[0] > 0) {
                        center[0] = x + dDN[0];
                        dDN[1] = 2;
                        dDN[0] = -1;
                    } else {
                        dDN[1] = 1;
                    }
                    return new boolean[]{false, false};
                }
            }
            case 1 -> {
                if (y > 0 && (core.playerBoard[x][y - 1] != 1 && core.playerBoard[x][y - 1] != 3)) {
                    hit = core.fire(x, y - 1, false);
                    fired.add(new int[]{x, y - 1});
                    if (hit) {
                        center[0] = x;
                        center[1] = y - 1;
                        dDN[2]++;
                        sank[dDN[2]][0] = center[0];
                        sank[dDN[2]][1] = center[1];
                        dDN[0]++;
                    } else {
                        if (dDN[0] > 0) {
                            dDN[1] = 3;
                            center[1] = y + dDN[0];
                            dDN[0] = -1;
                        } else {
                            dDN[1] = 2;
                        }
                    }
                } else {
                    if (dDN[0] > 0) {
                        dDN[1] = 3;
                        center[1] = y + dDN[0];
                        dDN[0] = -1;
                    } else {
                        dDN[1] = 2;
                    }
                    return new boolean[]{false, false};
                }
            }
            case 2 -> {
                if (x < 9 && (core.playerBoard[x + 1][y] != 1 && core.playerBoard[x + 1][y] != 3)) {
                    hit = core.fire(x + 1, y, false);
                    fired.add(new int[]{x + 1, y});
                    if (hit) {
                        center[0] = x + 1;
                        center[1] = y;
                        dDN[2]++;
                        sank[dDN[2]][0] = center[0];
                        sank[dDN[2]][1] = center[1];
                        dDN[0]++;
                    } else {
                        if (dDN[0] > 0) {
                            dDN[1] = 0;
                            center[0] = x - dDN[0];
                            dDN[0] = -1;
                        } else {
                            dDN[1] = 3;
                        }
                    }
                } else {
                    if (dDN[0] > 0) {
                        center[0] = x - dDN[0];
                        dDN[0] = -1;
                        dDN[1] = 0;
                    } else {
                        dDN[1] = 3;
                    }
                    return new boolean[]{false, false};
                }
            }
            case 3 -> {
                if (y < 9 && (core.playerBoard[x][y + 1] != 1 && core.playerBoard[x][y + 1] != 3)) {
                    hit = core.fire(x, y + 1, false);
                    fired.add(new int[]{x, y + 1});
                    if (hit) {
                        center[0] = x;
                        center[1] = y + 1;
                        dDN[2]++;
                        sank[dDN[2]][0] = center[0];
                        sank[dDN[2]][1] = center[1];
                        dDN[0]++;
                    } else {
                        if (dDN[0] > 0) {
                            dDN[1] = 0;
                            center[1] = y - dDN[0];
                            dDN[0] = 1;
                        } else {
                            dDN[1] = 4;
                        }
                    }
                } else {
                    if (dDN[0] > 0) {
                        dDN[1] = 0;
                        center[1] = y - dDN[0];
                        dDN[0] = 1;
                    } else {
                        dDN[1] = 4;
                    }
                    return new boolean[]{false, false};
                }
            }
            case 4 -> {
                if (x > 0 && y > 0 && (core.playerBoard[x - 1][y - 1] != 1 && core.playerBoard[x - 1][y - 1] != 3)) {
                    hit = core.fire(x - 1, y - 1, false);
                    fired.add(new int[]{x - 1, y - 1});
                    if (hit) {
                        center[0] = x - 1;
                        center[1] = y - 1;
                        dDN[2]++;
                        sank[dDN[2]][0] = center[0];
                        sank[dDN[2]][1] = center[1];
                        dDN[0]++;
                    } else {
                        if (dDN[0] > 0) {
                            dDN[1] = 6;
                            center[0] = x + dDN[0];
                            center[1] = y + dDN[0];
                            dDN[0] = -1;
                        } else {
                            dDN[1] = 5;
                        }
                    }
                } else {
                    if (dDN[0] > 0) {
                        dDN[1] = 6;
                        center[0] = x + dDN[0];
                        center[1] = y + dDN[0];
                        dDN[0] = -1;
                    } else {
                        dDN[1] = 5;
                    }
                    return new boolean[]{false, false};
                }
            }
            case 5 -> {
                if (y > 0 && x < 9 && (core.playerBoard[x + 1][y - 1] != 1 && core.playerBoard[x + 1][y - 1] != 3)) {
                    hit = core.fire(x + 1, y - 1, false);
                    fired.add(new int[]{x + 1, y - 1});
                    if (hit) {
                        center[0] = x + 1;
                        center[1] = y - 1;
                        dDN[2]++;
                        sank[dDN[2]][0] = center[0];
                        sank[dDN[2]][1] = center[1];
                        dDN[0]++;
                    } else {
                        if (dDN[0] > 0) {
                            dDN[1] = 7;
                            center[0] = x - dDN[0];
                            center[1] = y + dDN[0];
                            dDN[0] = -1;
                        } else {
                            dDN[1] = 6;
                        }
                    }
                } else {
                    if (dDN[0] > 0) {
                        dDN[1] = 7;
                        center[0] = x - dDN[0];
                        center[1] = y + dDN[0];
                        dDN[0] = -1;
                    } else {
                        dDN[1] = 6;
                    }
                    return new boolean[]{false, false};
                }
            }
            case 6 -> {
                if (x < 9 && y < 9 && (core.playerBoard[x + 1][y + 1] != 1 && core.playerBoard[x + 1][y + 1] != 3)) {
                    hit = core.fire(x + 1, y + 1, false);
                    fired.add(new int[]{x + 1, y + 1});
                    if (hit) {
                        center[0] = x + 1;
                        center[1] = y + 1;
                        dDN[2]++;
                        sank[dDN[2]][0] = center[0];
                        sank[dDN[2]][1] = center[1];
                        dDN[0]++;
                    } else {
                        if (dDN[0] > 0) {
                            center[0] = x - dDN[0];
                            center[1] = y - dDN[0];
                            dDN[0] = -1;
                            dDN[1] = 4;
                        } else {
                            dDN[1] = 7;
                        }
                    }
                } else {
                    if (dDN[0] > 0) {
                        dDN[1] = 4;
                        center[0] = x - dDN[0];
                        center[1] = y - dDN[0];
                        dDN[0] = -1;
                    } else {
                        dDN[1] = 7;
                    }
                    return new boolean[]{false, false};
                }
            }
            case 7 -> {
                if (y < 9 && x > 0 && (core.playerBoard[x - 1][y + 1] != 1 && core.playerBoard[x - 1][y + 1] != 3)) {
                    hit = core.fire(x - 1, y + 1, false);
                    fired.add(new int[]{x - 1, y + 1});
                    if (hit) {
                        center[0] = x - 1;
                        center[1] = y + 1;
                        dDN[2]++;
                        sank[dDN[2]][0] = center[0];
                        sank[dDN[2]][1] = center[1];
                        dDN[0]++;
                    } else {
                        if (dDN[0] > 0) {
                            center[0] = x + dDN[0];
                            center[1] = y - dDN[0];
                            dDN[0] = -1;
                            dDN[1] = 5;
                        } else {
                            dDN[1] = 0;
                        }
                    }
                } else {
                    if (dDN[0] > 0) {
                        center[0] = x + dDN[0];
                        center[1] = y - dDN[0];
                        dDN[0] = -1;
                        dDN[1] = 5;
                    } else {
                        dDN[1] = 0;
                    }
                    return new boolean[]{false, false};
                }
            }
        }
        return new boolean[]{hit, true};
    }

    /**
     * Checks to see if a ship has been sunk. I'm not entirely sure if it works, but, so far, I haven't had any problems
     */
    private void checkShips() {
        if ((sank[0][0] == sank[1][0] && sank[1][0] == sank[2][0]) || (sank[0][1] == sank[1][1] & sank[1][1] == sank[2][1])) {
            tracing = false;
            for (int[] ship : sank) {
                for (int i = 0; i < ship.length; i++) {
                    ship[i] = rand.nextInt();
                }
            }
            dDN[0] = -1;
            dDN[2] = -1;
        } else if (inDiagonal(sank[0], sank[1]) && (inDiagonal(sank[1], sank[2]) || inDiagonal(sank[0], sank[2]))) {
            tracing = false;
            for (int[] ship : sank) {
                for (int i = 0; i < ship.length; i++) {
                    ship[i] = rand.nextInt();
                }
            }
            dDN[0] = -1;
            dDN[2] = -1;
        } else if (inSquare(sank[0], sank[1], sank[2], sank[3])) {
            tracing = false;
            for (int[] ship : sank) {
                for (int i = 0; i < ship.length; i++) {
                    ship[i] = rand.nextInt();
                }
            }
            dDN[0] = -1;
            dDN[2] = -1;
        }
    }

    /**
     * Checks to see if two ships are in a diagonal, one cell apart
     *
     * @param cell1 One of the cells' coordinates
     * @param cell2 The other cell's coordinates (order does not matter)
     * @return if the cells are in a diagonal
     */
    private boolean inDiagonal(int[] cell1, int[] cell2) {
        return ((cell1[0] + 1 == cell2[0]) && (cell1[1] + 1 == cell2[1])) || ((cell1[0] - 1 == cell2[0]) && (cell1[1] - 1 == cell2[1])) || ((cell1[0] + 1 == cell2[0]) && (cell1[1] - 1 == cell2[1])) || ((cell1[0] - 1 == cell2[0]) && (cell1[1] + 1 == cell2[1]));
    }

    /**
     * Checks to see if four cells are in a square-formation
     *
     * @param cell1 One of the cells' coordinates
     * @param cell2 another cell's coordinates
     * @param cell3 another cell's coordinates
     * @param cell4 yet another cell's coordinates
     * @return if the cells are in a square
     */
    private boolean inSquare(int[] cell1, int[] cell2, int[] cell3, int[] cell4) {
        return ((cell1[0] == cell2[0]) && (cell3[0] == cell4[0]) && (cell1[1] == cell3[1]) && (cell2[1] == cell4[1])) || ((cell2[0] == cell3[0]) && (cell1[0] == cell4[0]) && (cell1[1] == cell2[1]) && (cell3[1] == cell4[1])) || ((cell1[0] == cell3[0]) && (cell2[0] == cell4[0]) && (cell1[1] == cell4[1]) && (cell3[1] == cell2[1])) || ((cell1[0] == cell2[0]) && (cell3[0] == cell4[0]) && (cell1[1] == cell4[1]) && (cell3[1] == cell2[1])) || ((cell1[0] == cell3[0]) && (cell2[0] == cell4[0]) && (cell3[1] == cell4[1]) && (cell1[1] == cell2[1])) || ((cell1[0] == cell4[0]) && (cell2[0] == cell3[0]) && (cell1[1] == cell3[1]) && (cell4[1] == cell2[1]));
    }

    /**
     * Method to place the Computer's Destroyer. Picks the location randomly
     */
    public void setDestroyer() {
        int x = rand.nextInt(0, 9);
        int y = rand.nextInt(0, 9);
        core.placeDestroyer(x, y, false);
    }

    /**
     * Method to place the Computer's Submarine. Picks the location randomly, and repeats the process until the location is valid.
     */
    public void setSub() {
        boolean validMove = false;
        int x, y, direction;
        while (!validMove) {
            x = rand.nextInt(0, 10);
            y = rand.nextInt(0, 10);
            direction = rand.nextInt(0, 2);
            validMove = core.placeSub(x, y, direction, false);
        }
    }

    /**
     * Method to place the Computer's Cruiser. Picks the location randomly, and repeats the process until the location is valid.
     */
    public void setCruiser() {
        boolean validMove = false;
        int x, y, direction;
        while (!validMove) {
            x = rand.nextInt(0, 10);
            y = rand.nextInt(0, 10);
            direction = rand.nextInt(0, 2);
            validMove = core.placeCruiser(x, y, direction, false);
        }
    }
}
