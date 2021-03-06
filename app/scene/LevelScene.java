package app.scene;

import app.App;
import app.Test;
import game.Ball;
import game.Grid;
import gui.Button;
import processing.core.PApplet;
import reader.MapFromFile;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static processing.core.PConstants.BLUR;

public class LevelScene extends Scene {

    int level = 0;
    boolean pause;
    boolean gameStarted = true;

    Button next;
    Button prev;
    Button reset;
    Button pauseButton;
    Button quitPause;       // quit button, only active when pause == true
    Button resumePause;     // resume button, only active when pause == true

    String levelPath;
    Grid game;

    public LevelScene(PApplet sketch, int width, int height, int panelHeight) {
        super(sketch, width, height, panelHeight);


//        String path = App.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//        String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
//        String[] arr = decodedPath.split("/");
//        levelPath = "";
//        for (int i = 1; i < arr.length - 1; i++) {
//            levelPath += "/" + arr[i];
//        }
//        levelPath += "/";
//        System.out.println(levelPath);
//
//        if (!(App.class.getResource("App.class").toString().split(":")[0].equals("jar"))) {
//            System.out.println("not jar -- " + Arrays.toString(App.class.getResource("App.class").toString().split(":")));
//        } else {
//            System.out.println("jar");
//        }


    }

    public void togglePause() {
        togglePause(!pause);
    }

    public void togglePause(boolean state) {
        pause = state;
        if (state) {
            quitPause.setActive(true);
            resumePause.setActive(true);

            pauseButton.setActive(false);
            reset.setActive(false);
            next.setActive(false);
            prev.setActive(false);
            sketch.frameRate(config.pauseFps);
        } else {
            quitPause.setActive(false);
            resumePause.setActive(false);

            pauseButton.setActive(true);
            reset.setActive(true);
            next.setActive(true);
            prev.setActive(true);

            sketch.frameRate(config.fps);
        }
    }

    private void openInEditor() {

    }

    private void prevLevel() {
        level--;
        resetLevel();
    }

    private void nextLevel() {
        level++;
        resetLevel();
    }

    private void resetLevel() {
        File f = new File(levelPath + "levels/level" + level + ".map");
        if (f.exists() && !f.isDirectory()) {
            MapFromFile file = new MapFromFile(levelPath + "levels/level" + level + ".map", width, height - panelHeight);
            game = file.generate();
            game.setAll(sketch);
            setTitle("InkBall - level " + (level+1));
            System.out.println("loaded level " + (level + 1));
//            sketch.surface.setTitle("Ink Ball - Level " + (level + 1));
        } else if (level < 0) {
            while (level != 0) level++;
        } else {
            level--;
        }
    }

    private void endGame() {
        sketch.frameRate(config.fps);
//        swap.swap(Test.SceneType.MENU);
        manager.swapScenes(SceneManager.SceneType.MENU);
    }

    @Override
    public void init() {

        levelPath = manager.getAbsolutePathToGame();
//        System.out.println(levelPath + " level path <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

//        resetLevel();

//        File f = new File(levelPath + "levels/level" + level + ".map");
//
//        if (f.exists()) {
//            MapFromFile file = new MapFromFile(levelPath + "levels/level" + level + ".map", width, height - panelHeight);
//            System.out.println("loaded level 1");
//
//            game = file.generate();
//            game.setAll(sketch);
//        }

        manager.fps(config.fps);

        pauseButton = new Button(sketch, "Quit", config.textSize / 2, width / 4, height / 5 * 3, width / 2, height / 10);
        pauseButton.setAction(this::endGame);

        reset = new Button(sketch, "Restart", config.textSize / 2, width - (width / 8 * 2), -panelHeight, width / 8, panelHeight);
        reset.setAction(this::resetLevel);
        reset.setActive(false);

        next = new Button(sketch, "Next level", config.textSize / 2, width - (width / 8 * 3), -panelHeight, width / 8, panelHeight);
        next.setAction(this::nextLevel);
        next.setActive(false);

        prev = new Button(sketch, "Previous level", config.textSize / 2, width - (width / 8 * 4), -panelHeight, width / 8, panelHeight);
        prev.setAction(this::prevLevel);
        prev.setActive(false);

        quitPause = new Button(sketch, "Quit to main menu", config.textSize, width / 4, (int) (height / 5 * 2.5), width / 2, height / 10);
        quitPause.setAction(this::endGame);
        quitPause.setActive(false);

        resumePause = new Button(sketch, "Resume", config.textSize, width / 4, (int) (height / 5 * 1.5), width / 2, height / 10);
        resumePause.setAction(this::togglePause);
        resumePause.setActive(false);

        gameStarted = true;
        pauseButton = new Button(sketch, "Pause", config.textSize / 2, width - width / 8, -panelHeight, width / 8, panelHeight);
        pauseButton.setAction(this::togglePause);

        pauseButton.setActive(true);
        reset.setActive(true);
        next.setActive(true);
        prev.setActive(true);

        resetLevel();
    }

