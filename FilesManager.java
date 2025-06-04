import java.io.*;
import java.util.*;

public class FilesManager {
    public static final String DATADIR = "data";

    public static String[] listAvailableFiles() {
        File dir = new File(DATADIR);
        if (!dir.exists() || !dir.isDirectory()) {
            return new String[0]; // No files or directory doesn't exist
        }
        return dir.list((dir1, name) -> name.endsWith(".dat"));
    }

    public static boolean doesFileExist(String filename) {
        File file = new File(DATADIR, filename);
        return file.exists() && file.isFile();
    }

    public static DataContainer loadDataFromFile(String filename) {
        File file = new File(DATADIR, filename);
        if (!file.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (DataContainer) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean saveDataToFile(DataContainer data, String filename) {
        File folder = new File(DATADIR);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, filename);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
