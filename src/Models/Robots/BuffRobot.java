package Models.Robots;

import Interfaces.BuffRobotInterface;


public class BuffRobot extends AbstractRobot implements BuffRobotInterface {
    private boolean secondChange;

    public BuffRobot(int xPos, int yPos, int fuel) {
        super(xPos, yPos, fuel);
        secondChange = true;
    }

    @Override
    public void useSecondChance() {
        if (didDie() && secondChange) {
            secondChange = false;
            fuel += 5;

        }
    }

    @Override
    public boolean hasUsedSecondChance(){
        return !secondChange;
    }

    @Override
    public void resetSecondChance() {
        secondChange = true;
    }

}
