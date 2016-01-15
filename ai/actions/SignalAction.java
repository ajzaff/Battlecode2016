package team137.ai.actions;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class SignalAction extends BaseAction {

  public final int range;

  protected SignalAction(int range) {
    super("()");
    this.range = range;
  }

  @Override
  public boolean act(RobotController rc) throws GameActionException {
    rc.broadcastSignal(range);
    return true;
  }

  public static SignalAction newInstance(int range) {
    return new SignalAction(range);
  }
}
