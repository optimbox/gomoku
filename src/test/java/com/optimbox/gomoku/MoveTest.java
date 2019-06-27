package com.optimbox.gomoku;

import com.optimbox.gomoku.model.Board;
import com.optimbox.gomoku.model.Cell;
import com.optimbox.gomoku.model.Move;
import org.junit.jupiter.api.Test;


import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

class MoveTest {

    @Test
    void moveToOccupiedCellShouldBeIllegal() throws IOException {
        Board board = loadGame(2);
        try {
            board.makeMove(new Move(11, 3));
            fail();
        } catch (RuntimeException ignored) {
        }
    }

    @Test
    void blackMovesToEmptyCellShouldBeLegal() throws IOException {
        Board board = loadGame(1);
        int row = 11;
        int col = 3;
        board.makeMove(new Move(row, col));
        assertThat(board.getGrid()[row][col], is(Cell.WHITE));
    }

    @Test
    void whiteMovesToEmptyCellShouldBeLegal() throws IOException {
        Board board = loadGame(2);
        int row = 5;
        int col = 3;
        board.makeMove(new Move(row, col));
        assertThat(board.getGrid()[row][col], is(Cell.BLACK));
    }

    @Test
    void moveToOutsideOfTheBoardShouldBeIllegal() throws IOException {
        Board board = loadGame(0);
        try {
            board.makeMove(new Move(-1, 5));
            fail();
        } catch (RuntimeException ignored) {
        }

        try {
            board.makeMove(new Move(1, -5));
            fail();
        } catch (RuntimeException ignored) {
        }

        try {
            board.makeMove(new Move(21, 5));
            fail();
        } catch (RuntimeException ignored) {
        }

        try {
            board.makeMove(new Move(2, 50));
            fail();
        } catch (RuntimeException ignored) {
        }
    }

    @Test
    void moveShouldChangeTurn() {
        Board board = new Board();
        board.makeMove(new Move(0, 0));
        board.makeMove(new Move(1, 1));

        assertThat(board.getGrid()[0][0], is(Cell.BLACK));
        assertThat(board.getGrid()[1][1], is(Cell.WHITE));
    }

    @Test
    void lastMoveOfTheGameShouldWin() throws IOException {
        Board board = loadGame(69);
        assertThat(board.checkWin(), is(true));
    }

    @Test
    void beforeLastMoveShouldNotWin() throws IOException {
        Board board = loadGame(68);
        assertThat(board.checkWin(), is(false));
    }

    @Test
    void emptyBoardShouldNotWin() throws IOException {
        Board board = new Board();
        assertThat(board.checkWin(), is(false));
    }

    private Board loadGame(int moveNum) throws IOException {
        Board board = new Board();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("game1");
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);

        Move[] moves = new Move[moveNum];

        int row = 0;
        for (String line; (line = reader.readLine()) != null;) {
            String[] values = line.split(" ");
            int col = 0;
            for (String value : values) {
                if (!value.isEmpty()) {
                    int intValue = Integer.parseInt(value);
                    if (intValue > 0 && intValue <= moveNum) {
                        moves[intValue - 1] = new Move(row, col);
                    }
                    ++col;
                }
            }
            ++row;
        }

        for (int i = 0; i < moveNum; ++i) {
            board.makeMove(moves[i]);
        }

        board.printFriendly();

        return board;
    }

}
