/**
 *
 * Backend code for the Order and Chaos game.
 *
 * Created by jakastavanja on 19/04/16.
 */

public class OAC {

    private char[][] game;

    public OAC(char[][] game) {
        this.game = game;
    }

    public void changeElement(int i, int j, char c) {
        this.game[i][j] = c;
    }

    public char[][] returnGame() {
        return this.game;
    }

    public boolean checkRows() {

        char current, prevCurrent;
        int recurrentSame = 1;

        for(int i = 0; i < 6; i++) {
            prevCurrent = game[i][0];
            for(int j = 1; j < 6; j++) {
                current = game[i][j];
                if(current == prevCurrent) {
                    recurrentSame++;
                } else {
                    recurrentSame = 1;
                }
                if(recurrentSame >= 5) {
                    return true;
                }
                prevCurrent = current;
            }
        }
        return false;
    }

    public boolean checkColumns() {

        char current, prevCurrent;
        int recurrentSame = 1;

        for(int i = 0; i < 6; i++) {
            prevCurrent = this.game[0][i];
            for(int j = 1; j < 6; j++) {
                current = this.game[j][i];
                if(current == prevCurrent) {
                    recurrentSame++;
                } else {
                    recurrentSame = 1;
                }
                if(recurrentSame >= 5) {
                    return true;
                }
                prevCurrent = current;
            }
        }
        return false;
    }

    public boolean checkDiagonals() {

        char current, prevCurrent;
        int recurrentSame = 1;

        // checking the middle diagonal UL to LR
        prevCurrent = game[0][0];
        for(int i = 1; i < 6; i++) {
            current = game[i][i];
            if (current == prevCurrent) {
                recurrentSame++;
            } else {
                recurrentSame = 1;
            }
            if (recurrentSame >= 5) {
                return true;
            }
            prevCurrent = current;
        }

        // checking the middle diagonal LL to UR

        int j = 1;
        prevCurrent = game[5][0];
        for(int i = 4; i >= 0; i--) {
            current = game[i][j];
            if (current == prevCurrent) {
                recurrentSame++;
            } else {
                recurrentSame = 1;
            }
            if (recurrentSame >= 5) {
                return true;
            }
            prevCurrent = current;
            j++;
        }

        // checking small upper diagonal UL to DR

        prevCurrent = game[0][1];
        for(int i = 1; i < 5; i++) {
            current = game[i][i+1];
            if (current == prevCurrent) {
                recurrentSame++;
            } else {
                recurrentSame = 1;
            }
            if (recurrentSame >= 5) {
                return true;
            }
            prevCurrent = current;
        }

        // checking small lower diagonal UL to DR

        prevCurrent = game[1][0];
        for(int i = 2; i < 6; i++) {
            current = game[i][i-1];
            if (current == prevCurrent) {
                recurrentSame++;
            } else {
                recurrentSame = 1;
            }
            if (recurrentSame >= 5) {
                return true;
            }
            prevCurrent = current;
        }

        // checking small upper diagonal LL to UR

        j = 1;
        prevCurrent = game[4][0];
        for(int i = 3; i >= 0; i--) {
            current = game[i][j];
            if (current == prevCurrent) {
                recurrentSame++;
            } else {
                recurrentSame = 1;
            }
            if (recurrentSame >= 5) {
                return true;
            }
            prevCurrent = current;
            j++;
        }

        // checking small lower diagonal LL to UR

        j = 2;
        prevCurrent = game[5][1];
        for(int i = 4; i > 0; i--) {
            current = game[i][j];
            if (current == prevCurrent) {
                recurrentSame++;
            } else {
                recurrentSame = 1;
            }
            if (recurrentSame >= 5) {
                return true;
            }
            prevCurrent = current;
            j++;
        }

        return false;
    }

    public boolean checkForWin() {
        if(checkColumns() == true || checkRows() == true || checkDiagonals() == true) {
            return true;
        } else {
            return false;
        }
    }
}
