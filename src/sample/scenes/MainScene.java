package sample.scenes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

/**
 * Created by zhenia on 27.02.17.
 */

public class MainScene {
    public static Scene setStartScene(){
        Text simpleAlgoBanner = new Text("y = ((a/b + b/a) * (a/c + c/a))/(a + b + c)");
        Text branchedAlgoBanner = new Text("Branched");
        Text cycledAlgoBanner = new Text("Cycled");

        Button simple = new Button("Simple");
        Button branched = new Button("Branched");
        Button cycled = new Button("Cycled");

        branched.setPrefSize(200, 50);
        cycled.setPrefSize(200, 50);
        simple.setPrefSize(200, 50);

        GridPane root = setLayout(simpleAlgoBanner, branchedAlgoBanner, cycledAlgoBanner, simple, branched, cycled);

        setButtons(simple, branched, cycled);

        return new Scene(root, 700, 500);
    }

    public static void setButtons(Button simple, Button branched, Button cycled){
        simple.setOnAction(event -> Main.stage.setScene(SimpleAlgoScene.setScene()));
        branched.setOnAction(event -> Main.stage.setScene(BranchedAlgoScene.setScene()));
        cycled.setOnAction(event -> Main.stage.setScene(CycledAlgoScene.setScene()));
    }

    private static GridPane setLayout(Text simpleAlgoBanner, Text branchedAlgoBanner, Text cycledAlgoBanner,
                                      Button simple, Button branched, Button cycled){
        GridPane root = new GridPane();


        ColumnConstraints[] columnsConstraints = new ColumnConstraints[4];

        for (int i = 0; i < columnsConstraints.length; i++) {
            columnsConstraints[i] = new ColumnConstraints();
            columnsConstraints[i].setPercentWidth(25);
        }

        root.getColumnConstraints().addAll(columnsConstraints);

        RowConstraints[] rowsConstraints = new RowConstraints[5];

        for (int i = 0; i < rowsConstraints.length; i++) {
            rowsConstraints[i] = new RowConstraints();
            rowsConstraints[i].setPercentHeight(20);
        }

        root.getRowConstraints().addAll(rowsConstraints);

        root.add(simpleAlgoBanner, 1, 1);
        root.add(branchedAlgoBanner, 1, 2);
        root.add(cycledAlgoBanner, 1, 3);

        root.add(simple, 2, 1);
        root.add(branched, 2, 2);
        root.add(cycled, 2, 3);

        return root;
    }
}

