package game;

import app.App;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Square {

    public enum TYPE {
        WALL,
        FREE,
        SPAWN,
        HOLE,
        ONE_WAY_UP,
        ONE_WAY_RIGHT,
        ONE_WAY_DOWN,
        ONE_WAY_LEFT
    }

    public enum HoleType {
        BLUE,
        ORANGE,
        RED,
        GREEN,
        YELLOW,
        NEUTRAL
    }

    private float w;
    private float h;
    private float posX;
    private float posY;
    private TYPE type;

    private PApplet sketch;
    private PImage img;
    private boolean main_hole;
    private String hType = null;
    public float attractionR;

    private Grid grid;

    public Square(PApplet sketch, float posX, float posY, float w, float h, TYPE type) {
        this.posX = posX;
        this.posY = posY;
        this.w = w;
        this.h = h;
        this.type = type;

        this.sketch = sketch;
    }

    public Square(float posX, float posY, float w, float h, TYPE type) {
        this.posX = posX;
        this.posY = posY;
        this.w = w;
        this.h = h;
        this.type = type;
    }

    public Square(float posX, float posY, float w, float h, TYPE type, HoleType hType, boolean main) {
        this.posX = posX;
        this.posY = posY;
        this.w = w;
        this.h = h;
        this.type = type;

        main_hole = main;

        String name = null;

        if (hType == HoleType.BLUE) name = "blue";
        else if (hType == HoleType.GREEN) name = "green";
        else if (hType == HoleType.NEUTRAL) name = "neutral";
        else if (hType == HoleType.ORANGE) name = "orange";
        else if (hType == HoleType.RED) name = "red";
        else if (hType == HoleType.YELLOW) name = "yellow";

        this.hType = name;

        attractionR = w / 3;

        //img = sketch.loadImage("img/hole_" + name + ".bmp");
    }

    public Square(PApplet sketch, float posX, float posY, float w, float h, TYPE type, boolean main, HoleType hType) {
        this.posX = posX;
        this.posY = posY;
        this.w = w;
        this.h = h;
        this.type = type;

        this.sketch = sketch;

        main_hole = main;

        attractionR = w / 3;

        String name = "";

        if (hType == HoleType.BLUE) name = "blue";
        else if (hType == HoleType.GREEN) name = "green";
        else if (hType == HoleType.NEUTRAL) name = "neutral";
        else if (hType == HoleType.ORANGE) name = "orange";
        else if (hType == HoleType.RED) name = "red";
        else if (hType == HoleType.YELLOW) name = "yellow";

        String path = "";

        try {
//            String s = String.valueOf(new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI()));
//            System.out.println(s + " <<<<<<<<<<<<<<<< path");

            String pathh = App.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String decodedPath = URLDecoder.decode(pathh, "UTF-8");

//            System.out.println(decodedPath + " >>>>>>>>>>>>>>>>>>  path 2.0");

            //System.out.println(URLDecoder.decode(ClassLoader.getSystemClassLoader().getResource(".").getPath(), "UTF-8"));

            String[] arr = decodedPath.split("/");
//            System.out.println(Arrays.toString(arr));

            path = "";

            for(int i=1; i<arr.length-1; i++) {
                path += arr[i] + "/";
            }

            System.out.println(path);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("path >>>>>>>>>>>>> " + path + "/img/hole_" + name + ".bmp");

        img = sketch.loadImage(path + "/img/hole_" + name + ".bmp");

    }

    public void show() {
        sketch.stroke(200);
        sketch.strokeWeight(1);
        if (type == TYPE.WALL) {
            sketch.fill(100);
            sketch.rect(posX, posY, w, h);
        } else if (type == TYPE.FREE) {
            sketch.fill(220);
            sketch.rect(posX, posY, w, h);
        } else if (type == TYPE.SPAWN) {
            sketch.fill(200);
            //sketch.ellipseMode(CENTER);

            sketch.rect(posX, posY, w, h);

            sketch.fill(150);
            sketch.ellipse(posX + w / 2f, posY + h / 2f, w / 3f, w / 3f);
        } else if (type == TYPE.ONE_WAY_DOWN) {
            sketch.fill(150);

            sketch.rect(posX, posY, w, h);

            sketch.fill(3, 84, 0);
            sketch.noStroke();

            sketch.beginShape();

            sketch.vertex(posX + w / 3, posY + h / 3);
            sketch.vertex(posX + w / 3 * 2, posY + h / 3);

            sketch.vertex(posX + w / 2, posY + h / 3 * 2);

            sketch.endShape();
        } else if (type == TYPE.ONE_WAY_UP) {
            sketch.fill(150);

            sketch.rect(posX, posY, w, h);

            sketch.fill(3, 84, 0);
            sketch.noStroke();

            sketch.beginShape();

            sketch.vertex(posX + w / 3, posY + h / 3 * 2);
            sketch.vertex(posX + w / 3 * 2, posY + h / 3 * 2);

            sketch.vertex(posX + w / 2, posY + h / 3);

            sketch.endShape();
        } else if (type == TYPE.ONE_WAY_RIGHT) {
            sketch.fill(150);

            sketch.rect(posX, posY, w, h);

            sketch.fill(0, 6, 84);
            sketch.noStroke();

            sketch.beginShape();

            sketch.vertex(posX + w / 3, posY + h / 3);
            sketch.vertex(posX + w / 3, posY + h / 3 * 2);

            sketch.vertex(posX + w / 3 * 2, posY + h / 2);

            sketch.endShape();
        } else if (type == TYPE.ONE_WAY_LEFT) {
            sketch.fill(150);

            sketch.rect(posX, posY, w, h);

            sketch.fill(0, 6, 84);
            sketch.noStroke();

            sketch.beginShape();

            sketch.vertex(posX + w / 3 * 2, posY + h / 3);
            sketch.vertex(posX + w / 3 * 2, posY + h / 3 * 2);

            sketch.vertex(posX + w / 3, posY + h / 2);

            sketch.endShape();
        } else {


            if (main_hole) {
                sketch.stroke(200);
                sketch.fill(220);
                sketch.strokeWeight(2);
                sketch.rect(posX, posY, w, h);
                sketch.image(img, posX + 1, posY + 1, w - 1, h - 1);
            }

        }


    }

    public TYPE getType() {
        return type;
    }

    public float getW() {
        return w;
    }

    public float getH() {
        return h;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
        if (hType != null) {
            img = sketch.loadImage("img/hole_" + hType + ".bmp");
        }
    }

    public String getHType() {
        return hType;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public void del(Ball ball) {
        for (Ball b : grid.balls) {
            if (b == ball) {
                grid.balls.remove(b);
                return;
            }
        }
    }
}
