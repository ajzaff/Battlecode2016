package team137.ai.tables;

import battlecode.common.RobotType;

public class RobotTable<V> {

  private static final Double[] UNIFORM_DOUBLES;

  static {
    UNIFORM_DOUBLES = new Double[12];
    for(int i=0; i < 12; i++) {
      UNIFORM_DOUBLES[i] = 1.;
    }
  }

  private final V[] prefs;

  protected RobotTable(V[] prefs) {
    this.prefs = prefs;
  }

  public V get(RobotType type) {
    return prefs[type.ordinal()];
  }

  public static RobotTable uniformWeights() {
    return new RobotTable<>(UNIFORM_DOUBLES);
  }

  public static <V> RobotTable newTable(V[] prefs) {
    assert prefs.length == 12;
    return new RobotTable<>(prefs);
  }
}
