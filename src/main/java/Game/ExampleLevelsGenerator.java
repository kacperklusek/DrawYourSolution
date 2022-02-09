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
                new Vector2Serial(1.5, 15.5),
                new Vector2Serial(5, 5),
                0
        ));
        targetConfigs.add(new TargetConfig(
                ShapeType.RECTANGLE,
                new Vector2Serial(11.5, 15.5),
                new Vector2Serial(5, 5),
                1
        ));
        List<ConstraintConfig> constraintConfigs = new ArrayList<>();
        constraintConfigs.add( new ConstraintConfig(
                ShapeType.RECTANGLE,
                new Vector2Serial(21.5, 0.5),
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
                    new Vector2Serial(0.5+4.333*i, 17.5),
                    new Vector2Serial(4.333, 2.94),
//                    indexList.get(i)
                    switch (i) {
                        case 0 -> 3;
                        case 1 -> 0;
                        case 2 -> 1;
                        case 3 -> 5;
                        case 4 -> 2;
                        case 5 -> 4;
                        default -> throw new IllegalStateException("Unexpected value: " + i);
                    }
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
                new Vector2Serial(21.5, 15.5),
                new Vector2Serial(5, 5),
                0
        ));
        targetConfigs.add(new TargetConfig(
                ShapeType.RECTANGLE,
                new Vector2Serial(11.5, 15.5),
                new Vector2Serial(5, 5),
                1
        ));
        List<ConstraintConfig> constraintConfigs = new ArrayList<>();
        constraintConfigs.add( new ConstraintConfig(
                ShapeType.RECTANGLE,
                new Vector2Serial(21.5, 0.5),
                new Vector2Serial(5, 15)
        ));
        constraintConfigs.add( new ConstraintConfig(
                ShapeType.RECTANGLE,
                new Vector2Serial(9.5, 10.5),
                new Vector2Serial(9, 7)
        ));

        return new LevelConfig(itemList, targetConfigs, constraintConfigs);
    }

    public static LevelConfig generateLevelBasic(){
        ItemConfig objectiveCircle = new ItemConfig();
        objectiveCircle.addBodyConfig(new BodyConfig(
                ShapeType.CIRCLE,
                new Vector2(3, -8),
                new Vector2(1, 1),
                0,
                MassType.NORMAL,
                0
        ));
        ItemConfig obstacle = new ItemConfig();
        obstacle.addBodyConfig(new BodyConfig(
                ShapeType.RECTANGLE,
                new Vector2(15, -20),
                new Vector2(5, 0.3),
                -3.14*0.8,
                MassType.INFINITE
        ));

        List<ItemConfig> itemList = new ArrayList<>();
        itemList.add(objectiveCircle);
        itemList.add(obstacle);

        List<TargetConfig> targetConfigs = new ArrayList<>();
        targetConfigs.add(new TargetConfig(
                ShapeType.RECTANGLE,
                new Vector2Serial(21.5, 15.5),
                new Vector2Serial(5, 5),
                0
        ));

        List<ConstraintConfig> constraintConfigs = new ArrayList<>();

        constraintConfigs.add( new ConstraintConfig(
                ShapeType.RECTANGLE,
                new Vector2Serial(4.5, 0.5),
                new Vector2Serial(22, 20)
        ));

        return new LevelConfig(itemList, targetConfigs, constraintConfigs);
    }
    public static LevelConfig generateLevelTakeoff(){
        ItemConfig objectiveCircle = new ItemConfig();
        objectiveCircle.addBodyConfig(new BodyConfig(
                ShapeType.CIRCLE,
                new Vector2(3, -1.5),
                new Vector2(1, 1),
                0,
                MassType.NORMAL,
                0
        ));


        List<ItemConfig> itemList = new ArrayList<>();
        itemList.add(objectiveCircle);

        List<TargetConfig> targetConfigs = new ArrayList<>();
        targetConfigs.add(new TargetConfig(
                ShapeType.RECTANGLE,
                new Vector2Serial(21.5, 0.5),
                new Vector2Serial(5, 5),
                0
        ));

        List<ConstraintConfig> constraintConfigs = new ArrayList<>();

        return new LevelConfig(itemList, targetConfigs, constraintConfigs);
    }
    public static LevelConfig generateLevelTemplate(){
        ItemConfig objectiveCircle = new ItemConfig();
        objectiveCircle.addBodyConfig(new BodyConfig(
                ShapeType.CIRCLE,
                new Vector2(3, -3),
                new Vector2(1, 1),
                0,
                MassType.NORMAL,
                0
        ));


        List<ItemConfig> itemList = new ArrayList<>();
        itemList.add(objectiveCircle);

        List<TargetConfig> targetConfigs = new ArrayList<>();
        targetConfigs.add(new TargetConfig(
                ShapeType.RECTANGLE,
                new Vector2Serial(21.5, 15.5),
                new Vector2Serial(5, 5),
                0
        ));

        List<ConstraintConfig> constraintConfigs = new ArrayList<>();

        return new LevelConfig(itemList, targetConfigs, constraintConfigs);
    }
}
