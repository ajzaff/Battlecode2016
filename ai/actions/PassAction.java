package team137.ai.actions;

import battlecode.common.RobotController;

public final class PassAction extends BaseAction {

  public static final PassAction INSTANCE = new PassAction();

  private PassAction() {
    super();
  }

  @Override
  public boolean act(RobotController rc) {
    return true;
  }

  public static PassAction getInstance() {
    return INSTANCE;
  }
}
