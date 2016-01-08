package team137.ai.tables;

import battlecode.common.Direction;

import java.util.Arrays;

public final class Directions {

  private static final Direction[] cardinalDirections;

  static {
    cardinalDirections = Arrays.copyOfRange(Direction.values(), 0, 8);
  }

  public static Direction[] cardinals() {
    return cardinalDirections;
  }
}
