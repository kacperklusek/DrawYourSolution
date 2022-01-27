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

import javafx.scene.paint.Color;
import org.dyn4j.collision.Fixture;
import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.AABB;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Vector2;

import java.util.Random;

public class Rectangle extends javafx.scene.shape.Rectangle implements BodyListener {
	private static Random randomGenerator = new Random(System.currentTimeMillis());

	public Rectangle() {
		setSmooth(true);
		setFill(Color.color(randomGenerator.nextDouble() * 0.75,
				randomGenerator.nextDouble() * 0.75,
				randomGenerator.nextDouble() * 0.75));
	}

	@Override
	public void bodyUpdate(BodyEvent e) {
		if (e.getType() == BodyEvent.Type.BODY_UPDATE) {
			Body body = e.getSource();

			Vector2 position = body.getWorldCenter();
			double angle = body.getTransform().getRotationAngle();
			Fixture fixture = body.getFixture(0);
			Convex bodyShape = fixture.getShape();

			AABB aabb = bodyShape.createAABB();
			setX((position.x + aabb.getMinX()) * GUI.SCALE);
			setY((-position.y + aabb.getMinY()) * GUI.SCALE);
			setWidth(aabb.getWidth() * GUI.SCALE);
			setHeight(aabb.getHeight() * GUI.SCALE);
			rotateProperty().set(-Math.toDegrees(angle));
		}
	}
}
