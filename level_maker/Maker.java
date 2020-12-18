package level_maker;

import app.App;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;
import reader.MapFromFile;

import java.io.File;
import java.util.*;


public class Maker extends PApplet {

    public Maker() {

    }

    public Maker(String pathFrom) {
        MapFromFile map = new MapFromFile(pathFrom, 1, 1);
        ArrayList<game.Square> squares = map.generate().getGrid();

        rect = new ArrayList<>();

//        for(game.Square s: squares) {
//            if(s.getType() == game.Square.TYPE.HOLE) {
//                rect.add(new Square(this,s.getPosX(),s.getPosY(),))
//            }
//        }
    }

    private static final Map<Square.HoleType, int[]> ballColor;

    static {
        ballColor = new HashMap<>();
        ballColor.put(Square.HoleType.BLUE, new int[]{73, 82, 214});
        ballColor.put(Square.HoleType.ORANGE, new int[]{255, 137, 49});
        ballColor.put(Square.HoleType.RED, new int[]{247, 38, 46});
        ballColor.put(Square.HoleType.GREEN, new int[]{44, 187, 86});
        ballColor.put(Square.HoleType.YELLOW, new int[]{255, 252, 10});
        ballColor.put(Square.HoleType.NEUTRAL, new int[]{73, 82, 214});
    }

    public static class Square {

        public void setType(TYPE wall) {
            this.type = wall;
        }

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
        private Square.TYPE type;

        private Maker sketch;
        private PImage img;
        private boolean main_hole;
        private HoleType hType = HoleType.BLUE;

        public int i, j;
        public int maxI, maxJ;

        public Square(Maker sketch, float posX, float posY, float w, float h, Square.TYPE type, int i, int j, int maxI, int maxJ) {
            this.posX = posX;
            this.posY = posY;
            this.w = w;
            this.h = h;
            this.type = type;

            this.sketch = sketch;

            this.i = i;
            this.j = j;
            this.maxI = maxI;
            this.maxJ = maxJ;
        }

        public Square(float posX, float posY, float w, float h, Square.TYPE type) {
            this.posX = posX;
            this.posY = posY;
            this.w = w;
            this.h = h;
            this.type = type;
        }

        public Square(float posX, float posY, float w, float h, Square.TYPE type, Square.HoleType hType, boolean main) {
            this.posX = posX;
            this.posY = posY;
            this.w = w;
            this.h = h;
            this.type = type;

            main_hole = main;

            String name = "";

            if (hType == Square.HoleType.BLUE) name = "blue";
            else if (hType == Square.HoleType.GREEN) name = "green";
            else if (hType == Square.HoleType.NEUTRAL) name = "neutral";
            else if (hType == Square.HoleType.ORANGE) name = "orange";
            else if (hType == Square.HoleType.RED) name = "red";
            else if (hType == Square.HoleType.YELLOW) name = "yellow";

            this.hType = hType;

            //img = sketch.loadImage("img/hole_" + name + ".bmp");
        }

        public Square(PApplet sketch, float posX, float posY, float w, float h, Square.TYPE type, boolean main, Square.HoleType hType) {
            this.posX = posX;
            this.posY = posY;
            this.w = w;
            this.h = h;
            this.type = type;

            this.sketch = (Maker) sketch;

            main_hole = main;

            String name = "";

            if (hType == Square.HoleType.BLUE) name = "blue";
            else if (hType == Square.HoleType.GREEN) name = "green";
            else if (hType == Square.HoleType.NEUTRAL) name = "neutral";
            else if (hType == Square.HoleType.ORANGE) name = "orange";
            else if (hType == Square.HoleType.RED) name = "red";
            else if (hType == Square.HoleType.YELLOW) name = "yellow";

            img = sketch.loadImage("img/hole_" + name + ".bmp");

            this.hType = hType;

        }

        public Square(Maker sketch, float posX, float posY, float w, float h, int i, int j, int maxI, int maxJ, Square.TYPE type, boolean main, Square.HoleType hType) {
            this.posX = posX;
            this.posY = posY;
            this.w = w;
            this.h = h;
            this.type = type;

            this.sketch = sketch;

            main_hole = main;

            this.i = i;
            this.j = j;
            this.maxI = maxI;
            this.maxJ = maxJ;

            String name;

            if (hType == Square.HoleType.BLUE) name = "blue";
            else if (hType == Square.HoleType.GREEN) name = "green";
            else if (hType == Square.HoleType.NEUTRAL) name = "neutral";
            else if (hType == Square.HoleType.ORANGE) name = "orange";
            else if (hType == Square.HoleType.RED) name = "red";
            else if (hType == Square.HoleType.YELLOW) name = "yellow";
            else name = "blue";

            //img = sketch.loadImage("img/hole_" + name + ".bmp");
            setImg(sketch.img.get(name));


            this.hType = hType;

        }

