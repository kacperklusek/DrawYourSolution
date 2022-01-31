package Game;

import Game.configs.BodyConfig;
import Game.configs.ItemConfig;
import Game.configs.LevelConfig;
import Game.configs.ShapeType;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import java.io.*;
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
        ItemConfig persistentItem = new ItemConfig();
        BodyConfig persistentBody;
        ObjectInputStream objectinputstream = null;
        try {
            FileInputStream streamIn = new FileInputStream("test.dat");
            objectinputstream = new ObjectInputStream(streamIn);
            persistentBody = (BodyConfig) objectinputstream.readObject();
//            List<MyClass> readCase = (List<MyClass>) objectinputstream.readObject();
//            recordList.add(readCase);
//            System.out.println(recordList.get(i));
            persistentItem.addBodyConfig(persistentBody);
            itemConfigs.add(persistentItem);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectinputstream != null) {
                try {
                    objectinputstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        itemConfigs.add(obstacleConfig);

        //Target
        List<Vector2> target = new ArrayList<>();
        target.add(new Vector2(20, -25));
        target.add(new Vector2(25, -31));

        return new LevelConfig(itemConfigs, target);
    }

    public void saveLevel(String levelName, LevelConfig levelConfig){
        try (FileOutputStream fos = new FileOutputStream("test.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            // create a new user object
            BodyConfig bodyConfig = new BodyConfig(ShapeType.RECTANGLE, new Vector2Serial(5,-5), new Vector2Serial(3, 4), 20, MassType.NORMAL);

            // write object to file
            oos.writeObject(bodyConfig);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
