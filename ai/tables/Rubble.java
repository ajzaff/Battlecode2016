package team137.ai.tables;

public final class Rubble {

  public enum Type {
    RUBBLE_LOW,
    RUBBLE_MEDIUM,
    RUBBLE_SEVERE
  }

  public static double weight(double[] rubbleMap, double rubble) {
    if(rubble <= 50) {
      return rubbleMap[Type.RUBBLE_LOW.ordinal()];
    }
    if(rubble < 100) {
      return rubbleMap[Type.RUBBLE_MEDIUM.ordinal()];
    }
    return rubbleMap[Type.RUBBLE_SEVERE.ordinal()];
  }

  public static double[] map(double low, double medium, double severe) {
    double[] rubbleMap = new double[3];
    rubbleMap[Type.RUBBLE_LOW.ordinal()] = low;
    rubbleMap[Type.RUBBLE_MEDIUM.ordinal()] = medium;
    rubbleMap[Type.RUBBLE_SEVERE.ordinal()] = severe;
    return rubbleMap;
  }

  public static double[] defaultMap() {
    return map(1, .5, 0);
  }
}
