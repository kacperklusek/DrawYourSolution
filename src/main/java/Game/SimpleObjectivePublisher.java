package Game;

public interface SimpleObjectivePublisher {
    public void addSimpleObjectiveListener(SimpleObjectiveListener listener);

    public void removeSimpleObjectiveListener(SimpleObjectiveListener listener);

    public void notifyObjectiveSatisfied();
}
