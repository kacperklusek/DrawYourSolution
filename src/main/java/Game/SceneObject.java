package Game;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.joint.Joint;

import java.util.ArrayList;
import java.util.List;

public class SceneObject {

    List<Body> bodies = new ArrayList<>();
    List<Joint> joints = new ArrayList<>();

    public SceneObject() {
    }

    public void addBody(Body body) {
        this.bodies.add(body);
    }

    public void addJoint(Joint<Body> joint) {
        if (!bodies.contains(joint.getBody1()) || !bodies.contains(joint.getBody2())) {
            System.out.println("BODIES IN JOINT NOT IN BODIES LIST!");
            return;
        }
        joints.add(joint);
    }


}
