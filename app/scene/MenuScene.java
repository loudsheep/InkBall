package app.scene;

import app.App;
import app.Test;
import gui.Button;
import processing.core.PApplet;
import reader.MapFromFile;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class MenuScene extends Scene {

    Button start;
    Button settings;
    Button quit;

    String[] version;

    public MenuScene(PApplet sketch, int width, int height, int panelHeight) {
        super(sketch, width, height, panelHeight);


        String configPath;

        String path = App.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
        String[] arr = decodedPath.split("/");
        configPath = "";
        for (int i = 1; i < arr.length - 1; i++) {
            configPath += "/" + arr[i];
        }
        configPath += "/";
//        System.out.println(configPath);

//        if (!(App.class.getResource("App.class").toString().split(":")[0].equals("jar"))) {
//            System.out.println("menu --- not jar -- " + Arrays.toString(App.class.getResource("App.class").toString().split(":")));
//        } else {
//            System.out.println("menu --- jar");
//        }


        File f = new File(configPath + "config/ver.txt");

        if (f.exists()) {
            try {
                version = new String[2];
                Scanner sc = new Scanner(f);
                version[0] = sc.nextLine();
                version[1] = sc.nextLine();

//                System.out.println(Arrays.toString(version));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void end() {
        manager.quitApp();
    }

    private void startGame() {
//        swap.swap(Test.SceneType.GAME);
        manager.swapScenes(SceneManager.SceneType.GAME);
    }

    private void enterSettings() {
//        swap.swap(Test.SceneType.SETTINGS);
        manager.swapScenes(SceneManager.SceneType.SETTINGS);
    }

    @Override
    public void init() {
        sketch.frameRate(config.menuFps);

        setTitle("InkBall");

        start = new Button(sketch, "Start game", config.textSize, width / 4, height / 5, width / 2, height / 10);
        start.setAction(this::startGame);

        settings = new Button(sketch, "Settings", config.textSize, width / 4, height / 5 * 2, width / 2, height / 10);
        settings.setAction(this::enterSettings);

        quit = new Button(sketch, "Quit", config.textSize, width / 4, height / 5 * 3, width / 2, height / 10);
        quit.setAction(this::end);

        start.setActive(true);
        settings.setActive(true);
        quit.setActive(true);
    }

    @Override
    public void show() {
        start.show();
        settings.show();
        quit.show();

        if (version != null && version.length >= 2) {
            sketch.strokeWeight(1);
            sketch.fill(0);
            sketch.textSize(config.textSize);
            sketch.text(version[0], 10, height - config.textSize);

            float w = sketch.textWidth(version[1]);
            sketch.text(version[1], width - w - 10, height - config.textSize);
        }
    }

    @Override
    public void mousePressed() {
        start.clicked(sketch.mouseX, sketch.mouseY);
        settings.clicked(sketch.mouseX, sketch.mouseY);
        quit.clicked(sketch.mouseX, sketch.mouseY);
    }

    @Override
    public void mouseReleased() {
        start.released(sketch.mouseX, sketch.mouseY);
        settings.released(sketch.mouseX, sketch.mouseY);
        quit.released(sketch.mouseX, sketch.mouseY);
    }
}
