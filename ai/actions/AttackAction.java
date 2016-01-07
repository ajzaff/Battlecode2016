package team137.ai.actions;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class AttackAction extends BaseAction {

  protected AttackAction(String repr) {
    super(repr);
  }

  @Override
  public boolean act(RobotController rc) throws GameActionException {
    return true;
  }
}
