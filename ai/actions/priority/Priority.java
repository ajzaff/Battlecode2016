package team137.ai.actions.priority;

public enum Priority {
  FORBID_PRIORITY(0),
  LOWEST_PRIORITY(.1),
  DEFAULT_PRIORITY(1),
  LEVEL2_PRIORITY(2),
  LEVEL4_PRIORITY(4),
  LEVEL8_PRIORITY(8),
  LEVEL16_PRIORITY(16),
  HIGHEST_PRIORITY(32);

  public final double value;

  Priority(double value) {
    this.value = value;
  }
}
