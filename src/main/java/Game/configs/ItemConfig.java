package Game.configs;

import Game.configs.BodyConfig;
import Game.configs.JointType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemConfig implements Serializable {
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
