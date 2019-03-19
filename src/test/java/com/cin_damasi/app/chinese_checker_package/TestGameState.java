package com.cin_damasi.app.chinese_checker_package;

import org.junit.Test.None;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


//  player color constants
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PLAYER_RED;
import static com.cin_damasi.app.chinese_checker_package.ProjectDefinitions.PLAYER_GREEN;

import java.awt.*;
import javax.swing.*;
import java.util.List;

import javax.swing.JButton;
import javax.swing.text.Position;

class JUnit5ExampleTest {

    // empty constructer
    GameState testGameStateInitial = new GameState();

    Player boran = new HumanPlayer(PLAYER_GREEN);


    @Test
    void testEmptyPiece() {

        // out of border
        assertEquals(false, testGameStateInitial.isSquareAvailable(-1, -1));
        assertEquals(false, testGameStateInitial.isSquareAvailable(8, 8));

        // it should return false for not empty cell
        assertEquals(false, testGameStateInitial.isSquareAvailable(0, 0));

        // is should return true for empty cell
        assertEquals(true, testGameStateInitial.isSquareAvailable(3, 3));


    }

    @Test
    void testGetNextMovesRedPieceRightBottomTest() {

        PiecePosition rightBottomCornerPos = new PiecePosition(2 ,2);
        Piece rightBottomPiece = new Piece(rightBottomCornerPos, ProjectDefinitions.PIECE_COLOR_GREEN_PIECE, boran);

        List<Move> rightBottomoMoves = testGameStateInitial.getNextMovesForward(rightBottomPiece);

        assertEquals(2, rightBottomoMoves.size());

    }
}