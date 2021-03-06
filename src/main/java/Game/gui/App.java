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
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application implements ButtonsListener {

	public static final double SCENE_WIDTH = 1920*0.7;
	public static final double SCENE_HEIGHT = 1080*0.7;

	ClickHandler clickHandler;
	LevelManager levelManager;
    Persistency persistency;
	ObjectiveChecker objectiveChecker;
	BoardGui boardGui;

	Stage primaryStage;

	String currentLevelName = "sorting";

	@Override
	public void init() {

	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("DrawYourSolution");
		primaryStage.sizeToScene();
		this.primaryStage = primaryStage;

		clickHandler = new ClickHandler();
		persistency = new Persistency();
		objectiveChecker = new ObjectiveChecker();

		// setup scene
		Group root = new Group();
		Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
		primaryStage.setScene(scene);

		// Creating the world
		levelManager = new LevelManager();
		boardGui = new BoardGui(root, currentLevelName);

		addListeners();

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
                case S -> persistency.saveLevel(currentLevelName, levelManager.generateLevelConfig());
			}
		});

		loadLevel();
		primaryStage.show();
	}

	private void addListeners() {
		boardGui.addButtonListener(this);
		levelManager.addBoardStateListener(boardGui);
		levelManager.addBoardStateListener(objectiveChecker);
		clickHandler.addItemCreationListener(levelManager);
		clickHandler.setBoardDimensions(levelManager.WIDTH, levelManager.HEIGHT, BoardGui.SCALED_OFFSET);
		objectiveChecker.addObjectiveStateListener(levelManager);
		objectiveChecker.addObjectiveStateListener(boardGui);
	}

	private void loadLevel() {
		levelManager.createBoundaries();
		try {
			levelManager.loadLevel(persistency.loadLevel(currentLevelName));
		} catch (IOException | ClassNotFoundException e) {
			System.out.println(e.getMessage());

			levelManager.loadLevel(ExampleLevelsGenerator.generateLevelTemplate());
		}
		clickHandler.setConstraints(levelManager.getLevelConfig().constraintConfigs);
	}

	@Override
	public void handleStart() {
		levelManager.startSimulation();
	}

	@Override
	public void handleStop() {
		levelManager.stopSimulation();
	}

	@Override
	public void handleReset() {
		this.start(primaryStage);
	}

	@Override
	public void handleSave(String levelName) {
		currentLevelName = levelName;
		persistency.saveLevel(levelName, levelManager.generateLevelConfig());
	}

	@Override
	public void handleLoad(String levelName) {
		currentLevelName = levelName;
		handleReset();
	}
}
