package Game;

import org.dyn4j.geometry.Vector2;

import java.util.List;

public class LevelConfig {
    List<ItemConfig> itemConfigs;
    List<Vector2> target; // narazie zakładam że targetem jest dotarcie kulki
                     // do danego miejsca (prostokąt opisany przez 2 wektory)


    public LevelConfig(List<ItemConfig> itemConfigs, List<Vector2> target) {
        this.itemConfigs = itemConfigs;
        this.target = target;
    }
}