package Game;

import Game.configs.BodyConfig;
import Game.configs.ItemConfig;
import Game.configs.LevelConfig;
import Game.configs.ShapeType;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Persistency {


    //  dopóki nie mamy persystencji na dysku tworze jakiś basic level
    public LevelConfig loadLevel(String levelName){
        // itemConfigs
        List<ItemConfig> itemConfigs = new ArrayList<>();

        // make bodyConfigs
        ItemConfig obstacleConfig = new ItemConfig();
        obstacleConfig.addBodyConfig(new BodyConfig(
                ShapeType.RECTANGLE,
                new Vector2(12.5, -10),
                new Vector2(12, 1),
                Math.PI/2,
                MassType.INFINITE
        ));

        itemConfigs.add(obstacleConfig);

        //Target
        List<Vector2> target = new ArrayList<>();
        target.add(new Vector2(20, -25));
        target.add(new Vector2(25, -31));

        return new LevelConfig(itemConfigs, target);
    }

    public void saveLevel(String levelName, LevelConfig levelConfig){

    }
}
