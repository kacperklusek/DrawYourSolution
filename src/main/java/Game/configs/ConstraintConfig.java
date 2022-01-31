package Game.configs;

import Game.Vector2Serial;

import java.io.Serializable;

public record ConstraintConfig (
        ShapeType shape,
        Vector2Serial position,
        Vector2Serial size) implements Serializable{
}
