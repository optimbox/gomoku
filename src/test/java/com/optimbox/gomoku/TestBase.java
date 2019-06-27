package com.optimbox.gomoku;

import com.optimbox.gomoku.model.Board;
import com.optimbox.gomoku.model.Color;
import com.optimbox.gomoku.model.Move;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

class TestBase {

    Board loadGame(int moveNum) throws IOException {
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

        return board;
    }

}
