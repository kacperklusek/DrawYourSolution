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
import Game.configs.TargetConfig;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import org.dyn4j.geometry.Vector2;

public class BoardGui implements BoardStateListener {
	public static final int SCALE = 32;
    public static final Vector2 BOARD_OFFSET = new Vector2(0.5*SCALE, 1.4*SCALE);

	Group group;

	public BoardGui(Group group) {
		this.group = group;
		Button startButton = new Button("start");
		startButton.setOnAction(event -> {
			// do sth
		});
		this.group.getChildren().add(startButton);
	}

	@Override
	public void worldUpdate(WorldEvent e) {
		if (e.getType() == WorldEvent.Type.BODY_ADDED) {
			if (e.getBody().getBody().getFixture(0).getShape() instanceof org.dyn4j.geometry.Rectangle){
				Rectangle rectangle = new Rectangle();
				e.getBody().addBodyListener(rectangle);
				this.group.getChildren().add(rectangle);
			}
			else {
				Circle circle = new Circle();
				e.getBody().addBodyListener(circle);
				this.group.getChildren().add(circle);
			}
		}
	}

	@Override
	public void targetAdded(TargetConfig targetConfig) {
		switch (targetConfig.shape()) {
			case RECTANGLE -> {
				javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle();
				rectangle.setX(targetConfig.position().x * SCALE);
				rectangle.setY(targetConfig.position().y * SCALE);
				rectangle.setWidth(targetConfig.size().x * SCALE);
				rectangle.setHeight(targetConfig.size().y * SCALE);
				rectangle.setFill(Color.GREEN);
				this.group.getChildren().add(rectangle);
			}
		}
	}

}