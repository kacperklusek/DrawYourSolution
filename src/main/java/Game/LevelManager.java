package Game;

import Game.configs.*;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LevelManager implements ItemCreationListener, BoardStatePublisher{
    public double getHEIGHT() {
        return HEIGHT;
    }

    public double getWIDTH() {
        return WIDTH;
    }

    private final double HEIGHT = 21;
    private final double WIDTH = 27;

    private World world;
    private Scheduler scheduler;

    public LevelConfig getLevelConfig() {
        return levelConfig;
    }
    private LevelConfig levelConfig;
    private List<ItemConfig> addedItems;

    private List<BoardStateListener> listeners;

    public LevelManager() {
        this.world = new World();
        // setting default gravity
        world.setGravity(new Vector2(0., -10.));
        this.scheduler = new Scheduler(world);
        addedItems = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    @Override
    public void itemCreated(ItemConfig itemConfig) {
        world.addItem(itemConfig);
        addedItems.add(itemConfig);
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
        this.levelConfig = levelConfig;
        for (ConstraintConfig constraintConfig: levelConfig.constraintConfigs) {
            notifyAllConstraintAdded(constraintConfig);
        }
        for (TargetConfig targetConfig: levelConfig.targetConfigs) {
            notifyAllTargetAdded(targetConfig);
        }
        for (ItemConfig itemConfig: this.levelConfig.itemConfigs) {
            world.addItem(itemConfig);
        }

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

    public LevelConfig generateLevelConfig() {
        return new LevelConfig(Stream.of(levelConfig.itemConfigs, addedItems)
                .flatMap(Collection::stream)
                .collect(Collectors.toList()), levelConfig.targetConfigs,
                levelConfig.constraintConfigs);
    }

    @Override
    public void addBoardStateListener(BoardStateListener listener) {
        this.listeners.add(listener);
        world.addBoardStateListener(listener);
    }

    @Override
    public void removeBoardStateListener(BoardStateListener listener) {
        this.listeners.remove(listener);
        world.addBoardStateListener(listener);
    }

    @Override
    public void notifyAllWorldUpdate(WorldEvent event){
        throw new UnsupportedOperationException();
    }

    @Override
    public void notifyAllTargetAdded(TargetConfig targetConfig) {
        for(BoardStateListener lister: listeners) {
            lister.targetAdded(targetConfig);
        }
    }

    @Override
    public void notifyAllConstraintAdded(ConstraintConfig constraintConfig) {
        for(BoardStateListener listener: listeners) {
            listener.constraintAdded(constraintConfig);
        }
    }
}
