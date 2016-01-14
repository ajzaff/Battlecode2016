package team137.ai.units;

import battlecode.common.*;
import team137.ai.actions.Action;
import team137.ai.actions.BuildAction;
import team137.ai.actions.MoveAction;
import team137.ai.actions.archon.ActivateAction;
import team137.ai.actions.priority.Priority;
import team137.ai.actions.priority.units.ArchonPrioritySet;
import team137.ai.tables.Directions;
import team137.ai.tables.Rubble;
import team137.ai.tables.robots.FleeWeights;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static battlecode.common.RobotType.*;

public class Archon extends MovableUnit {

  ///////////// "SHARED" FIELDS

  public static final FleeWeights FLEE_TABLE = FleeWeights.newInstance();


  static {
    // CUSTOMIZE FLEE_TABLE
    FLEE_TABLE.put(ZOMBIEDEN, FleeWeights.Row.newInstance(-.5, -.05));
  }

  private static final double[] RUBBLE_MAP = Rubble.map(1, .5, .125);
  private static final int SENSOR_RADIUS =
      (int) Math.sqrt(ARCHON.sensorRadiusSquared);

  ///////////// ROBOT CONSTANT FIELDS

  private final ArchonPrioritySet prioritySet; // priority-queue-like structure
  private final Random rand;                   // random number generator

  public Archon(RobotController rc) {
    super(rc);
    prioritySet = new ArchonPrioritySet();
    rand = new Random(rc.getID());
  }

  @Override
  public void update() {

    // TURN VARIABLES

    MapLocation curLoc = rc.getLocation();
    Map<Direction, Double> fleeBuffer = new HashMap<>(8);

    // START TURN

    try {
      rc.setIndicatorString(0, prioritySet.toString(7));  // debug

      if(rc.isCoreReady()) {

        checkBuild(curLoc);

        RobotInfo[] localRobots = rc.senseNearbyRobots();

        checkLocals(curLoc, localRobots, fleeBuffer);

        checkRubble(fleeBuffer, curLoc);

        applyFleeBuffer(fleeBuffer);

        // BEGIN ACTUATION

        Action action = prioritySet.fairAct(rc, rand);                    // act!

        // DECAY PRIORITY

        decay(action);
      }
    }
    catch (GameActionException e) {
      e.printStackTrace();
    }
  }

  private void checkRubble(Map<Direction, Double> fleeBuffer, MapLocation curLoc) {
    for(Direction dir : fleeBuffer.keySet()) {
      MapLocation locOfFlee = curLoc.add(dir);
      double rubble = rc.senseRubble(locOfFlee);
      double weight = Rubble.weight(RUBBLE_MAP, rubble);
      fleeBuffer.put(dir, fleeBuffer.get(dir) / weight);
    }
  }

  private void decay(Action action) {
    if(action instanceof MoveAction) {
      prioritySet.putPriority(action, Priority.FORBID_PRIORITY);
    }
    else if(action instanceof ActivateAction) {
      prioritySet.putPriority(action, Priority.FORBID_PRIORITY);
    }
  }

  private void applyFleeBuffer(Map<Direction, Double> fleeBuffer) {
    for(Direction dir : fleeBuffer.keySet()) {
      prioritySet.addPriorityButPermit(
          MoveAction.inDirection(dir),
          fleeBuffer.get(dir)
      );
    }
  }

  private void checkLocals(
      MapLocation curLoc,
      RobotInfo[] localRobots,
      Map<Direction, Double> fleeBuffer)
      throws GameActionException
  {
    for(RobotInfo robotInfo : localRobots) {
      checkNeutrals(curLoc, robotInfo);
      checkFlee(
          FLEE_TABLE,
          curLoc,
          robotInfo,
          Priority.DEFAULT_PRIORITY,
          fleeBuffer);
    }
  }

  private void checkBuild(MapLocation curLoc) throws GameActionException {
    if(rc.getTeamParts() > SOLDIER.partCost) {
      for(Direction dir : Directions.cardinals()) {
        if(rc.canBuild(dir, SOLDIER)) {
          prioritySet.putPriority(BuildAction.getInstance(SOLDIER, dir), Priority.DEFAULT_PRIORITY);
        }
      }
    }
  }

  public boolean checkNeutrals(MapLocation curLoc, RobotInfo robotInfo) throws GameActionException {
    // returns flag for ``adjacentNeutral``
    boolean adjacentNeutral = false;
    if(robotInfo.team == Team.NEUTRAL) {
      Direction dirToLoc = curLoc.directionTo(robotInfo.location);
      Action action;
      Priority priority;
      if (curLoc.isAdjacentTo(robotInfo.location)) {
        action = ActivateAction.fromDirection(dirToLoc);
        priority = Priority.LEVEL8_PRIORITY;
        adjacentNeutral = true;
      } else {
        action = MoveAction.inDirection(dirToLoc);
        priority = Priority.LOWEST_PRIORITY;
      }
      prioritySet.addPriority(action, priority);
    }
    return adjacentNeutral;
  }
}
