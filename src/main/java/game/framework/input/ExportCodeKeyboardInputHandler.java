package game.framework.input;

import game.framework.SimulationBody;
import org.dyn4j.world.World;

import java.awt.*;

public class ExportCodeKeyboardInputHandler extends AbstractKeyboardInputHandler {
	private final World<SimulationBody> world;
	
	public ExportCodeKeyboardInputHandler(Component component, int key, World<SimulationBody> world) {
		super(component, key);
		this.world = world;
	}
	
	@Override
	protected void onKeyPressed() {
		super.onKeyPressed();
		System.out.println(CodeExporter.export("SampleExport", this.world));
	}

	@Override
	public boolean isActive() {
		return false;
	}
}
