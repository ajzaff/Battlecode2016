package team137.ai.tables.robots;

import battlecode.common.RobotType;

public class NullFleeWeights extends FleeWeights {

  private static NullFleeWeights INSTANCE = new NullFleeWeights();

  protected NullFleeWeights() {
    super(null);
  }

  @Override
  public FleeWeights.Row get(RobotType type) {
    return FleeWeights.Row.getNull();
  }

  public static NullFleeWeights getInstance() {
    return INSTANCE;
  }
}
