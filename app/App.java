package app;

import game.Ball;
import game.Grid;
import processing.core.PApplet;
import reader.MapFromFile;

import java.io.File;

public class App extends PApplet {

////    import java.util.Map;
////import java.nio.file.Paths;
////
////import javafx.application.Platform;
////import javafx.fxml.FXMLLoader;
////import javafx.scene.Parent;
////import javafx.scene.Scene;
////import javafx.scene.SceneAntialiasing;
////import javafx.scene.canvas.Canvas;
////import javafx.scene.layout.AnchorPane;
////import javafx.stage.Stage;
////
////import processing.javafx.PSurfaceFX;
//
////    protected PSurface initSurface() {
////        surface = (PSurfaceFX) super.initSurface();
////        final Canvas canvas = (Canvas) surface.getNative();
////        final Scene oldScene = canvas.getScene();
////        final Stage stage = (Stage) oldScene.getWindow();
////
////        try {
////            FXMLLoader loader = new FXMLLoader(Paths.get("C:\\path--to--fxml\\stage.fxml").toUri().toURL()); // abs path to fxml file
////            final Parent sceneFromFXML = loader.load();
////            final Map<String, Object> namespace = loader.getNamespace();
////
////            final Scene newScene = new Scene(sceneFromFXML, stage.getWidth(), stage.getHeight(), false,
////                    SceneAntialiasing.BALANCED);
////            final AnchorPane pane = (AnchorPane) namespace.get("anchorPane"); // get element by fx:id
////
////            pane.getChildren().add(canvas); // processing to stackPane
////            canvas.widthProperty().bind(pane.widthProperty()); // bind canvas dimensions to pane
////            canvas.heightProperty().bind(pane.heightProperty()); // bind canvas dimensions to pane
////
////            Platform.runLater(new Runnable() {
////                                  @Override
////                                  public void run() {
////                                      stage.setScene(newScene);
////                                  }
////                              }
////            );
////        }
////        catch (IOException e) {
////            e.printStackTrace();
////        }
////        return surface;
////    }
//
//    protected PSurface initSurface() {
//
//        surface = super.initSurface();
//        final Canvas canvas = (Canvas) surface.getNative();
//
//        final Scene oldScene = canvas.getScene();
//        final Stage stage = (Stage) oldScene.getWindow();
//
//        try {
//            FXMLLoader loader = new FXMLLoader(Paths.get("FXML.fxml").toUri().toURL());
//            final Parent sceneFromFXML = loader.load();
//
//            final Map<String,Object> nameSpace = loader.getNamespace();
//
//            final Scene newScene = new Scene(sceneFromFXML, stage.getWidth(),stage.getHeight(),false, SceneAntialiasing.BALANCED);
//
//            final AnchorPane pane = (AnchorPane) nameSpace.get("anchorPane");
//
//            pane.getChildren().add(canvas);
//
//            canvas.widthProperty().bind(pane.widthProperty());
//            canvas.heightProperty().bind(pane.heightProperty());
//
//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//                    stage.setScene(newScene);
//                }
//            });
//
//
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        return surface;
//    }


    Button start;
    Button exit;
    Button reset;
    Button next, prev;

    Grid game;

    int w = 600;

    int panelHeight;

    boolean gameStarted = false;

    int level = 0;

    public void settings() {
        panelHeight = w / 20;
        size(w, w + panelHeight);
    }

    public void setup() {
        start = new Button(this, "Start game", height / 30, width / 4, height / 5, width / 2, height / 10);
        start.setStrokeColor(new Color(0, 0, 0));
        start.setFillColor(new Color(255, 255, 255));
        start.setTextColor(new Color(0, 0, 0));

        exit = new Button(this, "Quit", height / 30, width / 4, height / 5 * 3, width / 2, height / 10);
        exit.setStrokeColor(new Color(0, 0, 0));
        exit.setFillColor(new Color(255, 255, 255));
        exit.setTextColor(new Color(0, 0, 0));

//        exit = new Button(this, "Quit", height / 60, width - (width / game.getSquaresX() * 2), -panelHeight, width / game.getSquaresX() * 2, panelHeight);
//        exit.setAction(this::end);


        start.setAction(this::startGame);
        exit.setAction(this::end);

        File f = new File("levels/level" + level + ".map");

        if (f.exists() && !f.isDirectory()) {

            MapFromFile file = new MapFromFile("levels/level" + level + ".map", width, height - panelHeight);

            game = file.generate();
            game.setAll(this);

            reset = new Button(this, "Restart", height / 60, width - (width/8 * 2), -panelHeight, width/8, panelHeight);
            reset.setStrokeColor(new Color(0, 0, 0));
            reset.setFillColor(new Color(255, 255, 255));
            reset.setTextColor(new Color(0, 0, 0));
            reset.setAction(this::reset);
            reset.setActive(false);


            next = new Button(this, "Next level", height / 60, width - (width/8 * 3), -panelHeight, width/8, panelHeight);
            next.setStrokeColor(new Color(0, 0, 0));
            next.setFillColor(new Color(255, 255, 255));
            next.setTextColor(new Color(0, 0, 0));
            next.setAction(this::next);
            next.setActive(false);

            prev = new Button(this, "Previous level", height / 60, width - (width/8 * 4), -panelHeight, width/8, panelHeight);
            prev.setStrokeColor(new Color(0, 0, 0));
            prev.setFillColor(new Color(255, 255, 255));
            prev.setTextColor(new Color(0, 0, 0));
            prev.setAction(this::prev);
            prev.setActive(false);
        }


    }

