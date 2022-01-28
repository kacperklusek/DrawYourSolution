package Game;

import org.dyn4j.geometry.Vector2;

public class Simulation {
    private final World world;

    public Simulation() {
        this.world = new World();
        // setting default gravity
        world.setGravity(new Vector2(0., -10.));
    }

    public void addItem(ItemConfig itemConfig){

        // create bodies etc.
        // add to world
    }
}
