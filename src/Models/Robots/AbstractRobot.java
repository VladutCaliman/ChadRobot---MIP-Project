package Models.Robots;

import Interfaces.AbstractRobotInterface;

public abstract class AbstractRobot implements AbstractRobotInterface {
    protected int xPos, yPos;
    protected int fuel, currentCoins;
    protected boolean won = false;


    public AbstractRobot(int xPos, int yPos, int fuel) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.fuel = fuel;
        this.currentCoins = 0;
    }

    @Override
    public void move(int x, int y) {
        this.xPos += x;
        this.yPos += y;
        decreaseFuel(1);
    }

    @Override
    public void decreaseFuel(int value) {
        fuel-= value;
    }

    @Override
    public int getX() {
        return xPos;
    }

    @Override
    public int getY() {
        return yPos;
    }

    @Override
    public int getFuel() {
        return fuel;
    }

    @Override
    public int getCurrentCoins() {
        return currentCoins;
    }

    @Override
    public void addCoins(int amount){
        currentCoins += amount;
    }

    @Override
    public boolean didWin(){
        return won;
    }

    @Override
    public boolean didDie(){
        return fuel <= 0;
    }

    @Override
    public void setWon(){
        won = true;
    }

    @Override
    public void resetWon(){won = false;}

    @Override
    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    @Override
    public void resetCoins(){this.currentCoins = 0;}

}
