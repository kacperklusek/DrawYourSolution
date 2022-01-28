package Game;

import org.dyn4j.geometry.MassType;


public record BodyConfig(ShapeType shape, Vector2D position, Vector2D size, MassType massType) {

}
