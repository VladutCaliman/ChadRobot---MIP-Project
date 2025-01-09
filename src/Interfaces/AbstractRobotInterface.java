package Interfaces;

public interface AbstractRobotInterface {
    void move(int x, int y);
    void decreaseFuel(int value);
    void setWon();
    boolean didWin();
    boolean didDie();

    void setFuel(int value);
    int getX();
    int getY();
    int getFuel();
    int getCurrentCoins();
    void addCoins(int amount);
    void resetCoins();
    void resetWon();
}
