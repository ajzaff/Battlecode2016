package team137.ai.units;

import battlecode.common.RobotController;
import team137.ai.actions.priority.units.SoldierPrioritySet;

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

    rc.setIndicatorString(0, priorityMap.toString(7));

    try {
      priorityMap.fairAct(rc, rand);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    priorityMap.update();
  }
}
