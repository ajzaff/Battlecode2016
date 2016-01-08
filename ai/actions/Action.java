package team137.ai.actions;

import battlecode.common.*;

public interface Action {
  boolean act(RobotController rc) throws GameActionException;
}
