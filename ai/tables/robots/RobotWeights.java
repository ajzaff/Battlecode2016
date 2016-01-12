package team137.ai.tables.robots;

public class RobotWeights extends RobotTable<Double> {

  protected RobotWeights(Double[] weights) {
    super(weights);
  }

  public static RobotWeights uniformWeights() {
    return new UniformRobotWeights();
  }

  public static RobotWeights uniformWeights(Double value) {
    return new UniformRobotWeights(value);
  }
}