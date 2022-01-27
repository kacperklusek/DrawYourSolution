package Game;

import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.collision.Fixture;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.joint.Joint;
import org.dyn4j.dynamics.joint.RevoluteJoint;
import org.dyn4j.dynamics.joint.WeldJoint;
import org.dyn4j.geometry.*;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Rectangle;

import java.util.List;

public class ClickHandler {
    private static final CategoryFilter ALL = new CategoryFilter(1, Long.MAX_VALUE);

    int clickCount = 0;
    double[] firstClick = new double[2];

    public Body mouseClicked(double x, double y, World world) {
        clickCount ++;
        if (clickCount == 2){
            clickCount %= 2;

            BodyFixture bodyFixture = new BodyFixture(
                    new Rectangle(
                            Math.sqrt(Math.pow(x - firstClick[0], 2) + Math.pow(y - firstClick[1], 2)) / GUI.SCALE,
                            0.2
                    )
            );
            bodyFixture.setDensity(0.2);
            bodyFixture.setFriction(0.3);
            bodyFixture.setRestitution(0.2);

            Body line = new Body();
            line.addFixture(bodyFixture);
            line.setMass(MassType.NORMAL);
            line.translate( (x + firstClick[0]) / (2 * GUI.SCALE), -(y + firstClick[1]) / (2 * GUI.SCALE));
            line.getTransform().setRotation(-Math.atan(
                    (firstClick[1] - y) / (firstClick[0] - x)
            ));
            System.out.println(line.getTransform().getRotationAngle());

            Body ball = new Body();
            ball.addFixture(new BodyFixture(new Circle(2)));
            ball.setMass(MassType.NORMAL);
            ball.translate( (firstClick[0]) / (GUI.SCALE), -(firstClick[1]) / (GUI.SCALE));

            WeldJoint<Body> rj = new WeldJoint<>(line,
                    ball,
                    new Vector2((firstClick[0]) / (GUI.SCALE), -(firstClick[1]) / (GUI.SCALE)));
            world.addBody(ball);
            world.addBody(line);
            world.addJoint(rj);


        } else {
            firstClick[0] = x;
            firstClick[1] = y;
        }
        return null;
    }
}
