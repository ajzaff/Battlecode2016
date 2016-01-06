package team137.ai.units;

import battlecode.common.RobotController;
import team137.ai.actions.priority.units.SoldierPriorityMap;

import java.util.Random;

public class Soldier extends BaseUnit {

  private final SoldierPriorityMap priorityMap;
  private final Random rand;

  public Soldier(RobotController rc) {
    super(rc);
    priorityMap = new SoldierPriorityMap();
    rand = new Random(rc.getID());
  }

  @Override
  public void update() {
    priorityMap.fairAct(rc, rand);
    priorityMap.update();
  }
}