        public void setImg(PImage img) {
            this.img = img;
        }

        public void show() {
            sketch.stroke(200);
            sketch.strokeWeight(1);
            if (type == Square.TYPE.WALL) {
                sketch.fill(100);
                sketch.rect(posX, posY, w, h);
            } else if (type == Square.TYPE.FREE) {
                sketch.fill(220);
                sketch.rect(posX, posY, w, h);
            } else if (type == Square.TYPE.SPAWN) {
                sketch.fill(200);
                //sketch.ellipseMode(CENTER);

                sketch.rect(posX, posY, w, h);

                sketch.fill(150);
                sketch.ellipse(posX + w / 2f, posY + h / 2f, w / 3f, w / 3f);
            } else if (type == Square.TYPE.ONE_WAY_DOWN) {
                sketch.fill(150);

                sketch.rect(posX, posY, w, h);

                sketch.fill(3, 84, 0);
                sketch.noStroke();

                sketch.beginShape();

                sketch.vertex(posX + w / 3, posY + h / 3);
                sketch.vertex(posX + w / 3 * 2, posY + h / 3);

                sketch.vertex(posX + w / 2, posY + h / 3 * 2);

                sketch.endShape();
            } else if (type == Square.TYPE.ONE_WAY_UP) {
                sketch.fill(150);

                sketch.rect(posX, posY, w, h);

                sketch.fill(3, 84, 0);
                sketch.noStroke();

                sketch.beginShape();

                sketch.vertex(posX + w / 3, posY + h / 3 * 2);
                sketch.vertex(posX + w / 3 * 2, posY + h / 3 * 2);

                sketch.vertex(posX + w / 2, posY + h / 3);

                sketch.endShape();
            } else if (type == Square.TYPE.ONE_WAY_RIGHT) {
                sketch.fill(150);

                sketch.rect(posX, posY, w, h);

                sketch.fill(0, 6, 84);
                sketch.noStroke();

                sketch.beginShape();

                sketch.vertex(posX + w / 3, posY + h / 3);
                sketch.vertex(posX + w / 3, posY + h / 3 * 2);

                sketch.vertex(posX + w / 3 * 2, posY + h / 2);

                sketch.endShape();
            } else if (type == Square.TYPE.ONE_WAY_LEFT) {
                sketch.fill(150);

                sketch.rect(posX, posY, w, h);

                sketch.fill(0, 6, 84);
                sketch.noStroke();

                sketch.beginShape();

                sketch.vertex(posX + w / 3 * 2, posY + h / 3);
                sketch.vertex(posX + w / 3 * 2, posY + h / 3 * 2);

                sketch.vertex(posX + w / 3, posY + h / 2);

                sketch.endShape();
            } else if (type == TYPE.HOLE) {


                if (main_hole) {
                    sketch.stroke(200);
                    sketch.fill(220);
                    sketch.strokeWeight(2);
                    sketch.rect(posX, posY, w * 3, h * 3);
                    sketch.image(img, posX + 1, posY + 1, w * 3 - 1, h * 3 - 1);
                }

            }


        }

