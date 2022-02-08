package Game.gui;

public interface ButtonsListener {
    public void handleStart();

    public void handleStop();

    public void handleReset();

    public void handleSave(String levelName);

    public void handleLoad(String levelName);
}
