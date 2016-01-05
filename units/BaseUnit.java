package team137.units;

import battlecode.common.Direction;
import battlecode.common.RobotController;

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
    }
  }

  public boolean safeMove(Direction dir) {
    try {
      if(rc.isCoreReady() && rc.canMove(dir)) {
        rc.move(dir);
        return true;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}
