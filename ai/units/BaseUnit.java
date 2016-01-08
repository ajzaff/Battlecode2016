package team137.ai.units;

import battlecode.common.*;

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
  public void update() {

  }
}
