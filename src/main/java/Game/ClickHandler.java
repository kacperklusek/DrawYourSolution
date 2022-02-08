package Game;

import Game.configs.*;
import Game.gui.BoardGui;
import javafx.scene.input.MouseButton;
import org.dyn4j.geometry.*;

import java.util.ArrayList;
import java.util.List;

public class ClickHandler {

    private Vector2Serial upperLeft, lowerRight;
    private List<ConstraintConfig> constraintConfigs;

    int clickCount = 0;
    Vector2 firstClick = new Vector2(0,0);

    List<ItemCreationListener> listeners = new ArrayList<>();

    public void mouseClicked(double x, double y, MouseButton mouseButton) {
        if ((! isInBoard(x, y)) || (isInConstraint(x, y))) {return;}
        switch (mouseButton) {
            case PRIMARY -> handlePRIMARY(x, y);
            case SECONDARY -> handleSECONDARY(x, y);
        }
    }

    private void handlePRIMARY(double x, double y) {
        if (x == firstClick.x && y == firstClick.y) {return;}
        clickCount ++;
        if (clickCount == 2){
            clickCount %= 2;

            ItemConfig itemConfig = new ItemConfig();

            // create rectangle bodyConfig
            BodyConfig rectangleConfig = new BodyConfig(
                    ShapeType.RECTANGLE,
                    new Vector2((x + firstClick.x - 2 * BoardGui.SCALED_OFFSET.x) / (2 * BoardGui.SCALE),
                               -(y + firstClick.y - 2 * BoardGui.SCALED_OFFSET.y) / (2 * BoardGui.SCALE)),
                    new Vector2(Math.sqrt(Math.pow(x - firstClick.x, 2) + Math.pow(y - firstClick.y, 2)) / BoardGui.SCALE,
                            0.2),
                    -Math.atan((firstClick.y - y) / (firstClick.x - x)),
                    MassType.INFINITE);

            // add configs to list
            itemConfig.addBodyConfig(rectangleConfig);
            itemConfig.setJointType(JointType.WELD);

            notifyItemCreated(itemConfig);

        } else {
            firstClick.set(x, y);
        }
    }

    private boolean isInBoard(double x, double y) {
        Vector2Serial v = new Vector2Serial(x, y);
        return v.follows(upperLeft) && v.precedes(lowerRight);
    }

    private boolean isInConstraint(double x, double y) {
        Vector2Serial click = new Vector2Serial(x/BoardGui.SCALE, y/BoardGui.SCALE);
                for(ConstraintConfig con: constraintConfigs) {
            switch (con.shape()){
                case RECTANGLE -> {
                    if(click.follows(con.position().add(BoardGui.OFFSET)) &&
                            click.precedes(con.position().add(con.size()).add(BoardGui.OFFSET))) {
                        return true;
                    }
                }
                case CIRCLE -> {
                    if(click.distance(con.position().add(BoardGui.OFFSET)) <= con.size().x) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void handleSECONDARY(double x, double y) {
        Vector2 v = new Vector2((x - BoardGui.SCALED_OFFSET.x) / BoardGui.SCALE,
                           (-y + BoardGui.SCALED_OFFSET.y) / BoardGui.SCALE);

        System.out.println(v.x + "  " + v.y);

        ItemConfig itemConfig = new ItemConfig();

        // create ball bodyConfig
        BodyConfig bodyConfig = new BodyConfig(
                ShapeType.CIRCLE,
                new Vector2((x - BoardGui.SCALED_OFFSET.x) / BoardGui.SCALE,
                           (-y + BoardGui.SCALED_OFFSET.y) / BoardGui.SCALE),
                new Vector2(1, 1), // dla circle radius będzie jedną ze współrzędnych
                0,
                MassType.NORMAL
        );

        // add configs to list
        itemConfig.addBodyConfig(bodyConfig);

        notifyItemCreated(itemConfig);
    }



    public void addItemCreationListener(ItemCreationListener listener) {
        listeners.add(listener);
    }

    private void notifyItemCreated(ItemConfig itemConfig) {
        for (ItemCreationListener listener: listeners) {
            listener.itemCreated(itemConfig);
        }
    }

    public void setBoardDimensions(double width, double height, Vector2Serial offset) {
        upperLeft = new Vector2Serial(0, 0).add(offset);
        lowerRight = new Vector2Serial(width * BoardGui.SCALE, height * BoardGui.SCALE).add(offset);
    }

    public void setConstraints(List<ConstraintConfig> constraintConfigs){
        this.constraintConfigs = constraintConfigs;
    }
}