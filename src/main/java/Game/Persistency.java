package Game;

import Game.configs.*;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistency {

    public LevelConfig loadLevel(String levelName) throws IOException, ClassNotFoundException {
        LevelConfig levelConfig;
        ObjectInputStream objectinputstream = null;
        FileInputStream streamIn = new FileInputStream(filePath(levelName));
        objectinputstream = new ObjectInputStream(streamIn);
        levelConfig = (LevelConfig) objectinputstream.readObject();

        return levelConfig;
    }

    public void saveLevel(String levelName, LevelConfig levelConfig) {
        try (FileOutputStream fos = new FileOutputStream(filePath(levelName));
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(levelConfig);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private String filePath(String levelName) {
        return dataDirectoryPath() + "lvl_" + levelName + ".dat";
    }

    private String dataDirectoryPath() {
        String directoryName = "levelsData";
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directoryName + File.separatorChar;
    }
}
