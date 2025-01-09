package Utility;

import Models.*;
import Models.Robots.*;

import java.io.*;

public class GameManager {
    private int score, fuel = 20;
    private static final int boardSize = 7;

    private Board board;
    private AbstractRobot robot;

    public GameManager(){
        loadScoreFromFile("src/Resources/Score.txt");
        this.board = new Board(boardSize);
        setRobot(1);
    }

    public void loadScoreFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            score = Integer.parseInt(br.readLine());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void dumpScoreOnFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(String.valueOf(score));
        } catch (IOException e) {
            System.out.println("Error writing score to file: " + e.getMessage());
        }
    }

    public void processInput(char input) {
        if (!robot.didDie() && !robot.didWin()) {
            switch (input) {
                case 'w' -> moveLogic(-1, 0);
                case 'a' -> moveLogic(0, -1);
                case 's' -> moveLogic(1, 0);
                case 'd' -> moveLogic(0, 1);
            }
        } else {
            if (robot instanceof BuffRobot) {
                ((BuffRobot) robot).useSecondChance();
            } else {
                System.out.println("Died!");
            }
        }
    }

    public void moveLogic(int x, int y) {
        int newX = robot.getX() + x;
        int newY = robot.getY() + y;

        if (board.validatePosition(newX, newY)) {

            if (board.checkIsWall(newX, newY)) {
                robot.decreaseFuel(2);
                return;
            }

            if (board.checkIsChest(newX, newY)) {
                robot.setWon();
                score += 9 + robot.getCurrentCoins();
                dumpScoreOnFile("src/Resources/Score.txt");
                System.out.println("You won!");
            }

            robot.decreaseFuel(1);
            robot.addCoins(board.valueAt(newX, newY));
            board.markCollected(newX, newY);
            board.updateBoard(x, y);
        } else {
            robot.decreaseFuel(1);
        }
    }

    public AbstractRobot getRobot() {
        return robot;
    }
    public int getScore() {
        return score;
    }
    public Board getBoard() {
        return board;
    }

    public void setRobot(int choice){
        switch (choice){
            case 1 -> robot = new NormRobot(boardSize/2,boardSize/2,fuel);
            case 2 -> robot = new BuffRobot(boardSize/2,boardSize/2,fuel);
        }
    }
    public void resetBoard() {
        this.board = new Board(boardSize);
    }
    public void resetRobot(){
        this.robot.setFuel(fuel);
        this.robot.resetCoins();
        this.robot.resetWon();
        if(robot instanceof BuffRobot){
            ((BuffRobot) robot).resetSecondChance();
        }
    }
}
