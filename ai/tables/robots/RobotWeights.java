package team137.ai.tables.robots;

public class RobotWeights extends RobotTable<Double> {

  protected RobotWeights(Double[] weights) {
    super(weights);
  }

  public static RobotTable<Double> uniformWeights() {
    return new UniformRobotWeights();
  }

  public static RobotTable<Double> uniformWeights(Double value) {
    return new UniformRobotWeights(value);
  }
}