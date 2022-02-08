package Game;

import Game.configs.*;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ExampleLevelsGenerator {
    public static LevelConfig generateLevelSimple(){
        ItemConfig objectiveCircle = new ItemConfig();
        objectiveCircle.addBodyConfig(new BodyConfig(
                ShapeType.CIRCLE,
                new Vector2(8, -8),
                new Vector2(1, 1),
                0,
                MassType.NORMAL,
                0
        ));
        ItemConfig objectiveCircle2 = new ItemConfig();
        objectiveCircle2.addBodyConfig(new BodyConfig(
                ShapeType.CIRCLE,
                new Vector2(13, -8),
                new Vector2(1, 1),
                0,
                MassType.NORMAL,
                0
        ));
        ItemConfig objectiveCircle3 = new ItemConfig();
        objectiveCircle3.addBodyConfig(new BodyConfig(
                ShapeType.CIRCLE,
                new Vector2(11, -8),
                new Vector2(1, 1),
                0,
                MassType.NORMAL,
                1
        ));
        List<ItemConfig> itemList = new ArrayList<>();
        itemList.add(objectiveCircle);
        itemList.add(objectiveCircle2);
        itemList.add(objectiveCircle3);

        List<TargetConfig> targetConfigs = new ArrayList<>();
        targetConfigs.add(new TargetConfig(
                ShapeType.RECTANGLE,
                new Vector2Serial(22, 17),
                new Vector2Serial(5, 5),
                0
        ));
        targetConfigs.add(new TargetConfig(
                ShapeType.RECTANGLE,
                new Vector2Serial(12, 17),
                new Vector2Serial(5, 5),
                1
        ));
        List<ConstraintConfig> constraintConfigs = new ArrayList<>();
        constraintConfigs.add( new ConstraintConfig(
                ShapeType.RECTANGLE,
                new Vector2Serial(22, 2),
                new Vector2Serial(5, 14.5)
        ));

       return new LevelConfig(itemList, targetConfigs, constraintConfigs);
    }

    public static LevelConfig generateLevelSorting(){
        int targetsNumber = 6;
        List<ItemConfig> itemList = new ArrayList<>();
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < targetsNumber; i++){
            ItemConfig objectiveCircle = new ItemConfig();
            objectiveCircle.addBodyConfig(new BodyConfig(
                    ShapeType.CIRCLE,
                    new Vector2(2+4.5*i, -3),
                    new Vector2(1, 1),
                    0,
                    MassType.NORMAL,
                    i
            ));
            itemList.add(objectiveCircle);
            indexList.add(i);
        }

        Collections.shuffle(indexList, new Random());
        List<TargetConfig> targetConfigs = new ArrayList<>();
        for(int i = 0; i < targetsNumber; i++){
            targetConfigs.add(new TargetConfig(
                    ShapeType.RECTANGLE,
                    new Vector2Serial(1+4.333*i, 19),
                    new Vector2Serial(4.333, 2.94),
                    indexList.get(i)
            ));
        }
        List<ConstraintConfig> constraintConfigs = new ArrayList<>();
        return new LevelConfig(itemList, targetConfigs, constraintConfigs);
    }

    public static LevelConfig generateLevelTrampoline(){
        ItemConfig trampoline = new ItemConfig();
        BodyConfig circle = new BodyConfig(
                ShapeType.CIRCLE,
                new Vector2(3, -13),
                new Vector2(1, 1),
                0,
                MassType.INFINITE
        );
        BodyConfig plank = new BodyConfig(
                ShapeType.RECTANGLE,
                new Vector2(5, -14),
                new Vector2(6, 0.1),
                Math.toDegrees(Math.PI/20),
                MassType.NORMAL
        );
        trampoline.addBodyConfig(circle);
        trampoline.addBodyConfig(plank);
        trampoline.setJointType(JointType.SPRING);

        ItemConfig objectiveCircle = new ItemConfig();
        objectiveCircle.addBodyConfig(new BodyConfig(
                ShapeType.CIRCLE,
                new Vector2(8, -8),
                new Vector2(1, 1),
                0,
                MassType.NORMAL,
                0
        ));
        ItemConfig objectiveCircle2 = new ItemConfig();
        objectiveCircle2.addBodyConfig(new BodyConfig(
                ShapeType.CIRCLE,
                new Vector2(13, -8),
                new Vector2(1, 1),
                0,
                MassType.NORMAL,
                0
        ));
        ItemConfig objectiveCircle3 = new ItemConfig();
        objectiveCircle3.addBodyConfig(new BodyConfig(
                ShapeType.CIRCLE,
                new Vector2(11, -8),
                new Vector2(1, 1),
                0,
                MassType.NORMAL,
                1
        ));
        List<ItemConfig> itemList = new ArrayList<>();
        itemList.add(objectiveCircle);
        itemList.add(objectiveCircle2);
        itemList.add(objectiveCircle3);
        itemList.add(trampoline);

        List<TargetConfig> targetConfigs = new ArrayList<>();
        targetConfigs.add(new TargetConfig(
                ShapeType.RECTANGLE,
                new Vector2Serial(22, 17),
                new Vector2Serial(5, 5),
                0
        ));
        targetConfigs.add(new TargetConfig(
                ShapeType.RECTANGLE,
                new Vector2Serial(12, 17),
                new Vector2Serial(5, 5),
                1
        ));
        List<ConstraintConfig> constraintConfigs = new ArrayList<>();
        constraintConfigs.add( new ConstraintConfig(
                ShapeType.RECTANGLE,
                new Vector2Serial(22, 2),
                new Vector2Serial(5, 14.5)
        ));
        constraintConfigs.add( new ConstraintConfig(
                ShapeType.RECTANGLE,
                new Vector2Serial(10, 12),
                new Vector2Serial(9, 7)
        ));

        return new LevelConfig(itemList, targetConfigs, constraintConfigs);
    }
}