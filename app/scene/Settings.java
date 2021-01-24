package app.scene;

import app.App;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class Settings implements Serializable {
    public int resolution = 600;
    public int fps = 60;
    public int menuFps = 60;
    public int pauseFps = 10;
    public int textSize = 20;

    public Settings() {
    }

    public static Settings getSettings(String relativePath) throws Exception {
        String filePath = getPath();

//        String path = App.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//        String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
//        String[] arr = decodedPath.split("/");
//        for (int i = 1; i < arr.length - 1; i++) {
//            filePath += "/" + arr[i];
//        }
//        filePath += "/";
//        System.out.println(filePath + relativePath);

        FileInputStream fileIn = new FileInputStream(filePath + relativePath);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);

        Settings s = (Settings) objectIn.readObject();
        objectIn.close();
        fileIn.close();
        System.out.println("Settings loaded");
        return s;

    }

    private static String getPath() {
        String filePath = "";
        String path = App.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
        String[] arr = decodedPath.split("/");
        for (int i = 1; i < arr.length - 1; i++) {
            filePath += "/" + arr[i];
        }
        filePath += "/";
        return  filePath;
    }

    public static void saveSettings(String relativePath, Settings settings) {
        String filePath = getPath();
//        String path = App.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//        String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
//        String[] arr = decodedPath.split("/");
//        for (int i = 1; i < arr.length - 1; i++) {
//            filePath += "/" + arr[i];
//        }
//        filePath += "/";
//        System.out.println(filePath + relativePath);

//        if (!(App.class.getResource("App.class").toString().split(":")[0].equals("jar"))) {
//            System.out.println("not jar -- " + Arrays.toString(App.class.getResource("App.class").toString().split(":")));
//        } else {
//            System.out.println("jar");
//        }

        try {
            FileOutputStream fileOut = new FileOutputStream(filePath + relativePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(settings);
            objectOut.close();
            fileOut.close();
            System.out.println("Settings saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(" res: ").append(resolution);
        str.append(" fps: ").append(fps);
        str.append(" textSize: ").append(textSize);

        return str.toString();
    }
}
