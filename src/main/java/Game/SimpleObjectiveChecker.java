package Game;

import Game.configs.ConstraintConfig;
import Game.configs.TargetConfig;
import Game.gui.BoardGui;
import Game.gui.Rectangle;
import jdk.swing.interop.DispatcherWrapper;
import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;
import org.dyn4j.world.listener.BoundsListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SimpleObjectiveChecker implements BoardStateListener, BodyListener, ObjectiveStatePublisher{
    List<ObjectiveListener> objectiveListeners = new ArrayList<>();

    List<TargetConfig> targets = new ArrayList<>();
//    List<BodyWrapper> trackedBodies = new ArrayList<>();

    @Override
    public void worldUpdate(WorldEvent e) {
        if (e.getType() == WorldEvent.Type.BODY_ADDED) {
            // filter which body you want to listen
            e.getBody().addBodyListener(this);
        }
    }

    @Override
    public void targetAdded(TargetConfig targetConfig) {
        targets.add(targetConfig);
    }

    @Override
    public void constraintAdded(ConstraintConfig constraintConfig) {}

    @Override
    public void bodyUpdate(BodyEvent e) {
        if (e.getType() == BodyEvent.Type.BODY_UPDATE) {
            Body body = e.getSource();

            Integer targetID = e.getTargetID();
            if (targetID == null) {
                return;
            }


            Vector2Serial position = new Vector2Serial(new Vector2Serial(body.getTransform().getTranslation()));
            position = new Vector2Serial(position.x, - position.y);
            position = position.add(new Vector2Serial(
                    BoardGui.BOARD_OFFSET.x / BoardGui.SCALE,
                    BoardGui.BOARD_OFFSET.y / BoardGui.SCALE));

            for (TargetConfig target: targets) {
                if (target.ID() != targetID) {
                    continue;
                }
                switch (target.shape()) {
                    case RECTANGLE -> {
                        if (position.follows(target.position()) &&
                            position.precedes(target.position().add(target.size()))) {
                                notifyObjectiveSatisfied();
                            }
                    }
                    case CIRCLE -> {
                        if (position.distance(target.position()) <= target.size().x) {
                            notifyObjectiveSatisfied();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void addObjectiveStateListener(ObjectiveListener listener) {
        this.objectiveListeners.add(listener);
    }

    @Override
    public void removeObjectiveStateListener(ObjectiveListener listener) {
        this.objectiveListeners.remove(listener);
    }

    @Override
    public void notifyObjectiveSatisfied() {
        for (ObjectiveListener listener: objectiveListeners) {
            listener.objectiveSatisfied();
        }
    }
}
