package team137.ai.units;

import static battlecode.common.Direction.*;
import static battlecode.common.RobotType.*;
import static battlecode.common.MapLocation.getAllMapLocationsWithinRadiusSq;

import battlecode.common.*;
import team137.ai.actions.MoveAction;
import team137.ai.actions.archon.ClearAction;
import team137.ai.actions.priority.Priority;
import team137.ai.actions.priority.units.ArchonPriorityMap;

import java.util.Random;

public class Archon extends BaseUnit {

  private final ArchonPriorityMap priorityMap;
  private final Team team;
  private final Random rand;

  public Archon(RobotController rc) {
    super(rc);
    priorityMap = new ArchonPriorityMap();
    team = rc.getTeam();
    rand = new Random(rc.getID());
  }

  @Override
  public boolean update() {

    MapLocation curLoc = rc.getLocation();

    checkClearRubble(curLoc);
    checkParts(curLoc);

    MapLocation[] tiles = getAllMapLocationsWithinRadiusSq(curLoc, ARCHON.sensorRadiusSquared);

    checkWallAvoidance(curLoc, tiles);

    RobotInfo[] neighbors = rc.senseNearbyRobots();

    checkNeutrals(curLoc, neighbors);
    checkEvasion(curLoc, neighbors);


//    for(MapLocation loc : neighbors) {
//
//      Direction dirToLoc = curLoc.directionTo(loc);
//      if(! safeOnTheMap(loc)) {
//        double distSqToLoc = curLoc.distanceSquaredTo(loc);
//        if(distSqToLoc < nearWallDistSquared) {
//          nearWallDistSquared = distSqToLoc;
//          nearWallDir = dirToLoc;
//          nearWallLoc = loc;
//        }
//      }
//      if(loc.isAdjacentTo(curLoc)) {
//        double rubbleAmt = rc.senseRubble(loc);
//        if(rubbleAmt < minAdjacentRubble && rc.canMove(dirToLoc)) {
//          minAdjacentRubbleLoc = loc;
//          minAdjacentRubble = rubbleAmt;
//          minAdjacentRubbleDir = dirToLoc;
//        }
//      }
//      double partsAmt = rc.senseParts(loc);
//      if(partsAmt > maxParts) {
//        maxParts = partsAmt;
//        maxPartsLoc = loc;
//        maxPartsDir = dirToLoc;
//      }
//      RobotInfo robotInfo = safeSenseRobotAtLocation(loc);
//      if(robotInfo != null) {
//        double distSqToLoc = curLoc.distanceSquaredTo(loc);
//        if(robotInfo.team == Team.NEUTRAL) {
//          if(distSqToLoc < neutralDistanceSquared) {
//            neutralDistanceSquared = distSqToLoc;
//            neutralDir = dirToLoc;
//            neutralLoc = loc;
//          }
//        }
//        else if(distSqToLoc < 4 && (robotInfo.type == ZOMBIEDEN || robotInfo.team != team)) {
//          if(aveEvadeLoc == null) {
//            aveEvadeLoc = new MapLocation(loc.x, loc.y);
//          }
//          else {
//            aveEvadeLoc.add(loc.x, loc.y);
//          }
//          evadeCount++;
//        }
//      }
//    }
//
//    // debug
//    rc.setIndicatorString(0, "partsL: "+ maxPartsLoc);
//    rc.setIndicatorString(1, "neutralL: "+neutralLoc);
//    rc.setIndicatorString(2, "rubbleL: "+minAdjacentRubbleLoc);
//
//    if(evadeCount > 0) {
//      aveEvadeLoc = new MapLocation(aveEvadeLoc.x / evadeCount, aveEvadeLoc.y / evadeCount);
//      Direction dirFromLoc = curLoc.directionTo(aveEvadeLoc).opposite();
//      if(safeMoveProximate(dirFromLoc, rand) != OMNI) {
//        return true;
//      }
//    }
//
//    if(neutralLoc != null) {
//      safeActivate(neutralLoc);
//    }
//
//    if(maxParts > 0) {
//      if(safeMoveProximate(maxPartsDir, rand) != OMNI) {
//        return true;
//      }
//    }
//
//    if(minAdjacentRubble < Double.MAX_VALUE) {
//      if(safeMoveProximate(minAdjacentRubbleDir, rand) != OMNI) {
//        return true;
//      }
//    }

    rc.setIndicatorString(0, priorityMap.toString());

    priorityMap.update();
    return true;
  }

  public void checkClearRubble(MapLocation curLoc) {
    double curRubble = rc.senseRubble(curLoc);

    // see if we are stuck!
    if(curRubble > 100) {
      priorityMap.putPriority(ClearAction.OMNI, Priority.LEVEL2_PRIORITY);
    }
  }

  public void checkParts(MapLocation curLoc) {
    double curParts = rc.senseParts(curLoc);

    // max parts
    MapLocation maxPartsLoc = curLoc;
    Direction maxPartsDir = OMNI;
    double maxParts = 0;
  }

  public void checkMinRubblePath(MapLocation curLoc) {

    // min rubble
    MapLocation minAdjacentRubbleLoc = curLoc;
    Direction minAdjacentRubbleDir = OMNI;
    double minAdjacentRubble = Double.MAX_VALUE;
  }

  public void checkWallAvoidance(MapLocation curLoc, MapLocation[] tiles) {

    // nearest wall
    MapLocation nearWallLoc = null;
    Direction nearWallDir = OMNI;
    double nearWallDistSquared = Double.MAX_VALUE;

    for(MapLocation loc : tiles) {
      if(! safeOnTheMap(loc)) {
        Direction dirFromLoc = curLoc.directionTo(loc).opposite();
        MoveAction action = MoveAction.fromDirection(dirFromLoc);
        double priority = priorityMap.getPriority(action);
        priority += Priority.DEFAULT_PRIORITY.value;
        priorityMap.putPriority(action, priority);
      }
    }
  }

  public void checkNeutrals(MapLocation curLoc, RobotInfo[] neighbors) {
  }

  public void checkEvasion(MapLocation curLoc, RobotInfo[] neighbors) {
  }
}
