package Game.configs;

import Game.Vector2Serial;

import java.io.Serializable;
import java.util.List;

public class LevelConfig implements Serializable {
    public List<ItemConfig> itemConfigs;
    public List<TargetConfig> targetConfigs;


    public LevelConfig(List<ItemConfig> itemConfigs, List<TargetConfig> targets) {
        this.itemConfigs = itemConfigs;
        this.targetConfigs = targets;
    }
}
