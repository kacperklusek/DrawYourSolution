package game;

import game.framework.SimulationBody;
import game.framework.SimulationFrame;
import org.dyn4j.geometry.*;

import java.awt.*;

public class game extends SimulationFrame {

    public game() {
        super("Draw Your Solution", 30.0);
    }

    @Override
    protected void initializeWorld() {
        // Bottom
        SimulationBody bucketBottom = new SimulationBody();
        bucketBottom.addFixture(Geometry.createRectangle(15.0, 1.0));
        bucketBottom.setMass(MassType.INFINITE);
        bucketBottom.setColor(new Color(53, 165, 245));
        world.addBody(bucketBottom);

        // Top
        SimulationBody bucketTop = new SimulationBody();
        bucketTop.addFixture(Geometry.createRectangle(15.0, 1.0));
        bucketTop.translate(new Vector2(0.0, 14.0));
        bucketTop.setMass(MassType.INFINITE);
        bucketTop.setColor(new Color(53, 165, 245));
        world.addBody(bucketTop);

        // Left-Side
        SimulationBody bucketLeft = new SimulationBody();
        bucketLeft.addFixture(Geometry.createRectangle(1.0, 15.0));
        bucketLeft.translate(new Vector2(-7.5, 7.0));
        bucketLeft.setMass(MassType.INFINITE);
        bucketLeft.setColor(new Color(53, 165, 245));
        world.addBody(bucketLeft);

        // Right-Side
        SimulationBody bucketRight = new SimulationBody();
        bucketRight.addFixture(Geometry.createRectangle(1.0, 15.0));
        bucketRight.translate(new Vector2(7.5, 7.0));
        bucketRight.setMass(MassType.INFINITE);
        bucketRight.setColor(new Color(53, 165, 245));
        world.addBody(bucketRight);


        Circle c = Geometry.createCircle(1.0);
        SimulationBody b = new SimulationBody();
        b.addFixture(c);
        b.translate(new Vector2(-5.0, 3.0));
        b.setMass(MassType.NORMAL);
        world.addBody(b);

    }

    public static void main(String[] args) {
        game simulation = new game();
        simulation.run();
    }
}
