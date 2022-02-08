package Game;

import Game.configs.TargetConfig;
import Game.gui.BoardGui;
import org.dyn4j.dynamics.Body;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleObjectiveChecker implements BodyListener, SimpleObjectivePublisher{
    List<SimpleObjectiveListener> objectiveListeners = new ArrayList<>();
    Map<Body, Boolean> trackedBodies = new HashMap<>();

    TargetConfig targetConfig;

    public SimpleObjectiveChecker(TargetConfig targetConfig) {
        this.targetConfig = targetConfig;
    }

    @Override
    public void bodyUpdate(BodyEvent e) {
        if (e.getType() == BodyEvent.Type.BODY_UPDATE) {
            Body body = e.getSource();

            Vector2Serial position = new Vector2Serial(new Vector2Serial(body.getTransform().getTranslation()));
            position = new Vector2Serial(position.x, - position.y);

            switch (targetConfig.shape()) {
                case RECTANGLE -> {
                    if (position.follows(targetConfig.position()) &&
                        position.precedes(targetConfig.position().add(targetConfig.size()))) {
                            checkObjective(body);
                        }
                    else if (trackedBodies.get(body)){
                            trackedBodies.put(body, false);
                            notifyObjectiveNotSatisfied();
                        }
                }
                case CIRCLE -> {
                    if (position.distance(targetConfig.position()) <= targetConfig.size().x) {
                        checkObjective(body);
                    }
                    else if (trackedBodies.get(body)) {
                        trackedBodies.put(body, false);
                        notifyObjectiveNotSatisfied();
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

    private void checkObjective(Body body) {
        trackedBodies.put(body, true);
        if (! trackedBodies.containsValue(false)) {
            notifyObjectiveSatisfied();
        }
    }

    @Override
    public void notifyObjectiveSatisfied() {
        for (SimpleObjectiveListener listener: objectiveListeners) {
            listener.simpleObjectiveSatisfied(targetConfig.ID());
        }
    }

    @Override
    public void notifyObjectiveNotSatisfied() {
        for (SimpleObjectiveListener listener: objectiveListeners) {
            listener.simpleObjectiveNotSatisfied(targetConfig.ID());
        }
    }

    public void addTrackedBody(BodyWrapper bodyWrapper) {
        trackedBodies.put(bodyWrapper.getBody(), false);
    }
}
