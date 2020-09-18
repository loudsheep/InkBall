package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//
//        stage.setTitle("Title");
//
//        FlowPane root = new FlowPane();
//
//        Scene scene = new Scene(root, 300,200);
//
//        stage.setScene(scene);
//
//        Label label = new Label("first label");
//
//        root.getChildren().add(label);
//
//        stage.show();


        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));


//        Label label = new Label("My first label");
//
//        root.getChildrenUnmodifiable().add(label);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
