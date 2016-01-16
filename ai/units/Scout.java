package team137.ai.units;

import battlecode.common.RobotController;
import battlecode.common.Team;

public class Scout extends MovableUnit {

  private final Team team;

  public Scout(RobotController rc) {
    super(rc);
    team = rc.getTeam();
  }

  @Override
  public void update() {

  }
}
