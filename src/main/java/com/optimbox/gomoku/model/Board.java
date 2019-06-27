package com.optimbox.gomoku.model;

import static com.optimbox.gomoku.model.Cell.*;

public class Board {

    public static int N = 15;

    public static int CENTER = N / 2;

    private Cell grid[][];

    private int turn;

    private Move lastMove = null;

    public Board() {
        grid = new Cell[N][N];
        reset();
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public boolean checkWin() {
        boolean win = false;

        if (lastMove != null) {
            int[] rowDelta = {0, 1, 1, 1,};
            int[] colDelta = {1, 1, 0, -1};

            for (int i = 0; i < 4; ++i) {
                int inARow = countInARow(lastMove, rowDelta[i], colDelta[i]) + countInARow(lastMove, -rowDelta[i], -colDelta[i]) - 1;
                if (inARow >= 5) {
                    win = true;
                    break;
                }
            }
        }

        return win;
    }

    private int countInARow(Move origin, int rowDelta, int colDelta) {
        int total = 0;
        int curRow = origin.getRow();
        int curCol = origin.getCol();
        Cell color = grid[curRow][curCol];

        while (insideBoard(curRow, curCol) && grid[curRow][curCol] == color) {
            curRow += rowDelta;
            curCol += colDelta;
            ++total;
        }

        return total;
    }

    public void printFriendly() {
        System.out.println("  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5");

        for (int row = 0; row < N; ++row) {
            StringBuilder buffer = new StringBuilder();
            buffer.append(row % 10);
            for (int col = 0; col < N; ++col) {
                Cell cell = grid[row][col];
                buffer.append(" ");
                buffer.append(cell == BLACK ? 'X' : (cell == WHITE ? 'O': '.'));
            }
            System.out.println(buffer.toString());
        }
    }

    private void reset() {
        for (int row = 0; row < N; ++row) {
            for (int col = 0; col < N; ++col) {
                grid[row][col] = Cell.EMPTY;
            }
        }
    }

    public void makeMove(Move move) {
        if (!insideBoard(move.getRow(), move.getCol()) || (grid[move.getRow()][move.getCol()] != EMPTY)) {
            throw new RuntimeException("Illegal move");
        }

        grid[move.getRow()][move.getCol()] = turn == 0 ? BLACK : WHITE;
        turn = 1 - turn;
        lastMove = move;
    }

    private boolean insideBoard(int row, int col) {
        return (row >= 0 && row <= N - 1 && row >= 0 && row <= N - 1);
    }

}
