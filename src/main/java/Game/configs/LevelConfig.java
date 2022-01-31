package Game.configs;

import Game.Vector2Serial;
import Game.configs.ItemConfig;
import org.dyn4j.geometry.Vector2;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class LevelConfig implements Serializable {
    public List<ItemConfig> itemConfigs;
    public List<Vector2Serial> target; // narazie zakładam że targetem jest dotarcie kulki
                     // do danego miejsca (prostokąt opisany przez 2 wektory)


    public LevelConfig(List<ItemConfig> itemConfigs, List<Vector2Serial> target) {
        this.itemConfigs = itemConfigs;
        this.target = target;
    }
}
