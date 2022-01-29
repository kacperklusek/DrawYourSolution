package Game;

import org.dyn4j.geometry.Vector2;

import java.util.logging.Level;

public class LevelManager implements ItemCreationListener{

//    Simulation simulation = new Simulation();
    World world;
    Scheduler scheduler;
    Persistency persistency = new Persistency();
    LevelConfig level = persistency.getLevel();

    public LevelManager() {
        this.world = new World();
        // setting default gravity
        world.setGravity(new Vector2(0., -10.));
        this.scheduler = new Scheduler(world);
    }

    @Override
    public void itemCreated(ItemConfig itemConfig) {
        // i tu w sumie robie notify Simulation i przekazuje to po prostu dalej? troche bez sensu
        world.addItem(itemConfig);
        // if simulation is paused => render one frame for item to appear
        scheduler.handle(0);
    }

    public World getWorld() {
        return world;
    }

    public void startSimulation(){
        scheduler.start();
    }

    public void stopSimulation() {
        scheduler.stop();
    }

    public void loadLevel() {
        for (ItemConfig itemConfig: level.itemConfigs) {
            world.addItem(itemConfig);
        }
//        // fixme
//        world.addTarget(level.target);
        // refresh window
        scheduler.handle(0);
    }
}
