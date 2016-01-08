package team137.ai.tables.robots;

import battlecode.common.RobotType;

public class UniformRobotWeights extends RobotWeights {

  private static final UniformRobotWeights INSTANCE;

  static {
    INSTANCE = new UniformRobotWeights();
  }

  private final Double value;

  protected UniformRobotWeights(Double value) {
    super(null);
    this.value = value;
  }

  protected UniformRobotWeights() {
    this(1.);
  }

  @Override
  public Double get(RobotType type) {
    return value;
  }

  public static UniformRobotWeights getInstance() {
    return INSTANCE;
  }
}
