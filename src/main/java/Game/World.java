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

package Game;

import Game.configs.ItemConfig;
import Game.configs.ItemConfigParser;
import Game.configs.TargetConfig;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.joint.Joint;

import java.util.ArrayList;
import java.util.List;

public class World extends org.dyn4j.world.World implements BoardStatePublisher{
	List<BodyWrapper> bodies = new ArrayList<>();
	ItemConfigParser itemConfigParser = new ItemConfigParser();

	// time step for Box2D.
	float timeStep = 1f / 60f;

	// listeners
	private final List<BoardStateListener> listeners = new ArrayList<>();

	public World() {
		super();
	}


	public void addItem(ItemConfig itemConfig) {
		List<Body> bodies = itemConfigParser.parseBodies(itemConfig);
		List<Joint<Body>> joints = itemConfigParser.parseJoints(bodies, itemConfig.jointType);

		for (Body body: bodies) {
			System.out.println("addbody");
			addBody(body);
		}
		for (Joint<Body> joint: joints) {
			super.addJoint(joint);
		}

	}

	public void addBody(Body newBody) {
		super.addBody(newBody);
		BodyWrapper bodyWrapper = new BodyWrapper(newBody);
		this.bodies.add(bodyWrapper);
		fireBodyAdded(bodyWrapper);
	}

	public void update() {
		update(this.timeStep); // be aware that updatev exists!

		this.bodies.stream()
				.filter(bodyWrapper -> (!bodyWrapper.getBody().isAtRest()))
				.forEach(BodyWrapper::update);
		fireWorldUpdate();
	}

	@Override
	public void addBoardStateListener(BoardStateListener worldListener) {
		this.listeners.add(worldListener);
	}

	@Override
	public void removeBoardStateListener(BoardStateListener worldListener) {
		this.listeners.remove(worldListener);
	}

	@Override
	public void notifyAllWorldUpdate(WorldEvent event) {
		for(BoardStateListener listener : listeners) {
			listener.worldUpdate(event);
		}
	}

	@Override
	public void notifyAllTargetAdded(TargetConfig targetConfig) {
		throw new UnsupportedOperationException();
	}


	private void fireWorldUpdate() {
		final WorldEvent e = new WorldEvent(this, WorldEvent.Type.WORLD_UPDATE);
		notifyAllWorldUpdate(e);
	}

	private void fireBodyAdded(BodyWrapper body) {
		final WorldEvent e = new WorldEvent(this, body, WorldEvent.Type.BODY_ADDED);
		notifyAllWorldUpdate(e);
	}

	private void fireBodyRemoved(BodyWrapper body) {
		final WorldEvent e = new WorldEvent(this, body, WorldEvent.Type.BODY_REMOVED);
		notifyAllWorldUpdate(e);
	}
}
