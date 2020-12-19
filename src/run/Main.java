package run;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //System.out.println(getClass().getResource("sample.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        // primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));

        /**
         * Make the Window has not edge;
         * */
        primaryStage.initStyle(StageStyle.UNDECORATED);


        /**
         * Make the Window can be dragged;
         * */
        root.setOnMousePressed(event -> {

            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        /**
         * Show the MainWindow;
         * */
        //primaryStage.setFullScreen(true);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
