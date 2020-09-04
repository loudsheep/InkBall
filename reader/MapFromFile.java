package reader;

import game.Grid;
import game.Square;
import processing.core.PVector;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MapFromFile {

    private File file;
    private int w, h;

    public MapFromFile(String path, int w, int h) {
        file = new File(path);

        this.w = w;
        this.h = h;
    }

    public Grid generate() {

        int line = 0;

        Scanner sc;

        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        ArrayList<String> lines = new ArrayList<>();

        while (sc.hasNext()) {
            lines.add(sc.next());
        }

        int dimX;
        int dimY;

        dimX = Integer.parseInt(lines.get(0).split(",")[0]);
        dimY = Integer.parseInt(lines.get(0).split(",")[1]);
        line++;

        Grid gameGrid = new Grid(dimX, dimY, w, h);

        ArrayList<Square> grid = new ArrayList<>();

        float sizeX = w / (float) dimX;
        float sizeY = h / (float) dimY;

        int ballSize = Integer.parseInt(lines.get(1).split("-")[1]);
        line++;


        //System.out.println(ballSize);

        //if (lines.size() > dimY) throw new RuntimeException("Invalid line declaration");

        int posY = 0;

        Square.HoleType color;

        for (int i = 2; i < dimX + 2; i++) {

            String[] lineSetting = lines.get(i).split(",");

            int posX = 0;

            for (int j = 0; j < lineSetting.length; j++) {
                int count = 0;


                String[] setting = lineSetting[j].split("-");


                int num = Integer.parseInt(setting[0]);
                Square.TYPE type = Square.TYPE.valueOf(setting[1]);
                //System.out.println(num + "x " + type.toString());
                count += num;

                if (setting.length != 2) {
                    color = Square.HoleType.valueOf(setting[2]);
                    grid.add(new Square(sizeX * posX, sizeY * posY, sizeX * 3, sizeY * 3, type, color, true));

                    //System.out.println("Main hole added");
                    posX++;
                    continue;
                }

                if (count > dimX) throw new RuntimeException("Invalid line declaration");

                for (int k = 0; k < num; k++) {
                    if (type == Square.TYPE.HOLE) {
                        grid.add(new Square(sizeX * posX, sizeY * posY, sizeX, sizeY, type, null, false));
                    } else {
                        grid.add(new Square(sizeX * posX, sizeY * posY, sizeX, sizeY, type));
                    }

                    posX++;
                }


            }

            posY++;
            line++;

        }


        gameGrid.setGrid(grid);


        if (line < lines.size()) { // there are some ball-config in file

            HashMap<String, int[]> colors = new HashMap<>();
            colors.put("red", new int[]{255, 0, 0});
            colors.put("green", new int[]{0, 255, 0});
            colors.put("blue", new int[]{0, 0, 255});
            colors.put("orange", new int[]{255, 111, 0});
            colors.put("yellow", new int[]{255, 255, 0});

            for (; line < lines.size(); line++) {
                //System.out.println(line + " -<<<<<<<<<<");

                String[] lineSetting = lines.get(line).split(",");

                if (lineSetting[0].equals("static")) { // static ball in file

                    int speed, px, py, velX, velY;

                    speed = Integer.parseInt(lineSetting[1].split("-")[1]);
                    px = Integer.parseInt(lineSetting[2].split("-")[1]);
                    py = Integer.parseInt(lineSetting[3].split("-")[1]);
                    velX = Integer.parseInt(lineSetting[4].split("-")[1]);
                    velY = Integer.parseInt(lineSetting[5].split("-")[1]);
                    int[] c = colors.get(lineSetting[6].split("-")[1]);

                    gameGrid.addBall(px, py, new PVector(velX, velY), speed, ballSize, c[0], c[1], c[2]);

                } else if (lineSetting[0].equals("dynamic")) { // dynamic ball in file
                    int speed;

                    speed = Integer.parseInt(lineSetting[1].split("-")[1]);

                    int[] c = colors.get(lineSetting[2].split("-")[1]);

                    if (lineSetting.length > 3) {
                        int frame = Integer.parseInt(lineSetting[3].split("-")[1]);
                        gameGrid.addBall(speed, ballSize, c[0], c[1], c[2], frame);
                    } else
                        gameGrid.addBall(speed, ballSize, c[0], c[1], c[2]);
                }
            }

        } else { // there are no ball-config in file 

            gameGrid.addBall(4, ballSize, 0, 0, 255);
        }


        return gameGrid;
    }

}
