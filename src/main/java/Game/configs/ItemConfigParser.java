package Game.configs;

import Game.BodyWrapper;
import com.sun.source.tree.BreakTree;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.joint.Joint;
import org.dyn4j.dynamics.joint.RevoluteJoint;
import org.dyn4j.dynamics.joint.WeldJoint;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import java.util.ArrayList;
import java.util.List;

public class ItemConfigParser {

    public List<BodyWrapper> parseBodies(ItemConfig itemConfig) {
        List<BodyWrapper> bodiesList = new ArrayList<>();
        for (BodyConfig bodyConfig: itemConfig.bodyConfigs) {
            BodyFixture bodyFixture = getBodyFixture(bodyConfig.shape(), bodyConfig.size().toVector2());

            // bounciness
            bodyFixture.setRestitution(0.3);

            Body body = new Body();
            body.addFixture(bodyFixture);
            body.setMass(bodyConfig.massType());
            body.translate(bodyConfig.position().x, bodyConfig.position().y);
            body.getTransform().setRotation(bodyConfig.rotation());

            BodyWrapper bodyWrapper = new BodyWrapper(body, bodyConfig.targetID());

            bodiesList.add(bodyWrapper);
        }
        return bodiesList;
    }

    private BodyFixture getBodyFixture(ShapeType shapeType, Vector2 size) {
        return switch (shapeType){
            case CIRCLE -> new BodyFixture(new Circle(size.x));
            case RECTANGLE -> new BodyFixture(new Rectangle(size.x, size.y));
        };
    }

    public List<Joint<Body>> parseJoints(List<BodyWrapper> bodies, JointType jointType) {
        Body body1, body2;
        List<Joint<Body>> joints = new ArrayList<>();

        for (int i = 1; i < bodies.size(); i++) {
            body1 = bodies.get(i-1).getBody();
            body2 = bodies.get(i).getBody();
            Joint<Body> joint;
            switch (jointType) {
                case SPRING -> {
                    WeldJoint<Body> jnt = new WeldJoint<>(body1,
                            body2,
                            getPosition(body1));
                    jnt.setFrequency(80);
                    joint = jnt;
                }
                case REVOLUTE -> {
                    joint = new RevoluteJoint<>(body1,
                            body2,
                            getPosition(body1));
                }
//                case WELD -> {
                default -> {
                    joint = new WeldJoint<>(body1,
                            body2,
                            getPosition(body1));
                }
            }
            joints.add(joint);
        }
        return joints;
    }

    private Vector2 getPosition(Body body){
        return body.getTransform().getTranslation();
    }

    private Vector2 getPosition(Body body1, Body body2){
        return new Vector2(
                (getPosition(body1).x + getPosition(body2).x) / 2,
                (getPosition(body1).y + getPosition(body2).y) / 2);
    }
}
