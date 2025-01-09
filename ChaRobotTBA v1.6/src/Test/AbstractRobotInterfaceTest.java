package Test;

import Interfaces.AbstractRobotInterface;
import Models.Robots.AbstractRobot;
import Models.Robots.NormRobot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AbstractRobotInterfaceTest {

    @Test
    void decreaseFuel() {
        AbstractRobotInterface robotInterface = new NormRobot(2,2,2);
        robotInterface.decreaseFuel(1);
        assertEquals(robotInterface.getFuel(), 1);
    }

    @Test
    void setWon() {
        AbstractRobotInterface robotInterface = new NormRobot(2,2,2);
        robotInterface.setWon();
        assertTrue(robotInterface.didWin());
    }

    @Test
    void didWin() {
        AbstractRobotInterface robotInterface = new NormRobot(2,2,2);
        assertFalse(robotInterface.didWin());
    }

    @Test
    void didDie() {
        AbstractRobotInterface robotInterface = new NormRobot(2,2,2);
        robotInterface.decreaseFuel(2);
        assertTrue(robotInterface.didDie());
    }

    @Test
    void addCoins() {
        AbstractRobotInterface robotInterface = new NormRobot(2,2,2);
        robotInterface.addCoins(10);
        assertEquals(robotInterface.getCurrentCoins(), 10);
    }
}