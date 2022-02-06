package Game;

import Game.configs.ConstraintConfig;
import Game.configs.TargetConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectiveChecker implements BoardStateListener, ObjectiveStatePublisher, SimpleObjectiveListener {

    Map<Integer, List<TargetConfig>> targets = new HashMap<>();
    Map<Integer, List<SimpleObjectiveChecker>> simpleObjectiveCheckers = new HashMap<>();
    // below is the map in which State of targets with given Integer ID is being held
    Map<Integer, Boolean> simpleObjectiveStates = new HashMap<>();
    Map<Integer, List<BodyWrapper>> trackedBodies = new HashMap<>();

    List<ObjectiveListener> objectiveListeners = new ArrayList<>();

    @Override
    public void worldUpdate(WorldEvent e) {
        if (e.getType() == WorldEvent.Type.BODY_ADDED) {
            BodyWrapper bodyWrapper = e.getBodyWrapper();
            Integer targetID = bodyWrapper.getTargetID();
            if (targetID != null) {
                addTrackedBody(bodyWrapper);
                for (SimpleObjectiveChecker simpleObjectiveChecker: simpleObjectiveCheckers.get(targetID)){
                    bodyWrapper.addBodyListener(simpleObjectiveChecker);
                }
            }
        }
    }

    @Override
    public void targetAdded(TargetConfig targetConfig) {
        if(!targets.containsKey(targetConfig.ID())){
            targets.put(targetConfig.ID(), new ArrayList<>());
        }
        targets.get(targetConfig.ID()).add(targetConfig);
        updateObjectiveCheckers(targetConfig);
    }

    private void updateObjectiveCheckers (TargetConfig targetConfig){
        SimpleObjectiveChecker simpleOC = new SimpleObjectiveChecker(targetConfig);
        simpleOC.addSimpleObjectiveListener(this);
        if (!simpleObjectiveCheckers.containsKey(targetConfig.ID())) {
            simpleObjectiveCheckers.put(targetConfig.ID(), new ArrayList<>());
            simpleObjectiveStates.put(targetConfig.ID(), false);
        }
        simpleObjectiveCheckers.get(targetConfig.ID()).add(simpleOC);
    }

    @Override
    public void constraintAdded(ConstraintConfig constraintConfig) {}

    @Override
    public void addObjectiveStateListener(ObjectiveListener listener) {
        objectiveListeners.add(listener);
    }

    @Override
    public void removeObjectiveStateListener(ObjectiveListener listener) {
        objectiveListeners.remove(listener);
    }

    @Override
    public void notifyObjectivesSatisfied() {
        for(ObjectiveListener listener: objectiveListeners){
            listener.objectiveSatisfied();
        }
    }

    public void addTrackedBody(BodyWrapper bodyWrapper) {
        if (! trackedBodies.containsValue(bodyWrapper.getTargetID())){
            trackedBodies.put(bodyWrapper.getTargetID(), new ArrayList<>());
        }
        trackedBodies.get(bodyWrapper.getTargetID()).add(bodyWrapper);
    }

    @Override
    public void simpleObjectiveSatisfied(Integer id) {
        simpleObjectiveStates.put(id, true);
        System.out.println(id);
        checkObjectives();
    }

    private void checkObjectives() {
        if (! simpleObjectiveStates.containsValue(false)) {
            notifyObjectivesSatisfied();
        }
    }
}
