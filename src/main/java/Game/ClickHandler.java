package Game;

import Game.configs.BodyConfig;
import Game.configs.ItemConfig;
import Game.configs.JointType;
import Game.gui.BoardGui;
import Game.configs.ShapeType;
import javafx.scene.input.MouseButton;
import org.dyn4j.geometry.*;

import java.util.ArrayList;
import java.util.List;

public class ClickHandler {

    int clickCount = 0;
    Vector2 firstClick = new Vector2(0,0);

    List<ItemCreationListener> listeners = new ArrayList<>();

    public void mouseClicked(double x, double y, MouseButton mouseButton) {
        switch (mouseButton) {
            case PRIMARY -> handlePRIMARY(x, y);
            case SECONDARY -> handleSECONDARY(x, y);
        }
    }

    private void handlePRIMARY(double x, double y) {
        if ((x != firstClick.x || y != firstClick.y)){
            clickCount ++;
        }
        if (clickCount == 2){
            clickCount %= 2;

            ItemConfig itemConfig = new ItemConfig();

            // create rectangle bodyConfig
            BodyConfig rectangleConfig = new BodyConfig(
                    ShapeType.RECTANGLE,
                    new Vector2((x + firstClick.x - 2 * BoardGui.BOARD_OFFSET.x) / (2 * BoardGui.SCALE),
                               -(y + firstClick.y - 2 * BoardGui.BOARD_OFFSET.y) / (2 * BoardGui.SCALE)),
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

    private void handleSECONDARY(double x, double y) {
        ItemConfig itemConfig = new ItemConfig();

        // create ball bodyConfig
        BodyConfig bodyConfig = new BodyConfig(
                ShapeType.CIRCLE,
                new Vector2((x - BoardGui.BOARD_OFFSET.x) / BoardGui.SCALE,
                           (-y + BoardGui.BOARD_OFFSET.y) / BoardGui.SCALE),
                new Vector2(2, 2), // dla circle radius będzie jedną ze współrzędnych
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
}