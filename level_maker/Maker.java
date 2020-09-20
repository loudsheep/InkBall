package level_maker;

import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

import java.io.File;
import java.util.*;


public class Maker extends PApplet {

    //public final PImage img = loadImage("img/hole_blue.bmp");


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

            main_hole = true;

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
            } else if (type == TYPE.HOLE){


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

                    if(type == TYPE.HOLE) {
                        System.out.println("holelelele");
                        if(main_hole) {
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

        //TODO Do this holes making !!!

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
                    if(s == this) continue;
//                    if(s.i == i) {
//                        if(s.i == i+1 || s.i == i+2) {
//                            s.setType(TYPE.HOLE);
//                            s.main_hole = false;
//                        }
//                    }
                    if (s.i < i + 3 && s.j < j + 3) {
                        if (s.i >= i && s.j >= j) {
                            if(s.getType() == TYPE.HOLE) {
                                if(s.main_hole) {
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

    private ArrayList<Square> rect;

//    PImage blue = null;
//    PImage green = null;
//    PImage neutral = null;
//    PImage orange = null;
//    PImage red = null;
//    PImage yellow = null;

    public HashMap<String, PImage> img;

    private String[] modes = {"WALL", "ONE_WAY_UP", "ONE_WAY_DOWN", "ONE_WAY_RIGHT", "ONE_WAY_LEFT", "SPAWN", "HOLE"};
    private int modeCount = 0;

//    public enum HoleType {
//        BLUE,
//        ORANGE,
//        RED,
//        GREEN,
//        YELLOW,
//        NEUTRAL
//    }

    private String[] colors = {"BLUE", "ORANGE", "RED", "GREEN", "YELLOW", "NEUTRAL"};
    private int colorCount = 0;

    public void settings() {
        size(800, 800);
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

        fill(0);

        if (colorCount < 0) {
            colorCount = colors.length - 1;
        }

        if (modeCount < 0) {
            modeCount = modes.length - 1;
        }
        text("Mode: " + modes[modeCount], 10, 10);
        text("Color: " + colors[colorCount], 10, 20);
        text("Size: " + size+"x"+size, 10, 30);

        if (mousePressed) {
            if (mouseButton == LEFT) {
                for (Square s : rect) {
                    s.pressed(modes[modeCount], rect, colors[colorCount]);
                }
            } else if (mouseButton == RIGHT) {
                for (Square s : rect) {
                    s.pressed("FREE", rect, colors[colorCount]);
                }

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
        sizeY = height / (float) size;

        for (int i = 0; i < size; i++) {
            outer:
            for (int j = 0; j < size; j++) {

                Square.TYPE type = Square.TYPE.FREE;
                Square.HoleType col = null;
                boolean main = false;

                for (Square s : rectCopy) {
                    if (s.i == i && s.j == j && s.i != 0 && s.i != s.maxI && s.j != 0 && s.j != s.maxJ) {
                        type = s.getType();

//                        s.setPosX(sizeX * i);
//                        s.setPosY(sizeX * j);
//                        s.setW(sizeX);
//                        s.setH(sizeY);
//
//
//                        if (i == 0 || i == size - 1 || j == 0 || j == size - 1) {
//                            s.setType(Square.TYPE.WALL);
//
//                        }
//
//                        rect.add(s);
//                        continue outer;

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

//        for(int i=0; i<size; i++) {
//            for(int j=0; j<)
//        }

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
                    String s = count + "-" + current.getType();

                    if (current.getType() == Square.TYPE.HOLE) {
                        System.out.println("hole making ---- main: " + current.main_hole + " --------- " + current.gethType());
                        if (current.main_hole) {
                            s += "-" + current.gethType();
                        }
                    }

                    s += ",";

                    str.append(s);
                    current = rect.get(index);
                    count = 1;
                }

            }

            String s = count + "-" + current.getType() + ",";
            str.append(s);


            str.append("\n");
        }

        System.out.println(str);


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

    public void keyPressed() {
//        modeCount++;
//        modeCount %= modes.length;
        if (keyCode == UP) {
            modeCount++;
            modeCount %= modes.length;
        } else if (keyCode == DOWN) {
            modeCount--;
            modeCount %= modes.length;
        } else if (keyCode == LEFT) {
            colorCount--;
            colorCount %= colors.length;
        } else if (keyCode == RIGHT) {
            colorCount++;
            colorCount %= colors.length;
        } else if (keyCode == ENTER) {
            saveFile();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        PApplet.main("level_maker.Maker", args);
    }
}
