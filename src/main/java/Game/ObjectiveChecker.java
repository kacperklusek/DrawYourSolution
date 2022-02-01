package Game;

import Game.configs.BodyConfig;
import Game.configs.ConstraintConfig;
import Game.configs.TargetConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectiveChecker implements BoardStateListener{
//    List<TargetConfig> targets = new ArrayList<>();
    Map<Integer, List<TargetConfig>> targets = new HashMap<>();
    Map<Integer, List<BodyWrapper>> trackedBodies = new HashMap<>();

    @Override
    public void worldUpdate(WorldEvent e) {

    }

    @Override
    public void targetAdded(TargetConfig targetConfig) {
        if(!targets.containsKey(targetConfig.ID())){
            targets.put(targetConfig.ID(), new ArrayList<>());
        }
        targets.get(targetConfig.ID()).add(targetConfig);
    }

    @Override
    public void constraintAdded(ConstraintConfig constraintConfig) {}
}
