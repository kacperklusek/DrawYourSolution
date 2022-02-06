/**********************************************************************
 * This is free and unencumbered software released into the public domain.
 * <p>
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 * <p>
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 * <p>
 * For more information, please refer to <http://unlicense.org>
 **********************************************************************/

package Game.gui;

import Game.*;
import Game.configs.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {

	public static final double SCENE_WIDTH = 1920*0.7;
	public static final double SCENE_HEIGHT = 1080*0.7;

	ClickHandler clickHandler = new ClickHandler();
	LevelManager levelManager;
    Persistency persistency = new Persistency();
	ObjectiveChecker objectiveChecker = new ObjectiveChecker();

	@Override
	public void init() {

	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("DrawYourSolution");
		primaryStage.sizeToScene();


		// setup scene
		Group root = new Group();
		Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
		primaryStage.setScene(scene);

		// Creating the world
		levelManager = new LevelManager();
		BoardGui gui = new BoardGui(root);
		levelManager.addBoardStateListener(gui);
		levelManager.addBoardStateListener(objectiveChecker);
		clickHandler.addItemCreationListener(levelManager);
		objectiveChecker.addObjectiveStateListener(levelManager);
		clickHandler.setBoardDimensions(levelManager.WIDTH, levelManager.HEIGHT, BoardGui.BOARD_OFFSET);
        levelManager.createBoundaries();
        try {
            levelManager.loadLevel(persistency.loadLevel("2"));
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
			ItemConfig objectiveCircle = new ItemConfig();
			objectiveCircle.addBodyConfig(new BodyConfig(
                    ShapeType.CIRCLE,
                    new Vector2(8, -8),
                    new Vector2(1, 1),
                    0,
                    MassType.NORMAL,
					0
            ));
			ItemConfig objectiveCircle2 = new ItemConfig();
			objectiveCircle2.addBodyConfig(new BodyConfig(
					ShapeType.CIRCLE,
					new Vector2(13, -8),
					new Vector2(1, 1),
					0,
					MassType.NORMAL,
					1		));
            List<ItemConfig> itemList = new ArrayList<>();
			itemList.add(objectiveCircle);
			itemList.add(objectiveCircle2);

			List<TargetConfig> targetConfigs = new ArrayList<>();
			targetConfigs.add(new TargetConfig(
					ShapeType.RECTANGLE,
					new Vector2Serial(22, 17),
					new Vector2Serial(5, 5),
					0
			));
			targetConfigs.add(new TargetConfig(
					ShapeType.RECTANGLE,
					new Vector2Serial(12, 17),
					new Vector2Serial(5, 5),
					1
			));
			List<ConstraintConfig> constraintConfigs = new ArrayList<>();
			constraintConfigs.add( new ConstraintConfig(
					ShapeType.RECTANGLE,
					new Vector2Serial(22, 2),
					new Vector2Serial(5, 14.5)
			));

			levelManager.loadLevel(new LevelConfig(itemList, targetConfigs, constraintConfigs));
        }
		clickHandler.setConstraints(levelManager.getLevelConfig().constraintConfigs);

        // start simulation
//		levelManager.startSimulation();
		primaryStage.show();

		// clickHandler
		scene.setOnMouseClicked(e -> {
			clickHandler.mouseClicked(e.getX(), e.getY(), e.getButton());
			});

		// button events
		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
				case R -> start(primaryStage);
				case P -> levelManager.stopSimulation();
				case L -> levelManager.startSimulation();
                case S -> persistency.saveLevel("2", levelManager.generateLevelConfig());
			}
		});
	}
}
