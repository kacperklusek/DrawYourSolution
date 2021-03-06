/**********************************************************************
This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

For more information, please refer to <http://unlicense.org>
**********************************************************************/

package Game.gui;

import Game.*;
import Game.configs.ConstraintConfig;
import Game.configs.ShapeType;
import Game.configs.TargetConfig;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

public class BoardGui implements BoardStateListener, ObjectiveListener {
	private final String LEVELS_DATA_PATH = "./levelsData";
	public static final int SCALE = 32;
    public static final Vector2Serial SCALED_OFFSET = new Vector2Serial(0.5*SCALE, 1.7*SCALE);
    public static final Vector2Serial OFFSET = new Vector2Serial(0.5, 1.7);
	private final List<ButtonsListener> buttonsListeners = new ArrayList<>();

	Group group;
	HBox userInputContainer;
	ComboBox<String> levelNameInput;
	Text messageText;

	public BoardGui(Group group, String levelName) {
		this.group = group;
		this.userInputContainer = new HBox();
		this.group.getChildren().add(userInputContainer);
		initializeButtons();
		initializeLevelSelector(levelName);
		addInstructions();
	}

	@Override
	public void worldUpdate(WorldEvent e) {
		if (e.getType() == WorldEvent.Type.BODY_ADDED) {
			if (e.getBodyWrapper().getBody().getFixture(0).getShape() instanceof org.dyn4j.geometry.Rectangle) {
				Rectangle rectangle = new Rectangle();
				e.getBodyWrapper().addBodyListener(rectangle);
				this.group.getChildren().add(rectangle);
			}
			else {
				Circle circle = new Circle();
				e.getBodyWrapper().addBodyListener(circle);
				this.group.getChildren().add(circle);
			}
		}
	}

	@Override
	public void targetAdded(TargetConfig targetConfig) {
		drawShape(
			targetConfig.shape(),
			targetConfig.position(),
			targetConfig.size(),
			colorFromTargetID(targetConfig.ID(), true)
		);
	}

	@Override
	public void constraintAdded(ConstraintConfig constraintConfig) {
		drawShape(
				constraintConfig.shape(),
				constraintConfig.position(),
				constraintConfig.size(),
				Color.hsb(0, 1, 0.9, 0.3)
		);
	}

	private void drawShape(ShapeType shapeType, Vector2Serial position, Vector2Serial size, Color color) {
		switch (shapeType) {
			case RECTANGLE -> {
				javafx.scene.shape.Rectangle shape = new javafx.scene.shape.Rectangle();
				shape.setWidth(size.x * SCALE);
				shape.setHeight(size.y * SCALE);
				shape.setX(position.x * SCALE + SCALED_OFFSET.x);
				shape.setY(position.y * SCALE + SCALED_OFFSET.y);
				shape.setFill(color);
				this.group.getChildren().add(shape);
			}
			case CIRCLE -> {
				javafx.scene.shape.Circle shape = new javafx.scene.shape.Circle();
				shape.setRadius(size.x);
				shape.setCenterX(position.x * SCALE + SCALED_OFFSET.x);
				shape.setCenterY(position.y * SCALE + SCALED_OFFSET.y);
				shape.setFill(color);
			}
		}
	}

	public static Color colorFromTargetID(Integer targetID, boolean isTarget){
		if(isNull(targetID)){
			return Color.DARKGRAY;
		}
		return Color.hsb((100+targetID*((360-60)/9.0 + 15))%(360-60)+30, 1, 0.7, isTarget ? 0.5 : 1.0);
	}

	public void addButtonListener(ButtonsListener listener) {
		buttonsListeners.add(listener);
	}

	private void initializeButtons() {
		Button startButton = new Button("start (L)");
		startButton.setOnAction(event -> {
			for(ButtonsListener listener: buttonsListeners) {
				listener.handleStart();
			}
		});
		Button stopButton = new Button("stop (P)");
		stopButton.setOnAction(event -> {
			for(ButtonsListener listener: buttonsListeners) {
				listener.handleStop();
			}
		});
		Button saveButton = new Button("save level config (S)");
		saveButton.setOnAction(event -> {
			for(ButtonsListener listener: buttonsListeners) {
				listener.handleSave(levelNameInput.getValue());
			}
		});
		Button loadButton = new Button("load level config");
		loadButton.setOnAction(event -> {
			for(ButtonsListener listener: buttonsListeners) {
				listener.handleLoad(levelNameInput.getValue());
			}
		});
		Button resetButton = new Button("RESET (R)");
		resetButton.setOnAction(event -> {
			for(ButtonsListener listener: buttonsListeners) {
				listener.handleReset();
			}
		});
		userInputContainer.getChildren().add(startButton);
		userInputContainer.getChildren().add(stopButton);
		userInputContainer.getChildren().add(resetButton);
		userInputContainer.getChildren().add(saveButton);
		userInputContainer.getChildren().add(loadButton);
	}

	private void initializeLevelSelector(String levelName) {
		Text label = new Text(" level Name: ");
		levelNameInput = new ComboBox<>();

		Set<String> fileNames = Stream.of(Objects.requireNonNull(new File(LEVELS_DATA_PATH).listFiles()))
				.filter(File::isFile)
				.map(File::getName)
				.collect(Collectors.toSet());

		for (String fileName: fileNames) {
			// here i'm stripping 'lvl_' from beginning and '.dat' from the end
			levelNameInput.getItems().add(fileName.substring(4, fileName.length()-4));
		}

		levelNameInput.setValue(levelName);
		levelNameInput.setEditable(true);


		userInputContainer.getChildren().add(label);
		userInputContainer.getChildren().add(levelNameInput);
	}

	private void addInstructions() {
		Text instruction = new Text(" click LMB to draw lines, RMB to spawn balls");
		messageText = new Text(" get the colored balls to destined targets!");
		VBox box = new VBox();
		box.getChildren().addAll(instruction, messageText);
		userInputContainer.getChildren().add(box);
	}

	@Override
	public void objectiveSatisfied() {
		messageText.setText(" CONGRATULATIONS, YOU WIN!");
	}
}
