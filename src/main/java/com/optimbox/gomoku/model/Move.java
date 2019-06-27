package com.optimbox.gomoku.model;

public class Move {
    private int col;

    private int row;

    public Move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
