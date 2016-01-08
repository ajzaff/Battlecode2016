package team137.ai.units;

import battlecode.common.*;
import team137.ai.actions.AttackAction;
import team137.ai.actions.priority.Priority;
import team137.ai.actions.priority.units.TurretPrioritySet;
import team137.ai.tables.robots.RobotTable;
import team137.ai.tables.robots.UniformRobotWeights;

import java.util.Random;

public class Turret extends BaseUnit {

  private static final RobotTable<Double> PREFS = UniformRobotWeights.getInstance();

  private final Random rand;
  private final TurretPrioritySet prioritySet;

  public Turret(RobotController rc) {
    super(rc);
    prioritySet = new TurretPrioritySet();
    rand = new Random(rc.getID());
  }

  @Override
  public void update() {
    MapLocation curLoc = rc.getLocation();
    RobotInfo[] localHostiles = rc.senseHostileRobots(curLoc, RobotType.TURRET.sensorRadiusSquared);

    // traverse hostiles
    for(RobotInfo hostile : localHostiles) {
      double priority = Priority.LEVEL8_PRIORITY.value * PREFS.get(hostile.type);
      if(rc.canAttackLocation(hostile.location)) {
        prioritySet.putPriority(AttackAction.target(hostile.location), priority);
      }
    }

    try {
      if(rc.isCoreReady() && rc.isWeaponReady()) {
        prioritySet.fairAct(rc, rand);
      }
    }
    catch (GameActionException e) {
      e.printStackTrace();
    }

    prioritySet.clearAttacks();
    prioritySet.update();
  }
}
