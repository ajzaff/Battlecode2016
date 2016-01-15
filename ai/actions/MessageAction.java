package team137.ai.actions;

import java.util.Arrays;

public class MessageAction {

  private Integer hashCode;
  public final int range;
  public final int n1;
  public final int n2;

  protected MessageAction(int n1, int n2, int range) {
    this.n1 = n1;
    this.n2 = n2;
    this.range = range;
  }

  @Override
  public boolean equals(Object o) {
    if(! (o instanceof MessageAction)) {
      return false;
    }
    MessageAction action = (MessageAction) o;
    return n1 == action.n1 &&
        n2 == action.n1 &&
        range == action.range;
  }

  @Override
  public int hashCode() {
    if(hashCode == null) {
      hashCode = Arrays.hashCode(new int[] { n1, n2, range });
    }
    return hashCode;
  }

  public static MessageAction newInstance(int n1, int n2, int range) {
    return new MessageAction(n1, n2, range);
  }
}
