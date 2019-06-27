package com.optimbox.gomoku.model;

import java.util.Optional;

import static com.optimbox.gomoku.model.Color.*;

public class Board {

    static int N = 15;

    public static int CENTER = N / 2;

    private Optional<Color> grid[][];

    private Color turn;

    private Move lastMove;

    public Board() {
        turn = BLACK;
        grid = new Optional[N][N];
        reset();
        lastMove = null;
    }

    public Optional<Color>[][] getGrid() {
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
        Color color = grid[curRow][curCol].get();

        while (insideBoard(curRow, curCol) && grid[curRow][curCol].isPresent() && grid[curRow][curCol].get() == color) {
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
                Optional<Color> cell = grid[row][col];
                buffer.append(" ");
                buffer.append(cell.isPresent() ? (cell.get() == BLACK ? 'X' : 'O') : '.');
            }
            System.out.println(buffer.toString());
        }
    }

    private void reset() {
        for (int row = 0; row < N; ++row) {
            for (int col = 0; col < N; ++col) {
                grid[row][col] = Optional.empty();
            }
        }
    }

    public void makeMove(Move move) {
        if (!insideBoard(move.getRow(), move.getCol()) || (grid[move.getRow()][move.getCol()].isPresent())) {
            throw new RuntimeException("Illegal move");
        }

        grid[move.getRow()][move.getCol()] = Optional.of(turn);
        switchTurn();
        lastMove = move;
    }

    private void switchTurn() {
        if (turn == BLACK) {
            turn = WHITE;
        } else {
            turn = BLACK;
        }
    }

    private boolean insideBoard(int row, int col) {
        return (row >= 0 && row <= N - 1 && col >= 0 && col <= N - 1);
    }

}
