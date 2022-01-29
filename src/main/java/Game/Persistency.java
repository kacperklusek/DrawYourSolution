package Game;

import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Persistency {


    //  dopóki nie mamy persystencji na dysku tworze jakiś basic level
    public LevelConfig getLevel(){
        // itemConfigs
        List<ItemConfig> itemConfigs = new ArrayList<>();

        // make bodyConfigs
        ItemConfig groundConfig = new ItemConfig();
        groundConfig.addBodyConfig(new BodyConfig(
                ShapeType.RECTANGLE,
                new Vector2(12.5, -31),
                new Vector2(25, 1),
                0,
                MassType.INFINITE
        ));
        ItemConfig obstacleConfig = new ItemConfig();
        obstacleConfig.addBodyConfig(new BodyConfig(
                ShapeType.RECTANGLE,
                new Vector2(12.5, -25),
                new Vector2(12, 1),
                Math.PI/2,
                MassType.INFINITE
        ));

        itemConfigs.add(obstacleConfig);
        itemConfigs.add(groundConfig);

        //Target
        List<Vector2> target = new ArrayList<>();
        target.add(new Vector2(20, -25));
        target.add(new Vector2(25, -31));

        return new LevelConfig(itemConfigs, target);
    }
}
