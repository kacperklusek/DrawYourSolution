package game;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.joint.PinJoint;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.dyn4j.world.World;

public class game {

    public static void main (String[] args) {
        World<Body> world = new World<>();

        Body body = new Body();
        body.addFixture(Geometry.createCircle(1.0));
        body.translate(1.0, 0.0);
        body.setMass(MassType.NORMAL);
        world.addBody(body);

        PinJoint<Body> joint = new PinJoint<Body>(body, new Vector2(0, 0), 4, 0.7, 1000);
        world.addJoint(joint);

        for (int i = 0; i < 100; i++) {
            world.step(1);
        }

    }

}
