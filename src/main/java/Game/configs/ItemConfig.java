package Game.configs;

import Game.configs.BodyConfig;
import Game.configs.JointType;

import java.util.ArrayList;
import java.util.List;

public class ItemConfig {
    public List<BodyConfig> bodyConfigs = new ArrayList<>();
    public JointType jointType;

    public ItemConfig() {
    }

    public void addBodyConfig(BodyConfig bodyConfig){
        this.bodyConfigs.add(bodyConfig);
    }

    public void setJointType (JointType jointType){
        this.jointType = jointType;
    }
}
