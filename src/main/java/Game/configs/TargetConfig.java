package Game.configs;

import Game.Vector2Serial;

import java.io.Serializable;

public record TargetConfig(ShapeType shape,
                           Vector2Serial position,
                           Vector2Serial size,
                           int ID) implements Serializable {

}
