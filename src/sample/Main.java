package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.scenes.MainScene;

public class Main extends Application {
    public static Stage stage;
    public static int wndNum = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(MainScene.setStartScene());

        // TODO not working well
        stage.setOnCloseRequest(event -> {
            if(wndNum != 1){
                stage.close();
                stage.setScene(MainScene.setStartScene());
                stage.show();
            }
            else stage.close();
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
