package team137.ai.tables.robots;

import battlecode.common.RobotType;

public abstract class RobotTable<V> {

  private final V[] prefs;

  protected RobotTable(V[] prefs) {
    if(prefs != null && prefs.length != 12) {
      throw new IllegalArgumentException("robot table expected length 12 or null, got " + prefs.length);
    }
    this.prefs = prefs;
  }

  public V put(RobotType type, V value) {
    V res = prefs[type.ordinal()];
    prefs[type.ordinal()] = value;
    return res;
  }

  public V get(RobotType type) {
    return prefs[type.ordinal()];
  }
}
