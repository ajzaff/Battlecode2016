package team137.ai.tables.robots;

import battlecode.common.RobotType;

public class FleeWeights extends RobotTable<FleeWeights.Row> {

  private static final FleeWeights.Row[] DEFAULT_FLEE_TABLE;

  static {

    ////////////// INIT DEFAULT FLEE

    DEFAULT_FLEE_TABLE = new FleeWeights.Row[12];

    // these don`t deal damage (directly) so ignore these...
//    double aveOM = RobotType.STANDARDZOMBIE.getOutbreakMultiplier(1500);
//    DEFAULT_FLEE_TABLE[ARCHON.ordinal()]    = FleeWeights.Row.fromRobotDefault(ARCHON);
//    DEFAULT_FLEE_TABLE[SCOUT.ordinal()]     = FleeWeights.Row.fromRobotDefault(SCOUT);
//    DEFAULT_FLEE_TABLE[TTM.ordinal()]       = FleeWeights.Row.fromRobotDefault(TTM);
//    DEFAULT_FLEE_TABLE[ZOMBIEDEN.ordinal()] = FleeWeights.Row.fromRobotDefault(ZOMBIEDEN);
//    //
//    DEFAULT_FLEE_TABLE[STANDARDZOMBIE.ordinal()] = FleeWeights.Row.fromRobotDefault(STANDARDZOMBIE);
//    DEFAULT_FLEE_TABLE[RANGEDZOMBIE.ordinal()] = FleeWeights.Row.fromRobotDefault(RANGEDZOMBIE);
//    DEFAULT_FLEE_TABLE[FASTZOMBIE.ordinal()] = FleeWeights.Row.fromRobotDefault(FASTZOMBIE);
//    DEFAULT_FLEE_TABLE[BIGZOMBIE.ordinal()] = FleeWeights.Row.fromRobotDefault(BIGZOMBIE);
//    //
//    DEFAULT_FLEE_TABLE[SOLDIER.ordinal()] = FleeWeights.Row.fromRobotDefault(SOLDIER);
//    DEFAULT_FLEE_TABLE[GUARD.ordinal()] = FleeWeights.Row.fromRobotDefault(GUARD);
//    DEFAULT_FLEE_TABLE[VIPER.ordinal()] = FleeWeights.Row.fromRobotDefault(VIPER);
//    DEFAULT_FLEE_TABLE[TURRET.ordinal()] = FleeWeights.Row.fromRobotDefault(TURRET);

    for(int i=0; i < 12; i++) {
      DEFAULT_FLEE_TABLE[i] = Row.newInstance(Row.DEFAULT_ROW);
    }
  }

  protected FleeWeights(Row[] weights) {
    super(weights);
  }

  public static class Row {

    private static final Row DEFAULT_ROW = new Row(-1, -.25);
    private static final Row NULL_ROW = new Row(0, 0);

    public final double x0;
    public final double x1;

    protected Row(double x0, double x1) {
      this.x0 = x0;
      this.x1 = x1;
    }

    public static Row getDefault() {
      return DEFAULT_ROW;
    }

    public static Row newInstance(double x0, double x1) {
      return new Row(x0, x1);
    }

    public static Row newInstance(Row row) {
      return new Row(row.x0, row.x1);
    }

//    public static Row fromRobotDefault(RobotType type) {
//      double x0 = 0.;
//      double x1 = 0.;
//      if(type.canAttack()) {
//        x0 = -.3 * type.attackPower;
//        x1 = -.3 * (50 - type.attackRadiusSquared);
//      }
//      if(type == RobotType.VIPER) {
//        x0 = -1;
//        x1 = -.05;
//      }
//      if(type == RobotType.ZOMBIEDEN) {
//        x0 = -.2;
//        x1 = -.01;
//      }
//      if(type.isZombie) {
//        x0 *= type.getOutbreakMultiplier(1500);
//        x1 *= type.getOutbreakMultiplier(1500);
//      }
//      return Row.newInstance(x0, x1);
//    }
//
    public static Row getNull() {
      return NULL_ROW;
    }
  }

  @Override
  public Row get(RobotType type) {
    if(type.isZombie) {
      // TODO: add zombie outbreak factor here.
      return super.get(type);
    }
    else {
      return super.get(type);
    }
  }

  public static FleeWeights newInstance() {
    return new FleeWeights(DEFAULT_FLEE_TABLE);
  }

  public static FleeWeights getDefault() {
    return DefaultFleeWeights.getInstance();
  }

  public static FleeWeights getNull() {
    return NullFleeWeights.getInstance();
  }
}
