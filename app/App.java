package app;

import game.Ball;
import game.Grid;
import gui.Button;
import gui.Color;
import processing.core.PApplet;
import reader.MapFromFile;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class App extends PApplet {


    int w = 600;
    int panelHeight;

    Button start;
    Button pauseButton;
    Button reset;
    Button next;
    Button prev;
    Button editor;

    Button quitPause;       // quit button, only active when pause == true
    Button resumePause;     // resume button, only active when pause == true

    Grid game;

    String pathh;

    boolean pause;


    boolean gameStarted = false;

    int level = 0;

    public void settings() {
        panelHeight = w / 20;
        size(w, w + panelHeight);
    }

    public void setup() {

        pause = false;

        surface.setTitle("Ink Ball");
        //surface.setLocation(1000,100);

        start = new Button(this, "Start game", height / 30, width / 4, height / 5, width / 2, height / 10);
        start.setStrokeColor(new Color(0, 0, 0));
        start.setFillColor(new Color(255, 255, 255));
        start.setTextColor(new Color(0, 0, 0));
        start.setAction(this::startGame);

        pauseButton = new Button(this, "Quit", height / 30, width / 4, height / 5 * 3, width / 2, height / 10);
        pauseButton.setAction(this::end);

        reset = new Button(this, "Restart", height / 60, width - (width / 8 * 2), -panelHeight, width / 8, panelHeight);
        reset.setAction(this::reset);
        reset.setActive(false);


        next = new Button(this, "Next level", height / 60, width - (width / 8 * 3), -panelHeight, width / 8, panelHeight);
        next.setAction(this::next);
        next.setActive(false);

        prev = new Button(this, "Previous level", height / 60, width - (width / 8 * 4), -panelHeight, width / 8, panelHeight);
        prev.setAction(this::prev);
        prev.setActive(false);

        editor = new Button(this, "Open in Level Editor", height / 60, width - (width / 8 * 6), -panelHeight, width / 8 * 2, panelHeight);
        editor.setAction(this::openInEditor);
        editor.setActive(false);

        quitPause = new Button(this, "Quit", height / 30, width / 4, (int) (height / 5 * 2.5), width / 2, height / 10);
        quitPause.setAction(this::end);
        quitPause.setActive(false);

        resumePause = new Button(this, "Resume", height / 30, width / 4, (int) (height / 5 * 1.5), width / 2, height / 10);
        resumePause.setAction(this::togglePause);
        resumePause.setActive(false);

        String path = App.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
        String[] arr = decodedPath.split("/");
        pathh = "";
        for (int i = 1; i < arr.length - 1; i++) {
            pathh += "/" + arr[i];
        }
        pathh += "/";
        System.out.println(pathh);

        if (!(App.class.getResource("App.class").toString().split(":")[0].equals("jar"))) {
            System.out.println("not jar -- " + Arrays.toString(App.class.getResource("App.class").toString().split(":")));
        } else {
            System.out.println("jar");
        }

        File f = new File(pathh + "levels/level" + level + ".map");

        if (f.exists()) {

            MapFromFile file = new MapFromFile(pathh + "levels/level" + level + ".map", width, height - panelHeight);
            System.out.println("loaded level 1");

            game = file.generate();
            game.setAll(this);


        }


    }

    public void draw() {
        if (pause) {
            background(220, 250);
            filter(BLUR);
        } else {
            background(220);
        }
        translate(0, panelHeight);

        if (!pause) {
            if (gameStarted) {
                noFill();
                stroke(0);
                rect(0, -panelHeight, width / 6f, panelHeight);

                if (game.waiting.size() != 0) {
                    int i = 1;

                    //for (Ball b : game.wait.values()) {
                    for (int j : game.waiting.keySet()) {

                        Ball b = game.waiting.get(j);

                        int y = -panelHeight / 2;
                        int r = panelHeight / 2 - 1;

                        int x = (r + 2) * i;

                        noStroke();
                        //fill(0);

                        int[] c = b.getColorArray();

                        if (j >= 0) {
                            if (j - 60 < game.getGameFrame()) {
                                if (game.getGameFrame() % 20 < 10) {
                                    fill(c[0], c[1], c[2]);
                                    ellipse(x, y, r, r);
                                }
                            } else {
                                fill(c[0], c[1], c[2]);
                                ellipse(x, y, r, r);
                            }
                        } else {
                            fill(c[0], c[1], c[2]);
                            ellipse(x, y, r, r);
                        }


                        i++;

                    }
                }

                game.update(mouseX, mouseY - panelHeight, pmouseX, pmouseY - panelHeight);
                game.show();

                if (game.balls.size() == 0 && game.waiting.size() == 0) {
                    next();
                }

            }

            pauseButton.show();
            reset.show();
            next.show();
            start.show();
            prev.show();
            //editor.show();


//            text(frameRate, 10, 10);
        } else {

            if (gameStarted) {
                game.show();

                noStroke();
                fill(220, 175);
                rect(0, -panelHeight, width, height + panelHeight);
            }

            fill(0);
            textSize(width / 10f);
            float w = textWidth("Pause");
            text("Pause", width / 2 - w / 2, height / 2 - width / 3f);

            quitPause.show();
            resumePause.show();
        }


    }

    public void mousePressed() {
        if (!pause) if (gameStarted) game.startDraw(mouseX, mouseY - panelHeight, pmouseX, pmouseY - panelHeight);

        start.clicked(mouseX, mouseY - panelHeight);
        reset.clicked(mouseX, mouseY - panelHeight);
        pauseButton.clicked(mouseX, mouseY - panelHeight);
        next.clicked(mouseX, mouseY - panelHeight);
        prev.clicked(mouseX, mouseY - panelHeight);
        //editor.clicked(mouseX, mouseY - panelHeight);
        quitPause.clicked(mouseX, mouseY - panelHeight);
        resumePause.clicked(mouseX, mouseY - panelHeight);
    }

    public void mouseReleased() {
        if (gameStarted) game.stopDraw();

        start.released(mouseX, mouseY - panelHeight);
        reset.released(mouseX, mouseY - panelHeight);
        pauseButton.released(mouseX, mouseY - panelHeight);
        next.released(mouseX, mouseY - panelHeight);
        prev.released(mouseX, mouseY - panelHeight);
        //editor.released(mouseX, mouseY - panelHeight);
        quitPause.released(mouseX, mouseY - panelHeight);
        resumePause.released(mouseX, mouseY - panelHeight);


    }

    public void startGame() {
        gameStarted = true;

        start.setActive(false);
        pauseButton = new Button(this, "Pause", height / 60, width - width / 8, -panelHeight, width / 8, panelHeight);
        pauseButton.setAction(this::togglePause);

        reset.setActive(true);
        next.setActive(true);
        prev.setActive(true);
        //editor.setActive(true);

        reset();

    }

    public void end() {
        System.exit(0);
    }

    public void reset() {
        File f = new File(pathh + "levels/level" + level + ".map");

        if (f.exists() && !f.isDirectory()) {

            MapFromFile file = new MapFromFile(pathh + "levels/level" + level + ".map", width, height - panelHeight);

            game = file.generate();
            game.setAll(this);
            surface.setTitle("Ink Ball - Level " + (level + 1));
        } else if (level < 0) {
            while (level != 0) level++;
        } else {
            level--;
        }
    }

    public void next() {
        level++;
        reset();
    }

    public void prev() {
        level--;
        reset();
    }

    public void keyPressed() {
        if (keyCode == 27) {
            key = 0;
            if (gameStarted) {
                System.out.println("pause ----------------------------------------------------------------");
                togglePause();
            }
        }
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
            frameRate(10);
        } else {
            quitPause.setActive(false);
            resumePause.setActive(false);

            pauseButton.setActive(true);
            reset.setActive(true);
            next.setActive(true);
            prev.setActive(true);

            frameRate(60);
        }
    }

    public void openInEditor() {
        System.out.println("editorergregeg " + pathh);

//        File f = new File(pathh);
//
//        if (f.exists()) System.out.println("exists");
//
//        System.out.println(pathh);

        togglePause(true);
        //Maker.fromGame(pathh);


    }


    public static void main(String[] args) {
        PApplet.main("app.App", args);
    }

}
