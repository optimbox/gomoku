package com.optimbox.gomoku;

import com.optimbox.gomoku.model.Board;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class BoardTest extends TestBase {

    @Test
    void lastMoveOfTheGameShouldWin() throws IOException {
        Board board = loadGame(69);
        assertThat(board.checkWin(), is(true));
    }

    @Test
    void beforeLastMoveShouldNotWin() throws IOException {
        for (int i = 0; i < 69; ++i) {
            Board board = loadGame(i);
            assertThat(board.checkWin(), is(false));
        }
    }

    @Test
    void emptyBoardShouldNotWin() {
        Board board = new Board();
        assertThat(board.checkWin(), is(false));
    }

    @Test
    void shouldSuggestNextMove() throws IOException {
        Board board = loadGame(18);
        board.printFriendly();
    }

}
