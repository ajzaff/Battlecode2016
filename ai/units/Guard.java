package team137.ai.units;

import static battlecode.common.RobotType.*;

import battlecode.common.*;
import team137.ai.actions.MoveAction;
import team137.ai.actions.priority.Priority;
import team137.ai.actions.priority.units.GuardPrioritySet;
import team137.ai.tables.Directions;
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
    RobotInfo[] localEnemies = rc.senseHostileRobots(curLoc, GUARD.attackRadiusSquared);

    try {

      if(rc.isCoreReady()) {

        // SENSE ENEMIES

//        for (RobotInfo hostile : localEnemies) {
//          checkEnemy(prioritySet, FLEE_TABLE, curLoc, hostile);
//        }

        MapLocation attackLoc = AttackUnits.findWeakest(localEnemies);

        if(attackLoc != null && rc.canAttackLocation(attackLoc) && rc.isWeaponReady()) {
          rc.attackLocation(attackLoc);
        }
        else {
          RobotInfo[] nearbyFriends = rc.senseNearbyRobots(2, rc.getTeam());
          if(nearbyFriends.length>3) {
            Direction dir = Directions.cardinals()[rand.nextInt(8)];
            prioritySet.putPriority(MoveAction.fromDirection(dir), Priority.DEFAULT_PRIORITY);
          }else{//maybe a friend is in need!
            RobotInfo[] alliesToHelp = rc.senseNearbyRobots(1000000, rc.getTeam());
            MapLocation weakestOne = AttackUnits.findWeakest(alliesToHelp);
            if(weakestOne!=null){//found a friend most in need
              Direction towardFriend = rc.getLocation().directionTo(weakestOne);
              prioritySet.putPriority(MoveAction.fromDirection(towardFriend), Priority.LEVEL2_PRIORITY);
            }
          }
        }

        prioritySet.fairAct(rc, rand);
        prioritySet.update();
      }
    }
    catch (GameActionException e) {
      e.printStackTrace();
    }
  }


  private void checkWeakest() {

  }
}