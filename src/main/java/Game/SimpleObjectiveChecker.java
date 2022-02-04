package Game;

import Game.configs.ConstraintConfig;
import Game.configs.TargetConfig;
import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;

import java.util.ArrayList;
import java.util.List;

public class SimpleObjectiveChecker implements BoardStateListener, BodyListener {
    List<TargetConfig> targets = new ArrayList<>();

    @Override
    public void worldUpdate(WorldEvent e) {

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

            Vector2 position = body.getWorldCenter();
            // TODO check if the position is in one of the targets
        }
    }
}
