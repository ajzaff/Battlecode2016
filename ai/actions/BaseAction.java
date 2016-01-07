package team137.ai.actions;

public abstract class BaseAction implements Action {

  protected String repr;

  protected BaseAction(String repr) {
    this.repr = repr;
  }

  @Override
  public String toString() {
    return repr;
  }
}
