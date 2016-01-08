package team137.ai.units;

import battlecode.common.RobotController;
import team137.ai.actions.priority.units.SoldierPrioritySet;
import team137.ai.units.BaseUnit;

import java.util.Random;

public class Soldier extends BaseUnit {

  private final SoldierPrioritySet priorityMap;
  private final Random rand;

  public Soldier(RobotController rc) {
    super(rc);
    priorityMap = new SoldierPrioritySet();
    rand = new Random(rc.getID());
  }

  @Override
  public void update() {

    // debug!
    rc.setIndicatorString(0, priorityMap.toString(7));

    try {
      if(rc.isCoreReady()) {
        // act!
        priorityMap.fairAct(rc, rand);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    priorityMap.update();
  }
}
