package Test;

import Models.Board;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    @Test
    void validatePosition() {
        Board board = new Board(3);
        assertTrue(board.validatePosition(0, 0));
    }

    @Test
    void markCollected() {
        Board board = new Board(3);
        board.markCollected(0, 0);
        assertEquals(board.valueAt(0,0), 0);
    }

    @Test
    void valueAt() {
        Board board = new Board(3);
        int value = board.valueAt(0, 0);

        Set<Integer> allowedValues = Set.of(-1, 0, 1, 2, 3, 4, 9);

        assertTrue(allowedValues.contains(value));
    }

}