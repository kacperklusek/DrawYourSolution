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

package Game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.dyn4j.collision.CategoryFilter;

public class App extends Application {

	private static final CategoryFilter ALL = new CategoryFilter(1, Long.MAX_VALUE);
	private static final CategoryFilter BALL = new CategoryFilter(2, Long.MAX_VALUE);
	private static final CategoryFilter PIN = new CategoryFilter(4, 1 | 2 | 8);
	private static final CategoryFilter NOT_BALL = new CategoryFilter(8, 1 | 4);

	ClickHandler clickHandler = new ClickHandler();
	LevelManager levelManager;
    Persistency persistency = new Persistency();

	@Override
	public void init() {

	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("DrawYourSolution");
		primaryStage.sizeToScene();


		// setup scene
		Group root = new Group();
		Scene scene = new Scene(root, 1920*0.7, 1080*0.7);
		primaryStage.setScene(scene);

		// Creating the world
		levelManager = new LevelManager();
		GUI gui = new GUI(levelManager.getWorld(), root);
		clickHandler.addItemCreationListener(levelManager);
        levelManager.createBoundaries();
		levelManager.loadLevel(persistency.loadLevel("1"));

		// start simulation
		levelManager.startSimulation();
		primaryStage.show();

		// clickHandler
		scene.setOnMouseClicked(e -> {
			switch (e.getButton()){
				case SECONDARY, PRIMARY -> {
					clickHandler.mouseClicked(e.getX(), e.getY(), e.getButton());
				}
			}
		});

		// button events
		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
				case R -> start(primaryStage);
				case P -> levelManager.stopSimulation();
				case L -> levelManager.startSimulation();
			}
		});
	}

}
