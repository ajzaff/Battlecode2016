package team137.ai.units;

import static battlecode.common.Direction.*;
import static battlecode.common.RobotType.*;
import static battlecode.common.MapLocation.getAllMapLocationsWithinRadiusSq;

import battlecode.common.*;

import java.util.Random;

public class Archon extends BaseUnit {

  private final Team team;
  private final Random rand;
  public Archon(RobotController rc) {
    super(rc);
    team = rc.getTeam();
    rand = new Random(rc.getID());
  }

  @Override
  public boolean update() {
    MapLocation curLoc = rc.getLocation();
    MapLocation[] neighbors = getAllMapLocationsWithinRadiusSq(curLoc, ARCHON.sensorRadiusSquared);

    double curRubble = rc.senseRubble(curLoc);

    if(curRubble > 100) {
      if(safeClearRubble(OMNI)) {
        return true;
      }
    }

    double curParts = rc.senseParts(curLoc);

    // max parts
    MapLocation maxPartsLoc = curLoc;
    Direction maxPartsDir = OMNI;
    double maxParts = 0;

    // min rubble
    MapLocation minAdjacentRubbleLoc = curLoc;
    Direction minAdjacentRubbleDir = OMNI;
    double minAdjacentRubble = Double.MAX_VALUE;

    // neutral location
    MapLocation neutralLoc = null;
    Direction neutralDir = OMNI;
    double neutralDistanceSquared = Double.MAX_VALUE;

    // evasion
    MapLocation aveEvadeLoc = null;
    int evadeCount = 0;

    // nearest wall (avoid walls)
    MapLocation nearWallLoc = null;
    Direction nearWallDir = OMNI;
    double nearWallDistSquared = Double.MAX_VALUE;

    for(MapLocation loc : neighbors) {

      Direction dirToLoc = curLoc.directionTo(loc);
      if(! safeOnTheMap(loc)) {
        double distSqToLoc = curLoc.distanceSquaredTo(loc);
        if(distSqToLoc < nearWallDistSquared) {
          nearWallDistSquared = distSqToLoc;
          nearWallDir = dirToLoc;
          nearWallLoc = loc;
        }
      }
      if(loc.isAdjacentTo(curLoc)) {
        double rubbleAmt = rc.senseRubble(loc);
        if(rubbleAmt < minAdjacentRubble && rc.canMove(dirToLoc)) {
          minAdjacentRubbleLoc = loc;
          minAdjacentRubble = rubbleAmt;
          minAdjacentRubbleDir = dirToLoc;
        }
      }
      double partsAmt = rc.senseParts(loc);
      if(partsAmt > maxParts) {
        maxParts = partsAmt;
        maxPartsLoc = loc;
        maxPartsDir = dirToLoc;
      }
      RobotInfo robotInfo = safeSenseRobotAtLocation(loc);
      if(robotInfo != null) {
        double distSqToLoc = curLoc.distanceSquaredTo(loc);
        if(robotInfo.team == Team.NEUTRAL) {
          if(distSqToLoc < neutralDistanceSquared) {
            neutralDistanceSquared = distSqToLoc;
            neutralDir = dirToLoc;
            neutralLoc = loc;
          }
        }
        else if(distSqToLoc < 4 && (robotInfo.type == ZOMBIEDEN || robotInfo.team != team)) {
          if(aveEvadeLoc == null) {
            aveEvadeLoc = new MapLocation(loc.x, loc.y);
          }
          else {
            aveEvadeLoc.add(loc.x, loc.y);
          }
          evadeCount++;
        }
      }
    }

    rc.setIndicatorString(0, "partsL: "+ maxPartsLoc);
    rc.setIndicatorString(1, "neutralL: "+neutralLoc);
    rc.setIndicatorString(2, "rubbleL: "+minAdjacentRubbleLoc);

    if(evadeCount > 0) {
      aveEvadeLoc = new MapLocation(aveEvadeLoc.x / evadeCount, aveEvadeLoc.y / evadeCount);
      Direction dirFromLoc = curLoc.directionTo(aveEvadeLoc).opposite();
      if(safeMoveProximate(dirFromLoc, rand) != OMNI) {
        return true;
      }
    }

    if(neutralLoc != null) {
      safeActivate(neutralLoc);
    }

    if(maxParts > 0) {
      if(safeMoveProximate(maxPartsDir, rand) != OMNI) {
        return true;
      }
    }

    if(minAdjacentRubble < Double.MAX_VALUE) {
      if(safeMoveProximate(minAdjacentRubbleDir, rand) != OMNI) {
        return true;
      }
    }

    return false;
  }

  public void pathMaximumParts(double[] actionPriorities) {

  }

  public boolean safeActivate(MapLocation loc) {
    try {
      if(rc.isCoreReady() && rc.getLocation().isAdjacentTo(loc)) {
        rc.activate(loc);
        return true;
      }
    }
    catch(GameActionException e) {
      e.printStackTrace();
    }
    return false;
  }
}
