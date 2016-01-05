package team137.ai.units;

import battlecode.common.*;

import java.util.Random;

public abstract class BaseUnit implements Unit {
  protected final RobotController rc;
  public BaseUnit(RobotController rc) {
    this.rc = rc;
  }

  @Override
  @SuppressWarnings("InfiniteLoopStatement")
  public void run() {
    while(true) {
      update();
      Clock.yield();
    }
  }

  @Override
  public boolean update() {
    return false;
  }

  public RobotInfo safeSenseRobotAtLocation(MapLocation loc) {
    try {
      if(rc.canSenseLocation(loc)) {
        return rc.senseRobotAtLocation(loc);
      }
    }
    catch (GameActionException e) {
      e.printStackTrace();
    }
    return null;
  }

  public boolean safeOnTheMap(MapLocation loc) {
    try {
      return rc.onTheMap(loc);
    }
    catch (GameActionException e) {
      e.printStackTrace();
    }
    return false;
  }
}
