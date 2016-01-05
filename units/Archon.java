package team137.units;

import static battlecode.common.Direction.*;
import static battlecode.common.RobotType.*;
import static battlecode.common.MapLocation.getAllMapLocationsWithinRadiusSq;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class Archon extends BaseUnit {
  public Archon(RobotController rc) {
    super(rc);
  }

  @Override
  public void update() {
    MapLocation curLoc = rc.getLocation();
    MapLocation[] neighbors = getAllMapLocationsWithinRadiusSq(curLoc, ARCHON.sensorRadiusSquared);

    double curRubble = rc.senseRubble(curLoc);

    if(curRubble > 100) {
      safeClearRubble(NONE);
    }

    double curParts = rc.senseParts(curLoc);

    MapLocation maxParts = curLoc;
    MapLocation minRubble = curLoc;

    for(MapLocation loc : neighbors) {

    }
  }

  public boolean safeClearRubble(Direction dir) {
    try {
      if(rc.onTheMap(rc.getLocation().add(dir))) {
        rc.clearRubble(dir);
        return true;
      }
    }
    catch (GameActionException e) {
      e.printStackTrace();
    }
    return false;
  }
}