    @Override
    public void show() {
        if (pause) {
            sketch.background(220, 250);
            sketch.filter(BLUR);
        } else {
            sketch.background(220);
        }
        sketch.translate(0, panelHeight);

        if (!pause) {
            if (gameStarted) {
                sketch.noFill();
                sketch.stroke(0);
                sketch.rect(0, -panelHeight, width / 6f, panelHeight);

                if (game.waiting.size() != 0) {
                    int i = 1;
                    for (int j : game.waiting.keySet()) {
                        Ball b = game.waiting.get(j);
                        int y = -panelHeight / 2;
                        int r = panelHeight / 2 - 1;
                        int x = (r + 2) * i;
                        sketch.noStroke();

                        int[] c = b.getColorArray();
                        if (j >= 0) {
                            if (j - 60 < game.getGameFrame()) {
                                if (game.getGameFrame() % 20 < 10) {
                                    sketch.fill(c[0], c[1], c[2]);
                                    sketch.ellipse(x, y, r, r);
                                }
                            } else {
                                sketch.fill(c[0], c[1], c[2]);
                                sketch.ellipse(x, y, r, r);
                            }
                        } else {
                            sketch.fill(c[0], c[1], c[2]);
                            sketch.ellipse(x, y, r, r);
                        }
                        i++;
                    }
                }
                game.update(sketch.mouseX, sketch.mouseY - panelHeight, sketch.pmouseX, sketch.pmouseY - panelHeight);
                game.show();

                if (game.balls.size() == 0 && game.waiting.size() == 0) {
                    nextLevel();
                }
            }

            pauseButton.show();
            reset.show();
            next.show();
            prev.show();
        } else {
            if (gameStarted) {
                game.show();

                sketch.noStroke();
                sketch.fill(220, 175);
                sketch.rect(0, -panelHeight, width, height + panelHeight);
            }
            sketch.fill(0);
            sketch.textSize(width / 10f);
            float w = sketch.textWidth("Pause");
            sketch.text("Pause", width / 2f - w / 2, height / 2f - width / 3f);

            quitPause.show();
            resumePause.show();
        }
    }

    @Override
    public void mousePressed() {
        if (!pause && gameStarted)
            game.startDraw(sketch.mouseX, sketch.mouseY - panelHeight, sketch.pmouseX, sketch.pmouseY - panelHeight);

        reset.clicked(sketch.mouseX, sketch.mouseY - panelHeight);
        pauseButton.clicked(sketch.mouseX, sketch.mouseY - panelHeight);
        next.clicked(sketch.mouseX, sketch.mouseY - panelHeight);
        prev.clicked(sketch.mouseX, sketch.mouseY - panelHeight);
        quitPause.clicked(sketch.mouseX, sketch.mouseY - panelHeight);
        resumePause.clicked(sketch.mouseX, sketch.mouseY - panelHeight);
    }

    @Override
    public void mouseReleased() {
        if (gameStarted) game.stopDraw();

        reset.released(sketch.mouseX, sketch.mouseY - panelHeight);
        pauseButton.released(sketch.mouseX, sketch.mouseY - panelHeight);
        next.released(sketch.mouseX, sketch.mouseY - panelHeight);
        prev.released(sketch.mouseX, sketch.mouseY - panelHeight);
        quitPause.released(sketch.mouseX, sketch.mouseY - panelHeight);
        resumePause.released(sketch.mouseX, sketch.mouseY - panelHeight);
    }

    public void setGameStatus(boolean status) {
        this.gameStarted = status;
    }
}
