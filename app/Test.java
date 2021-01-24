package app;

import app.scene.*;
import processing.core.PApplet;
import processing.core.PImage;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static app.scene.SceneManager.SceneType.*;


public class Test extends PApplet implements SceneManager {


    int w;
    int panelHeight;

    Scene scene;
    Settings settings;

    public void settings() {
        loadSettings();
        size(w, w + panelHeight);
        System.out.println(getAbsolutePathToGame());
    }

    public void setup() {
        scene = new MenuScene(this, w, w + panelHeight, panelHeight);
        scene.setSceneManager(this);
//        scene.swapFunction(this::swapScenes);
//        scene.titleFunction(this::setTitle);
        scene.applySettings(settings);
        scene.init();

        setTitle("InkBall");
        PImage icon = loadImage(getAbsolutePathToGame() + "img/ico.bmp");
        surface.setIcon(icon);
//        System.out.println("loading file >>>> " + getAbsolutePathToGame());
//        Settings.saveSettings("config/settings.txt", settings);
    }

    public void draw() {
        background(220);
        scene.show();
    }

    public void mousePressed() {
        scene.mousePressed();
    }

    public void mouseReleased() {
        scene.mouseReleased();
    }

    public void loadSettings() {
        try {
            settings = Settings.getSettings("config/settings.txt");
        } catch (Exception e) {
            settings = new Settings();
        }
        w = settings.resolution;
        panelHeight = w / 20;
    }

    public void setTitle(String title) {
        surface.setTitle(title);
    }

    @Override
    public void quitApp() {
        Settings.saveSettings("config/settings.txt", settings);
        System.exit(0);
    }

    @Override
    public String getAbsolutePathToGame() {
        String filePath = "";

        String path = App.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
        String[] arr = decodedPath.split("/");
        for (int i = 1; i < arr.length - 1; i++) {
            filePath += "/" + arr[i];
        }
        filePath += "/";

        return filePath;
    }

    @Override
    public void swapScenes(SceneType type) {
        scene.clear();
        if (type == MENU) {
            scene = new MenuScene(this, w, w + panelHeight, panelHeight);
        } else if (type == GAME) {
            scene = new LevelScene(this, w, w + panelHeight, panelHeight);
        } else if (type == SETTINGS) {
            scene = new SettingsScene(this, w, w + panelHeight, panelHeight);
        }

//        scene.swapFunction(this::swapScenes);
//        scene.titleFunction(this::setTitle);
        scene.setSceneManager(this);
        scene.applySettings(settings);
        scene.init();
    }

    @Override
    public void fps(float fps) {
        frameRate(fps);
    }

//    private String getAbsolutePathToGame() {
//        String filePath = "";
//
//        String path = App.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//        String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
//        String[] arr = decodedPath.split("/");
//        for (int i = 1; i < arr.length - 1; i++) {
//            filePath += "/" + arr[i];
//        }
//        filePath += "/";
//        System.out.println(filePath);
//
//        return filePath;
//    }

    public static void main(String[] args) {
//        Settings.saveSettings("config/settings.txt", new Settings());
        PApplet.main("app.Test", args);
    }

}
