package com.optimbox.gomoku;

import com.optimbox.gomoku.model.Board;
import com.optimbox.gomoku.model.Color;
import com.optimbox.gomoku.model.Move;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.optimbox.gomoku.model.Color.BLACK;
import static com.optimbox.gomoku.model.Color.WHITE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

class MoveTest extends TestBase {

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
    void whiteMovesToEmptyCellShouldBeLegal() throws IOException {
        Board board = loadGame(1);
        int row = 11;
        int col = 3;
        board.makeMove(new Move(row, col));

        assertThat(board.getGrid()[row][col].get(), is(Color.WHITE));
    }

    @Test
    void blackMovesToEmptyCellShouldBeLegal() throws IOException {
        Board board = loadGame(2);
        int row = 5;
        int col = 3;
        board.makeMove(new Move(row, col));
        assertThat(board.getGrid()[row][col].get(), is(BLACK));
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

        assertThat(board.getGrid()[0][0].get(), is(BLACK));
        assertThat(board.getGrid()[1][1].get(), is(WHITE));
    }

}
