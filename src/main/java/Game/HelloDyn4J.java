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
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.*;

import java.util.Random;

public class HelloDyn4J extends Application {

	private static final CategoryFilter ALL = new CategoryFilter(1, Long.MAX_VALUE);
	private static final CategoryFilter BALL = new CategoryFilter(2, Long.MAX_VALUE);
	private static final CategoryFilter PIN = new CategoryFilter(4, 1 | 2 | 8);
	private static final CategoryFilter NOT_BALL = new CategoryFilter(8, 1 | 4);

	Stage primStage;
	ClickHandler clickHandler = new ClickHandler();


	@Override
	public void start(Stage primaryStage) {
		this.primStage = primaryStage;
		this.primStage.setTitle("HelloDyn4J");
		this.primStage.sizeToScene();

		Group root = new Group();

		Scene scene = new Scene(root, 600, 600);
		this.primStage.setScene(scene);

		// Creating the world
		World world = new World();
		world.setGravity(new Vector2(0., -10.));

		GUI gui = new GUI(world, root);

		Scheduler scheduler = new Scheduler(world);

		createGround(world);

		scheduler.start();
		this.primStage.show();

		scene.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.SECONDARY) {
//				createBoxAt(world, e.getX(), e.getY());
				createBallAt(world, e.getX(), e.getY());
			} else {
				clickHandler.mouseClicked(e.getX(), e.getY(), world);
//				Body retvalue = clickHandler.mouseClicked(e.getX(), e.getY());
//				if (retvalue != null) {
//					world.addBody(retvalue);
//				}
//				createBoxAt(world, e.getX(), e.getY());
			}
		});

	}


	private void createGround(World world) {
		Body ground = new Body();
		ground.addFixture(Geometry.createRectangle(50.0, 1.0));
		ground.translate(new Vector2(0.6875, -18.75));
		ground.setMass(MassType.INFINITE);
		world.addBody(ground);
	}

	private void createBallAt(World world, double x, double y) {
		// create a ball
		Circle ballShape = new Circle(2.0);

		BodyFixture ballFixture = new BodyFixture(ballShape);
		ballFixture.setDensity(0.2);
		ballFixture.setFriction(0.3);
		ballFixture.setRestitution(0.2);
//		mniejsze odbijanie się (chyba)
//		ballFixture.setFilter(BALL);

		Body ball = new Body();
		ball.addFixture(ballFixture);
		ball.setMass(MassType.NORMAL);
		ball.translate(x / GUI.SCALE, -y / GUI.SCALE);

		world.addBody(ball);
	}

	private void createBoxAt(World world, double x, double y) {
		// create a box
		Rectangle rectShape = new Rectangle(2.0, 2.0);

		BodyFixture boxFixture = new BodyFixture(rectShape);
		boxFixture.setDensity(0.2);
		boxFixture.setFriction(0.3);
		boxFixture.setRestitution(0.2);

		Body box = new Body();
		box.addFixture(boxFixture);
		box.setMass(MassType.NORMAL);
		box.translate(x / GUI.SCALE, -y / GUI.SCALE);

		world.addBody(box);
	}

	private void populate(World world) {
		// Random generator
		Random rand = new Random(System.currentTimeMillis());

		for (int i = 0; i < 12; i++) {
			Rectangle rectShape = new Rectangle(1f + rand.nextFloat() * 4, 1f + rand.nextFloat() * 4);

			BodyFixture boxFixture = new BodyFixture(rectShape);
			boxFixture.setDensity(0.2);
			boxFixture.setFriction(0.3);
			boxFixture.setRestitution(0.2);

			Body box = new Body();
			box.addFixture(boxFixture);
			box.setMass(MassType.NORMAL);
			box.translate(rand.nextInt(40) - 20, rand.nextInt(30) + 70);
			box.setAngularVelocity((rand.nextFloat() - 0.5f) * 16);

			world.addBody(box);
		}
	}
}