        public Square.TYPE getType() {
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

        public void setW(float w) {
            this.w = w;
        }

        public void setH(float h) {
            this.h = h;
        }

        public void setPosX(float posX) {
            this.posX = posX;
        }

        public void setPosY(float posY) {
            this.posY = posY;
        }

        public void setSketch(Maker sketch) {
            this.sketch = sketch;
            if (hType != null) {
                img = sketch.loadImage("img/hole_" + hType + ".bmp");
            }
        }

        public void sethType(HoleType hType) {
            this.hType = hType;
        }

        public HoleType gethType() {
            return hType;
        }

        public void pressed(String mode, ArrayList<Square> sq, String color) {
            if (sketch.mouseX > posX && sketch.mouseX < posX + w && sketch.mouseY > posY && sketch.mouseY < posY + h) {

                if (i != 0 && i != maxI && j != maxJ && j != 0) {

//                    if (type == TYPE.HOLE && !main_hole) {
//                        return;
//                    }

                    if (mode.equals("HOLE")) {
                        if (type == TYPE.HOLE) {

                            if (hType == null) {
                                makeHole(sq, color);
                                return;
                            }
                            if (!hType.toString().equals(color.toUpperCase()))
                                makeHole(sq, color);
                        } else
                            makeHole(sq, color);
                        return;
                    }

                    if (type == TYPE.HOLE) {
                        System.out.println("holelelele");
                        if (main_hole) {
                            antiMakeHole(sq);
                            type = TYPE.valueOf(mode);
                            main_hole = false;

                        }

                        return;
                    }

                    type = TYPE.valueOf(mode);
                    main_hole = false;
                }
            }
        }

        public void antiMakeHole(ArrayList<Square> sq) {
            System.out.println("antmake hole");
            for (Square s : sq) {
//                    if(s.i == i) {
//                        if(s.i == i+1 || s.i == i+2) {
//                            s.setType(TYPE.HOLE);
//                            s.main_hole = false;
//                        }
//                    }
                if (s.i < i + 3 && s.j < j + 3) {
                    if (s.i >= i && s.j >= j) {
                        s.setType(TYPE.FREE);
                        s.main_hole = false;
                        //s.setImg(img);
                    }
                }
            }
        }

        public void makeHole(ArrayList<Square> sq, String color) {
            //System.out.println("make hole");
            if (i > 0 && j > 0 && i < maxI - 2 && j < maxJ - 2) { // we can make hole !!!
                System.out.println("make hole || " + i + "-" + j + " || " + maxI + "-" + maxJ);
//                System.out.println("make hole");

                type = TYPE.HOLE;

                main_hole = true;

                hType = HoleType.valueOf(color);

                String name = color.toLowerCase();

                //img = sketch.loadImage("img/hole_" + name + ".bmp");
                setImg(sketch.img.get(name));

                for (Square s : sq) {
                    if (s == this) continue;
//                    if(s.i == i) {
//                        if(s.i == i+1 || s.i == i+2) {
//                            s.setType(TYPE.HOLE);
//                            s.main_hole = false;
//                        }
//                    }
                    if (s.i < i + 3 && s.j < j + 3) {
                        if (s.i >= i && s.j >= j) {
                            if (s.getType() == TYPE.HOLE) {
                                if (s.main_hole) {
                                    s.antiMakeHole(sq);
                                }
                            }
                            s.setType(TYPE.HOLE);
                            s.sethType(HoleType.valueOf(color));
                            s.main_hole = false;
                            s.setImg(img);
                        }
                    }
                }
            }
        }
    }


    private int size = 20;
    private float sizeX, sizeY;
    private float originalHeight;

    private ArrayList<Square> rect;
    public HashMap<String, PImage> img;

    private String[] modes = {"WALL", "ONE_WAY_UP", "ONE_WAY_DOWN", "ONE_WAY_RIGHT", "ONE_WAY_LEFT", "SPAWN", "HOLE"};
    private int modeCount = 0;


    private String[] colors = {"BLUE", "ORANGE", "RED", "GREEN", "YELLOW", "NEUTRAL"};
    private int colorCount = 0;

    private ArrayList<String[]> ballSettings;

    private boolean addBall = false;
    private boolean dynamicMode = false;    // when adding bal to spawner
    private int ballX = 1;
    private int ballY = 1;
    private int rotation = 0;
    private int xx, yy;
    private int ballFrame = 10;

    public void settings() {
        size(800, 800);

        originalHeight = height;
    }

    public void setup() {
        rect = new ArrayList<>();

        sizeX = width / (float) size;
        sizeY = height / (float) size;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Square.TYPE type = Square.TYPE.FREE;
                if (i == 0 || i == size - 1 || j == 0 || j == size - 1) type = Square.TYPE.WALL;
                rect.add(new Square(this, sizeX * i, sizeY * j, sizeX, sizeY, type, i, j, size - 1, size - 1));
            }
        }

        img = new HashMap<>();

        img.put("blue", requestImage("img/hole_blue.bmp"));
        img.put("green", requestImage("img/hole_green.bmp"));
        img.put("neutral", requestImage("img/hole_neutral.bmp"));
        img.put("orange", requestImage("img/hole_orange.bmp"));
        img.put("red", requestImage("img/hole_red.bmp"));
        img.put("yellow", requestImage("img/hole_yellow.bmp"));

