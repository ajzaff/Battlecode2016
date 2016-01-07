package team137.ai.units;

import static battlecode.common.RobotType.*;
import static battlecode.common.MapLocation.getAllMapLocationsWithinRadiusSq;

import battlecode.common.*;
import team137.ai.actions.Action;
import team137.ai.actions.MoveAction;
import team137.ai.actions.archon.ActivateAction;
import team137.ai.actions.priority.Priority;
import team137.ai.actions.priority.units.ArchonPrioritySet;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Archon extends BaseUnit {

  private final ArchonPrioritySet priorityMap;
  private final Team team;
  private final Random rand;

  public Archon(RobotController rc) {
    super(rc);
    priorityMap = new ArchonPrioritySet();
    team = rc.getTeam();
    rand = new Random(rc.getID());
  }

  @Override
  public void update() {

    MapLocation curLoc = rc.getLocation();
    MapLocation[] localTiles = getAllMapLocationsWithinRadiusSq(curLoc, ARCHON.sensorRadiusSquared);

    // check if we're stuck in impassable rubble!
    checkStuckInRubble(curLoc);

    // adjacent rubble multipliers (for path-finding)
    Map<Direction, Double> rubbleMap = new HashMap<>(8);

    try {
      for(MapLocation loc : localTiles) {
        if(loc.isAdjacentTo(curLoc)) {
          checkRubble(loc, rubbleMap);
        }
        checkParts(loc);
      }

      // debug
      rc.setIndicatorString(0, priorityMap.toString(7));

      // apply rubble map
      applyRubbleMap(rubbleMap);

      // act!
      if(rc.isCoreReady()) {
        priorityMap.fairAct(rc, rand);
      }
    }
    catch (GameActionException e) {
      e.printStackTrace();
    }

    // update priorities
    priorityMap.update();
  }

  private void applyRubbleMap(Map<Direction, Double> rubbleMap) {
    for(Direction dir : rubbleMap.keySet()) {
      priorityMap.putPriority(MoveAction.fromDirection(dir), priorityMap.getPriority());
    }
  }

  public void checkStuckInRubble(MapLocation curLoc) {
    double curRubble = rc.senseRubble(curLoc);

    // see if we are stuck!
    if(curRubble > 100) {
      // TODO: send distress signal
    }
  }

  public void checkClearRubble(MapLocation curLoc) {



  }

  public void checkParts(MapLocation loc) {
//    double curParts = rc.senseParts(curLoc);

    // max parts
//    MapLocation maxPartsLoc = curLoc;
//    Direction maxPartsDir = OMNI;
//    double maxParts = 0;
  }

  public void checkRubble(MapLocation loc, Map<Direction, Double> rubbleMap) {

    // min rubble
//    MapLocation minAdjacentRubbleLoc = curLoc;
//    Direction minAdjacentRubbleDir = OMNI;
//    double minAdjacentRubble = Double.MAX_VALUE;
  }

  public void checkWallAvoidance(MapLocation curLoc, MapLocation[] tiles) {
    for(MapLocation loc : tiles) {
      if(! safeOnTheMap(loc)) {
        Direction dirFromLoc = curLoc.directionTo(loc).opposite();
        MoveAction action = MoveAction.fromDirection(dirFromLoc);
        priorityMap.addPriority(action, Priority.LOWEST_PRIORITY);
      }
    }
  }

  public void checkNeutrals(MapLocation curLoc, RobotInfo[] neighbors) {
    boolean hasNeutrals = false;
    for(RobotInfo robo : neighbors) {
      if(robo.team == Team.NEUTRAL) {
        Direction dirToLoc = curLoc.directionTo(robo.location);
        Action action;
        Priority priority;
        if(curLoc.isAdjacentTo(robo.location)) {
          action = ActivateAction.fromDirection(dirToLoc);
          priority = Priority.LEVEL8_PRIORITY;
          hasNeutrals = true;
        }
        else {
          action = MoveAction.fromDirection(dirToLoc);
          priority = Priority.DEFAULT_PRIORITY;
        }
        priorityMap.addPriority(action, priority);
      }
    }

    if(! hasNeutrals) {
      priorityMap.forbidActivate();
    }
  }

  public void checkEvasion(MapLocation curLoc, RobotInfo[] neighbors) {
  }
}
