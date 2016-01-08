package team137.ai.actions;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public interface Action {
  boolean act(RobotController rc) throws GameActionException;
}
