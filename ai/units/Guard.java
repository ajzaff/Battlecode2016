package team137.ai.units;

import static battlecode.common.RobotType.*;

import battlecode.common.*;
import team137.ai.actions.AttackAction;
import team137.ai.actions.priority.Priority;
import team137.ai.actions.priority.units.GuardPrioritySet;
import team137.ai.tables.robots.FleeWeights;
import team137.ai.tables.robots.RobotWeights;

import java.util.Random;

public class Guard extends MovableUnit {

  private static final FleeWeights FLEE_TABLE = FleeWeights.newInstance();
  private static final RobotWeights ATTACK_TABLE = RobotWeights.uniformWeights();

  private final GuardPrioritySet prioritySet;
  private final Random rand;

  public Guard(RobotController rc) {
    super(rc);
    prioritySet = new GuardPrioritySet();
    rand = new Random(rc.getID());
  }

  @Override
  public void update() {
    MapLocation curLoc = rc.getLocation();
    RobotInfo[] localEnemies = rc.senseHostileRobots(curLoc, GUARD.attackRadiusSquared);

    try {

      if(rc.isCoreReady()) {

        // SENSE ENEMIES

//        for (RobotInfo hostile : localEnemies) {
//          checkEnemy(prioritySet, FLEE_TABLE, curLoc, hostile, Priority.DEFAULT_PRIORITY);
//        }

        MapLocation attackLoc = AttackUnits.findWeakest(localEnemies);

        if(attackLoc != null && rc.isWeaponReady()) {
          prioritySet.putPriority(AttackAction.target(attackLoc), Priority.LEVEL4_PRIORITY);
        }

        prioritySet.fairAct(rc, rand);
        prioritySet.update();
      }
    }
    catch (GameActionException e) {
      e.printStackTrace();
    }
  }
}