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

import org.dyn4j.dynamics.Body;

import java.util.ArrayList;
import java.util.List;

public class BodyWrapper {
	private Body body;

	public Integer getTargetID() {
		return targetID;
	}

	private Integer targetID = null;

	// listeners
	private final List<BodyListener> listeners = new ArrayList<>();

	public BodyWrapper(Body body) {
		this.body = body;
	}

	public BodyWrapper(Body body, Integer targetID) {
		this.body = body;
		this.targetID = targetID;
	}

	public Body getBody() {
		return this.body;
	}

	public void update() {
		fireBodyUpdate();
	}

	public void addBodyListener(BodyListener bodyListener) {
		assert (bodyListener != null);
		this.listeners.add(bodyListener);
	}

	public void removeBodyListener(BodyListener bodyListener) {
		assert (bodyListener != null);
		this.listeners.remove(bodyListener);
	}

	private void fireEvent(BodyEvent e) {
		for (final BodyListener bodyListener : this.listeners) {
			bodyListener.bodyUpdate(e);
		}
	}

	private void fireBodyUpdate() {
		final BodyEvent e = new BodyEvent(getBody(), BodyEvent.Type.BODY_UPDATE, targetID);
		fireEvent(e);
	}
}