    public void draw() {
        background(220);
        translate(0, panelHeight);

        if (gameStarted) {
            noFill();
            stroke(0);
            rect(0, -panelHeight, width / 6f, panelHeight);

//            fill(0);
//            textSize(panelHeight/2f);
//            //textAlign(CENTER);
//            text(game.waiting.size() + " balls left", 10, -panelHeight/3f);

            if (game.waiting.size() != 0) {
                int i = 1;
//                Set<Entry<Ball, Integer>> entries = game.waiting.entrySet();
//
//                TreeMap<Ball, Integer> sorted = new TreeMap<>(game.waiting);
//                Set<Entry<Ball, Integer>> mappings = sorted.entrySet();
//
//                List<Entry<Ball, Integer>> listOfEntries = new ArrayList<>(entries);
//
//                Collections.sort(listOfEntries, new Comparator<Entry<Ball, Integer>>() {
//                    @Override
//                    public int compare(Entry<Ball, Integer> o1, Entry<Ball, Integer> o2) {
//                        return o1.getValue().compareTo(o2.getValue());
//                    }
//                });
//
//                LinkedHashMap<Ball, Integer> sortedByValue = new LinkedHashMap<>(listOfEntries.size());
//
//                for(Entry<Ball, Integer> entry: listOfEntries) {
//                    sortedByValue.put(entry.getKey(),entry.getValue());
//                }


                for (Ball b : game.waiting.keySet()) {


                    int y = -panelHeight / 2;
                    int r = panelHeight / 2 - 1;

                    int x = (r + 2) * i;

                    noStroke();
                    fill(b.r, b.g, b.b);

                    ellipse(x, y, r, r);


                    i++;

                }
            }

            game.update(mouseX, mouseY - panelHeight, pmouseX, pmouseY - panelHeight);
            game.show();

            if (game.balls.size() == 0 && game.waiting.size() == 0) {
                next();
            }

//            if (game.waiting.size() != 0) {
//                for (int i = 0; i < game.waiting.size(); i++){
//                    game.waiting.
//                }
//            }

        }

        exit.show();
        reset.show();
        next.show();
        start.show();
        prev.show();


        text(frameRate, 10, 10);

    }

    public void mousePressed() {
        if (gameStarted) game.startDraw(mouseX, mouseY - panelHeight, pmouseX, pmouseY - panelHeight);

        start.clicked(mouseX, mouseY - panelHeight);
        reset.clicked(mouseX, mouseY - panelHeight);
        exit.clicked(mouseX, mouseY - panelHeight);
        next.clicked(mouseX, mouseY - panelHeight);
        prev.clicked(mouseX, mouseY - panelHeight);
    }

    public void mouseReleased() {
        if (gameStarted) game.stopDraw();

        start.released(mouseX, mouseY - panelHeight);
        reset.released(mouseX, mouseY - panelHeight);
        exit.released(mouseX, mouseY - panelHeight);
        next.released(mouseX, mouseY - panelHeight);
        prev.released(mouseX, mouseY - panelHeight);


    }

    public void startGame() {
        gameStarted = true;

        start.setActive(false);
        exit = new Button(this, "Quit", height / 60, width - width/8, -panelHeight, width/8, panelHeight);
        exit.setAction(this::end);

        reset.setActive(true);
        next.setActive(true);
        prev.setActive(true);

    }

    public void end() {
        System.exit(0);
    }

    public void reset() {
        File f = new File("levels/level" + level + ".map");

        if (f.exists() && !f.isDirectory()) {

            MapFromFile file = new MapFromFile("levels/level" + level + ".map", width, height - panelHeight);

            game = file.generate();
            game.setAll(this);
        } else if (level <= 0) {
            while (level != 1) level++;
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


    public static void main(String[] args) {
        PApplet.main("app.App", args);
    }

}
