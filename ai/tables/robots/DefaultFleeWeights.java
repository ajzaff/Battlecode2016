package team137.ai.tables.robots;

import battlecode.common.RobotType;

public class DefaultFleeWeights extends FleeWeights {

  public static final DefaultFleeWeights INSTANCE = new DefaultFleeWeights();

  protected DefaultFleeWeights() {
    super(null);
  }

  @Override
  public FleeWeights.Row get(RobotType type) {
    return FleeWeights.Row.getDefault();
  }

  public static DefaultFleeWeights getInstance() {
    return INSTANCE;
  }
}
