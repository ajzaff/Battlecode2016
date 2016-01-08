package team137.ai.units;

import static battlecode.common.RobotType.*;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import team137.ai.actions.priority.units.GuardPrioritySet;
import team137.ai.tables.robots.FleeWeights;

import java.util.Random;

public class Guard extends MovableUnit {

  private static final FleeWeights FLEE_TABLE = FleeWeights.newInstance();

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
    RobotInfo[] localEnemies = rc.senseHostileRobots(curLoc, GUARD.sensorRadiusSquared);

    try {

      if(rc.isCoreReady()) {

        // SENSE ENEMIES

        for (RobotInfo hostile : localEnemies) {
          checkEnemy(prioritySet, FLEE_TABLE, curLoc, hostile);
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