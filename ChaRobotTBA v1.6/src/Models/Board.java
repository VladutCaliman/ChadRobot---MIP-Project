package Models;

import java.util.*;

public class Board {
    private List<List<Integer>> board;
    private int size;

    private List<Integer> generateLine() {
        List<Integer> line = new ArrayList<>();
        Random random = new Random();

        int chanceZero = 950;          // 95.0%
        int chanceOneToFour = 40;      // 4.0%
        int chanceNine = 4;            // 0.1%

        for (int i = 0; i < size; i++) {
            int randNumber = random.nextInt(1000); // Random number between 0 and 999

            if (randNumber < chanceZero) {
                line.add(0);
            } else if (randNumber < chanceZero + chanceOneToFour) {
                line.add(random.nextInt(4) + 1);
            } else if (randNumber < chanceZero + chanceOneToFour + chanceNine) {
                line.add(9);
            } else {
                line.add(-1);
            }
        }

        return line;
    }

    private void addRowAtBeginning() {
        board.add(0, generateLine());
    }

    private void addRowAtEnd() {
        board.add(generateLine());
    }

    private void removeRowAtBeginning() {
        if (!board.isEmpty()) {
            board.removeFirst();
        }
    }

    private void removeRowAtEnd() {
        if (!board.isEmpty()) {
                board.removeLast();
        }
    }

    private void addColumnAtBeginning() {
        for (List<Integer> row : board) {
            row.addFirst(generateLine().getFirst());
        }
    }

    private void addColumnAtEnd() {
        for (List<Integer> row : board) {
            row.add(generateLine().getFirst());
        }
    }

    private void removeColumnAtBeginning() {
        if (!board.isEmpty()) {
            for (List<Integer> row : board) {
                row.removeFirst();
            }
        }
    }

    private void removeColumnAtEnd() {
        if (!board.isEmpty()) {
            for (List<Integer> row : board) {
                row.removeLast();
            }
        }
    }

    public Board(int size) {
        if(size % 2 != 1)
            throw new IllegalArgumentException("Dimensions must be odd");

        this.size = size;
        this.board = new ArrayList<>();
        for(int i = 0; i < size; i++){
            this.board.add(generateLine());
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean validatePosition(int x, int y) {
        if (x < 0 || x >= board.size())
        {
            return false;
        }
        return y >= 0 && y < board.get(x).size();
    }

    public void markCollected(int robotX, int robotY) {
        board.get(robotX).set(robotY, 0);
    }

    public int valueAt(int robotX, int robotY) {
        return board.get(robotX).get(robotY);
    }

    public boolean checkIsWall(int robotX, int robotY) {
        return board.get(robotX).get(robotY) == -1;
    }

    public boolean checkIsChest(int robotX, int robotY) {
        return board.get(robotX).get(robotY) == 9;
    }

    public void updateBoard(int newX, int newY) {
        if (newX == -1) {  // Move up
            addRowAtBeginning();
            removeRowAtEnd();
        } else if (newX == 1) {  // Move down
            addRowAtEnd();
            removeRowAtBeginning();
        }


        if (newY == -1) {  // Move left
            addColumnAtBeginning();
            removeColumnAtEnd();
        } else if (newY == 1) {  // Move right
            addColumnAtEnd();
            removeColumnAtBeginning();
        }

    }

    public List<List<Integer>> getMap() {
        return board;
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Check if the current position is the middle of the board
                if (i == size / 2 && j == size / 2) {
                    System.out.print("* ");  // Print "*" for the middle element
                } else {
                    System.out.print(board.get(i).get(j) + " ");  // Print the actual value
                }
            }
            System.out.println();
        }
    }

}


