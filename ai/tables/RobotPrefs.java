package team137.ai.tables;

import battlecode.common.RobotType;

public final class RobotPrefs {

  private static final double[] DEFAULT_PREFS;

  static {
    DEFAULT_PREFS = new double[12];
    for(int i=0; i < 12; i++) {
      DEFAULT_PREFS[i] = 1;
    }
  }

  private final double[] prefs;

  protected RobotPrefs() {
    prefs = DEFAULT_PREFS;
  }

  protected RobotPrefs(double[] prefs) {
    this.prefs = prefs;
  }

  public double getPref(RobotType type) {
    return prefs[type.ordinal()];
  }

  public static RobotPrefs defaultPrefs() {
    return new RobotPrefs();
  }

  public static RobotPrefs newPrefs(double... prefs) {
    assert prefs.length == 12;
    return new RobotPrefs(prefs);
  }
}
