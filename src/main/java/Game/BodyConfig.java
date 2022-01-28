package Game;

import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;


public record BodyConfig(ShapeType shape, Vector2 position, Vector2 size, double rotation,MassType massType) {

}
