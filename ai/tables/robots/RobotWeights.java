package team137.ai.tables.robots;

import java.util.Arrays;

public class RobotWeights extends RobotTable<Double> {

  protected RobotWeights(Double[] weights) {
    super(weights);
  }

  public static RobotWeights newInstance(Double defaultValue) {
    Double[] values = new Double[12];
    Arrays.fill(values, defaultValue);
    return new RobotWeights(values);
  }

  public static RobotWeights newInstance() {
    return newInstance(1.);
  }

  public static RobotWeights uniformWeights() {
    return new UniformRobotWeights();
  }

  public static RobotWeights uniformWeights(Double value) {
    return new UniformRobotWeights(value);
  }
}