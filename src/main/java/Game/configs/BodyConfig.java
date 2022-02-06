package Game.configs;

import Game.Vector2Serial;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import java.io.Serializable;


public record BodyConfig(
        ShapeType shape,
        Vector2Serial position,
        Vector2Serial size,
        double rotation,
        MassType massType,
        Integer targetID
) implements Serializable {
    public BodyConfig(ShapeType shape,
                      Vector2 position,
                      Vector2 size,
                      double rotation,
                      MassType massType,
                      Integer targetID){
        this(shape, new Vector2Serial(position), new Vector2Serial(size), rotation, massType, targetID);
    }

    public BodyConfig(ShapeType shape,
                      Vector2 position,
                      Vector2 size,
                      double rotation,
                      MassType massType){
        this(shape, new Vector2Serial(position), new Vector2Serial(size), rotation, massType, null);
    }

    public BodyConfig(
            ShapeType shape,
            Vector2Serial position,
            Vector2Serial size,
            double rotation,
            MassType massType){
        this(shape, position, size, rotation, massType, null);
    }



}
