package Game;

import Game.configs.ConstraintConfig;
import Game.configs.TargetConfig;

public interface BoardStatePublisher {
    public void addBoardStateListener(BoardStateListener listener);

    public void removeBoardStateListener(BoardStateListener listener);

    public void notifyAllWorldUpdate(WorldEvent event);

    public void notifyAllTargetAdded(TargetConfig targetConfig);

    public void notifyAllConstraintAdded(ConstraintConfig constraintConfig);
}
