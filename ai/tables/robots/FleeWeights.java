package team137.ai.tables.robots;

import battlecode.common.RobotType;

public class FleeWeights extends RobotTable<FleeWeights.Row> {

  private static final FleeWeights.Row[] DEFAULT_FLEE_TABLE;

  static {

    ////////////// INIT DEFAULT FLEE

    DEFAULT_FLEE_TABLE = new FleeWeights.Row[12];

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
