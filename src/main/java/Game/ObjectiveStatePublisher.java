package Game;

public interface ObjectiveStatePublisher {
    public void addObjectiveStateListener(ObjectiveListener listener);

    public void removeObjectiveStateListener(ObjectiveListener listener);

    public void notifyObjectiveSatisfied();
}
