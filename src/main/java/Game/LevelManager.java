package Game;

import org.dyn4j.geometry.Vector2;

public class LevelManager implements ItemCreationListener{

//    Simulation simulation = new Simulation();
    World world;

    public LevelManager() {
        this.world = new World();
        // setting default gravity
        world.setGravity(new Vector2(0., -10.));
    }

    @Override
    public void itemCreated(ItemConfig itemConfig) {
        // i tu w sumie robie notify Simulation i przekazuje to po prostu dalej? troche bez sensu
        world.addItem(itemConfig);
    }

    public World getWorld() {
        return world;
    }
}
