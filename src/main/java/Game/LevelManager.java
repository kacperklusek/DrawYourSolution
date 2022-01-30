package Game;

import Game.configs.BodyConfig;
import Game.configs.ItemConfig;
import Game.configs.LevelConfig;
import Game.configs.ShapeType;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

public class LevelManager implements ItemCreationListener{
    private final double HEIGHT = 21;
    private final double WIDTH = 27;

//    Simulation simulation = new Simulation();
    World world;
    Scheduler scheduler;
    LevelConfig level;

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

    public void loadLevel(LevelConfig levelConfig) {
        level = levelConfig;
        for (ItemConfig itemConfig: level.itemConfigs) {
            world.addItem(itemConfig);
        }
//        // fixme
//        world.addTarget(level.target);
        // refresh window
        scheduler.handle(0);
    }

    public void createBoundaries(){
        double thickness = 1;
        ItemConfig groundConfig = new ItemConfig();
        groundConfig.addBodyConfig(new BodyConfig(
                ShapeType.RECTANGLE,
                new Vector2(WIDTH/2, -HEIGHT),
                new Vector2(WIDTH, thickness),
                0,
                MassType.INFINITE
        ));
        ItemConfig ceilingConfig = new ItemConfig();
        ceilingConfig.addBodyConfig(new BodyConfig(
                ShapeType.RECTANGLE,
                new Vector2(WIDTH/2, 0),
                new Vector2(WIDTH, thickness),
                0,
                MassType.INFINITE
        ));
        ItemConfig leftWallConfig = new ItemConfig();
        leftWallConfig.addBodyConfig(new BodyConfig(
                ShapeType.RECTANGLE,
                new Vector2(0, -HEIGHT/2),
                new Vector2(thickness, HEIGHT+thickness),
                0,
                MassType.INFINITE
        ));
        ItemConfig rightWallConfig = new ItemConfig();
        rightWallConfig.addBodyConfig(new BodyConfig(
                ShapeType.RECTANGLE,
                new Vector2(WIDTH, -HEIGHT/2),
                new Vector2(thickness, HEIGHT+thickness),
                0,
                MassType.INFINITE
        ));
        world.addItem(groundConfig);
        world.addItem(ceilingConfig);
        world.addItem(leftWallConfig);
        world.addItem(rightWallConfig);
        scheduler.handle(0);
    }
}
