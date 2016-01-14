package team137.ai.units;

import static battlecode.common.RobotType.*;

import battlecode.common.*;
import team137.ai.actions.Action;
import team137.ai.actions.AttackAction;
import team137.ai.actions.MoveAction;
import team137.ai.actions.ClearAction;
import team137.ai.actions.priority.Priority;
import team137.ai.actions.priority.units.GuardPrioritySet;
import team137.ai.actions.priority.units.SoldierPrioritySet;
import team137.ai.tables.Directions;
import team137.ai.tables.robots.FleeWeights;
import team137.ai.tables.robots.RobotWeights;

import java.util.Random;

public class Soldier extends MovableUnit {

  private static final FleeWeights FLEE_TABLE = FleeWeights.newInstance();
  private static final RobotWeights ATTACK_TABLE = RobotWeights.uniformWeights();

  private final SoldierPrioritySet prioritySet;
  private final Random rand;
  private final Team team;

  public Soldier(RobotController rc) {
    super(rc);
    prioritySet = new SoldierPrioritySet();
    rand = new Random(rc.getID());
    team = rc.getTeam();
  }

  @Override
  public void update() {
    MapLocation curLoc = rc.getLocation();
    RobotInfo[] localEnemies = rc.senseNearbyRobots(SOLDIER.sensorRadiusSquared);

    try {
      MapLocation attackLoc = AttackUnits.findWeakest(localEnemies, ATTACK_TABLE, curLoc, Priority.LEVEL2_PRIORITY, team);
      rc.setIndicatorString(0, prioritySet.toString());
      rc.setIndicatorString(1, "" + attackLoc);

      if(attackLoc != null) {
        if(rc.canAttackLocation(attackLoc)) {
          prioritySet.putPriority(AttackAction.target(attackLoc), Priority.LEVEL4_PRIORITY);
        }
        else {
          Direction dirToLoc = curLoc.directionTo(attackLoc);
          prioritySet.putPriority(ClearAction.inDirection(dirToLoc), Priority.DEFAULT_PRIORITY);
          prioritySet.putPriority(MoveAction.inDirection(dirToLoc), Priority.DEFAULT_PRIORITY);
        }
      }
      RobotInfo[] localFriends = rc.senseNearbyRobots(2, team);
      if(localFriends.length > 3) {
        for(Direction dir : Directions.fairCardinals(rand)) {
          prioritySet.putPriority(MoveAction.inDirection(dir), Priority.DEFAULT_PRIORITY);
        }
      }
      if(rc.isCoreReady() && rc.isWeaponReady()) {
        Action action = prioritySet.fairAct(rc, rand);
        prioritySet.putPriority(action, Priority.FORBID_PRIORITY);
        prioritySet.update();
      }
    }
    catch (GameActionException e) {
      e.printStackTrace();
    }
  }
}