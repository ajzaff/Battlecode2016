package team137.ai.actions;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class MessageAction extends BaseAction {

  private final int hashCode;
  public final int range;
  public final int n1;
  public final int n2;

  protected MessageAction(int n1, int n2, int range) {
    super("(" + n1 + "," + n2 + "," + range + ")");
    this.n1 = n1;
    this.n2 = n2;
    this.range = range;
    hashCode = 29791 + 961 * n1 + 31 * n2 + range;
  }

  @Override
  public boolean act(RobotController rc) throws GameActionException {
    rc.broadcastMessageSignal(n1, n2, range);
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if(! (o instanceof MessageAction)) {
      return false;
    }
    MessageAction action = (MessageAction) o;
    return n1 == action.n1 &&
        n2 == action.n1 &&
        range == action.range;
  }

  @Override
  public int hashCode() {
    return hashCode;
  }

  public static MessageAction newInstance(int n1, int n2, int range) {
    return new MessageAction(n1, n2, range);
  }
}
