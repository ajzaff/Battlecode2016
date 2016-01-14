package team137.ai.tables;

import battlecode.common.Direction;
import team137.collect.iter.RandomIterator;

import java.util.Arrays;
import java.util.Random;

public final class Directions {

  private static final Direction[] cardinalDirections;

  static {
    cardinalDirections = Arrays.copyOfRange(Direction.values(), 0, 8);
  }

  public static Direction[] cardinals() {
    return cardinalDirections;
  }

  public static RandomIterator<Direction> fairCardinals(Random rand) {
    return RandomIterator.newInstance(rand, cardinalDirections);
  }
}