        ballSettings = new ArrayList<>();

        //ellipseMode(CENTER);
    }

    public void draw() {
        background(200);


        sizeX = width / (float) size;
        sizeY = height / (float) size;

        stroke(240);
        fill(200);
        for (Square s : rect) {
            s.show();
        }

        strokeWeight(2);

        for (int i = 0; i < ballSettings.size(); i++) {

            String[] s = ballSettings.get(i);

            if (s[0].equals("static")) {
                int[] col = ballColor.get(Square.HoleType.valueOf(s[6].toUpperCase()));

                stroke(col[0], col[1], col[2], 150);
                fill(col[0], col[1], col[2], 150);

                float xxx = (Integer.parseInt(s[2]) + 1) * (width / (float) size) - (width / (float) size / 2);
                float yyy = (Integer.parseInt(s[3]) + 1) * (width / (float) size) - (width / (float) size / 2);

                ellipse(xxx, yyy, width / (float) size, width / (float) size);

                stroke(0, 150);

                line(xxx, yyy, xxx + (Integer.parseInt(s[4]) * 30), yyy + (Integer.parseInt(s[5]) * 30));

                fill(0);
                textSize(12);
                text(i + 1, xxx, yyy);
            }
        }

        if (addBall) {

            int[] c = ballColor.get(Square.HoleType.valueOf(colors[colorCount]));
            stroke(c[0], c[1], c[2]);
            fill(c[0], c[1], c[2]);

            float x = (ballX + 1) * (width / (float) size) - (width / (float) size / 2);
            float y = (ballY + 1) * (width / (float) size) - (width / (float) size / 2);

            ellipse(x, y, width / (float) size, width / (float) size);

//            float xx = rotation % 2 == 0 ? 0 : 1;
//            float yy = rotation % 2 == 0 ? 1 : 0;

            if (!dynamicMode) {
                if (rotation == 0 || rotation == 2) {
                    xx = 0;
                } else if (rotation == 1) {
                    xx = 1;
                } else {
                    xx = -1;
                }

                if (rotation == 1 || rotation == 3) {
                    yy = 0;
                } else if (rotation == 2) {
                    yy = -1;
                } else {
                    yy = 1;
                }

                stroke(0);
                strokeWeight(3);
                line(x, y, x + (xx * 30), y + (yy * 30));
            }
        }

        strokeWeight(1);

        for (int i = ballSettings.size() - 1; i >= 0; i--) {


            String[] arr = ballSettings.get(i);

            if (arr[0].equals("static")) {
                if (Integer.parseInt(arr[2]) >= size - 1) {
                    ballSettings.remove(i);
                    surface.setSize(width, (int) (height - width / 20f));
                    continue;
                }

                if (Integer.parseInt(arr[3]) >= size - 1) {
                    ballSettings.remove(i);
                    surface.setSize(width, (int) (height - width / 20f));
                    continue;
                }

                float y = originalHeight + (i * width / 20f) + width / 20f / 2;

                stroke(0);
                noFill();

                rect(0, originalHeight + (i * width / 20f), width, width / 20f);

                stroke(255, 0, 0);
                fill(255, 0, 0, 50);
                rect(width / 10f * 9, originalHeight + (i * width / 20f), width / 10f, width / 20f);

                textSize(12);
                fill(255, 0, 0);
                text("Delete", (float) (width / 10 * 9.25), y + 6);


                int[] c = ballColor.get(Square.HoleType.valueOf(arr[6].toUpperCase()));

                stroke(c[0], c[1], c[2]);
                fill(c[0], c[1], c[2]);


                ellipse(width / 20f, y, 20, 20);

                fill(0);

                textSize(20);

                text("Position: " + arr[2] + "x" + arr[3], width / 20f * 3, y + 10);
                text(i + 1, width / 20f / 3, y + 10);
            } else {

                float y = originalHeight + (i * width / 20f) + width / 20f / 2;

                stroke(0);
                noFill();

                rect(0, originalHeight + (i * width / 20f), width, width / 20f);

                stroke(255, 0, 0);
                fill(255, 0, 0, 50);
                rect(width / 10f * 9, originalHeight + (i * width / 20f), width / 10f, width / 20f);

                textSize(12);
                fill(255, 0, 0);
                text("Delete", (float) (width / 10 * 9.25), y + 6);


                int[] c = ballColor.get(Square.HoleType.valueOf(arr[2].toUpperCase()));

                stroke(c[0], c[1], c[2]);
                fill(c[0], c[1], c[2]);

                ellipse(width / 20f, y, 20, 20);

                fill(0);

                textSize(20);

                text("Frame: " + arr[3] + "~" + Integer.parseInt(arr[3]) / 60f + "s", width / 20f * 3, y + 10);
                text(i + 1, width / 20f / 3, y + 10);
            }
        }


        fill(0);

        if (colorCount < 0) {
            colorCount = colors.length - 1;
        }

        if (modeCount < 0) {
            modeCount = modes.length - 1;
        }

        textSize(12);

        text("Mode: " + modes[modeCount], 10, 10);
        text("Color: " + colors[colorCount], 10, 20);
        text("Size: " + size + "x" + size, 10, 30);

        if (addBall) {
            fill(255, 0, 0);
            textSize(25);
            if (dynamicMode)
                text("BALL SETTINGS - DYNAMIC - FRAME " + ballFrame + "(" + ballFrame / 60f + "s)", 100, 20);
            else
                text("BALL SETTINGS - STATIC", 100, 20);
        }

        if (mousePressed) {
            if (!addBall) {
                if (mouseButton == LEFT) {
                    for (Square s : rect) {
                        s.pressed(modes[modeCount], rect, colors[colorCount]);
                    }
                } else if (mouseButton == RIGHT) {
                    for (Square s : rect) {
                        s.pressed("FREE", rect, colors[colorCount]);
                    }

                }
            } else {
                ballX = (int) (mouseX / (float) width * size);
                ballY = (int) (mouseY / (float) width * size);

                if (ballX < 1) ballX = 1;
                if (ballX > size - 2) ballX = size - 2;

                if (ballY < 1) ballY = 1;
                if (ballY > size - 2) ballY = size - 2;
            }
        }

        if (ballX < 1) ballX = 1;
        if (ballX > size - 2) ballX = size - 2;

        if (ballY < 1) ballY = 1;
        if (ballY > size - 2) ballY = size - 2;

    }

    public void mousePressed() {
        for (int i = ballSettings.size() - 1; i >= 0; i--) {
            if (mouseY > originalHeight + (i * width / 20f) && mouseY < originalHeight + ((i + 1) * width / 20f) && mouseX > width / 10 * 9) {
                ballSettings.remove(i);
                surface.setSize(width, (int) (height - width / 20f));
                return;
            }
        }
    }

    public void mouseWheel(MouseEvent e) {

        if (width / (float) size < 10) {
            size--;
            return;
        }
//        size += e.getCount() > 0 ? 1 : e.getCount() < 0 ? -1 : 0;
        if (e.getCount() > 0) size++;
        else if (e.getCount() < 0) size--;


        if (size < 8) size = 8;
        else if (size > 25) size = 25;


        resize();
    }

    private void resize() {

        ArrayList<Square> rectCopy = (ArrayList<Square>) rect.clone();

        rect.clear();

        sizeX = width / (float) size;
        sizeY = originalHeight / (float) size;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                Square.TYPE type = Square.TYPE.FREE;
                Square.HoleType col = null;
                boolean main = false;

                for (Square s : rectCopy) {
                    if (s.i == i && s.j == j && s.i != 0 && s.i != s.maxI && s.j != 0 && s.j != s.maxJ) {
                        type = s.getType();

                        if (type == Square.TYPE.HOLE) {
                            col = s.gethType();
                            main = s.main_hole;
                        }
                    }
                }


                if (i == 0 || i == size - 1 || j == 0 || j == size - 1) {
                    type = Square.TYPE.WALL;

                }


                if (type == Square.TYPE.HOLE && col != null) {
                    rect.add(new Square(this, sizeX * i, sizeY * j, sizeX, sizeY, i, j, size - 1, size - 1, Square.TYPE.HOLE, main, col));
                    //System.out.println("hole configured col: "+col);
                } else {
                    rect.add(new Square(this, sizeX * i, sizeY * j, sizeX, sizeY, type, i, j, size - 1, size - 1));
                }


            }
        }
    }

    private void saveFile() {

        int num = Objects.requireNonNull(new File("levels").listFiles()).length;

        System.out.println("num of files in dir: " + num);

        StringBuilder str = new StringBuilder(size + "," + size + "\n");

        for (int i = 0; i < size; i++) {

            int count = 1;
            Square current = rect.get(i * size);
            for (int j = 1; j < size; j++) {

                int index = j * size + i;

                if (current.getType() == rect.get(index).getType() && current.gethType() == rect.get(index).gethType() && current.main_hole == rect.get(index).main_hole) {
                    count++;
                } else {
                    String s = count + "=" + current.getType();

                    if (current.getType() == Square.TYPE.HOLE) {
                        System.out.println("hole making ---- main: " + current.main_hole + " --------- " + current.gethType());
                        if (current.main_hole) {
                            s += "=" + current.gethType();
                        }
                    }

                    s += ",";

                    str.append(s);
                    current = rect.get(index);
                    count = 1;
                }

            }

            String s = count + "=" + current.getType() + ",";
            str.append(s);


            str.append("\n");
        }


        for (String[] s : ballSettings) {
            String string = "";

            string += s[0] + ",";
            if (s[0].equals("static")) {
                string += "speed=" + s[1] + ",";
                string += "posX=" + s[2] + ",";
                string += "posY=" + s[3] + ",";
                string += "velX=" + s[4] + ",";
                string += "velY=" + s[5] + ",";
                string += "color=" + s[6] + ",";
            } else {
                string += "speed=" + s[1] + ",";
                string += "color=" + s[2] + ",";
                string += "frame=" + s[3] + ",";
            }


            str.append(string).append("\n");
        }

        System.out.println(str);

        PApplet.runSketch(new String[]{"--display=1",
                "--location=0,0",
                "App" }, new App());


//        File file = new File("levels\\level" + num);
//        try {
//            Formatter formatter = new Formatter(file);
//
//            formatter.format(str.toString());
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

    }

    public static void fromGame(String path) {
        PApplet.runSketch(new String[]{
                "projector"
        }, new Maker());
    }

    public void addBall() {
        if (dynamicMode) {
            String[] settings = new String[4];

            settings[0] = "dynamic";
            settings[1] = "4";
            if (colors[colorCount].equals("NEUTRAL")) {
                settings[2] = "blue";
            } else {
                settings[2] = colors[colorCount].toLowerCase();
            }

            settings[3] = String.valueOf(ballFrame);

            ballSettings.add(settings);
            surface.setSize(width, height + width / 20);

            System.out.println(Arrays.toString(settings));
        } else {
            String[] settings = new String[7];

            settings[0] = "static";
            settings[1] = "4";
            settings[2] = String.valueOf(ballX);
            settings[3] = String.valueOf(ballY);
            settings[4] = String.valueOf(xx);
            settings[5] = String.valueOf(yy);
            if (colors[colorCount].equals("NEUTRAL")) {
                settings[6] = "blue";
            } else {
                settings[6] = colors[colorCount].toLowerCase();
            }

            ballSettings.add(settings);
            surface.setSize(width, height + width / 20);

            System.out.println(Arrays.toString(settings));
        }

        ballX = 1;
        ballY = 1;
        dynamicMode = false;
        ballFrame = 10;
    }

    public void keyPressed() {
//        modeCount++;
//        modeCount %= modes.length;
        if (keyCode == UP) {
            if (!dynamicMode)
                modeCount++;
            else
                ballFrame += 10;
        } else if (keyCode == DOWN) {
            if (!dynamicMode)
                modeCount--;
            else
                ballFrame -= 10;
        } else if (keyCode == LEFT) {
            colorCount--;
        } else if (keyCode == RIGHT) {
            colorCount++;
        } else if (keyCode == ENTER) {
            if (addBall) {
                addBall = false;
                addBall();
            } else {
                saveFile();
                System.exit(0);
            }
        } else if (keyCode == 32) {
            addBall = !addBall;
        } else if (keyCode == 'R') {
            rotation--;
            if (rotation < 0) rotation = 3;
        } else if (keyCode == 'M') {
            dynamicMode = !dynamicMode;
        }

        if (modeCount < 0) modeCount = 6;
        if (modeCount > 6) modeCount = 0;

        if (colorCount < 0) colorCount = 5;
        if (colorCount > 5) colorCount = 0;
    }

    public static void main(String[] args) {
        PApplet.main("level_maker.Maker", args);
    }
}
