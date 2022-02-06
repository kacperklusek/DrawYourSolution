package Game;

import Game.configs.TargetConfig;
import Game.gui.BoardGui;
import org.dyn4j.dynamics.Body;

import java.util.ArrayList;
import java.util.List;

public class SimpleObjectiveChecker implements BodyListener, SimpleObjectivePublisher{
    List<SimpleObjectiveListener> objectiveListeners = new ArrayList<>();

    TargetConfig targetConfig;

    public SimpleObjectiveChecker(TargetConfig targetConfig) {
        this.targetConfig = targetConfig;
    }

    @Override
    public void bodyUpdate(BodyEvent e) {
        if (e.getType() == BodyEvent.Type.BODY_UPDATE) {
            Body body = e.getSource();

            Integer targetID = e.getTargetID();
            if (targetID == null) {
                System.out.println("should have target id but does not have");
                return;
            }

            Vector2Serial position = new Vector2Serial(new Vector2Serial(body.getTransform().getTranslation()));
            position = new Vector2Serial(position.x, - position.y);
            position = position.add(new Vector2Serial(
                    BoardGui.BOARD_OFFSET.x / BoardGui.SCALE,
                    BoardGui.BOARD_OFFSET.y / BoardGui.SCALE));

            if (targetConfig.ID() != targetID) {
                System.out.println("Bad target ID!");
                return;
            }
            switch (targetConfig.shape()) {
                case RECTANGLE -> {
                    if (position.follows(targetConfig.position()) &&
                        position.precedes(targetConfig.position().add(targetConfig.size()))) {
                            notifyObjectiveSatisfied();
                        }
                }
                case CIRCLE -> {
                    if (position.distance(targetConfig.position()) <= targetConfig.size().x) {
                        notifyObjectiveSatisfied();
                    }
                }
            }
        }
    }

    @Override
    public void addSimpleObjectiveListener(SimpleObjectiveListener listener) {
        this.objectiveListeners.add(listener);
    }

    @Override
    public void removeSimpleObjectiveListener(SimpleObjectiveListener listener) {
        this.objectiveListeners.remove(listener);
    }

    @Override
    public void notifyObjectiveSatisfied() {
        for (SimpleObjectiveListener listener: objectiveListeners) {
            listener.simpleObjectiveSatisfied(targetConfig.ID());
        }
    }
}
