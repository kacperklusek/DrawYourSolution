package Game.configs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LevelConfig implements Serializable {
    public List<ItemConfig> itemConfigs;
    public List<TargetConfig> targetConfigs;
    public List<ConstraintConfig> constraintConfigs;

    public LevelConfig(List<ItemConfig> itemConfigs,
                       List<TargetConfig> targets,
                       List<ConstraintConfig> constraintConfigs) {
        this.itemConfigs = itemConfigs;
        this.targetConfigs = targets;
        this.constraintConfigs = constraintConfigs;
    }

    public LevelConfig (List<ItemConfig> itemConfigs,
                        List<TargetConfig> targets) {
        this.itemConfigs = itemConfigs;
        this.targetConfigs = targets;
        this.constraintConfigs = new ArrayList<>();
    }
}
