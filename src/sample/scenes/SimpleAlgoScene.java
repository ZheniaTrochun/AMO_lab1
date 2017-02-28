package sample.scenes;

import algos.SimpleAlgo;
import exceptions.InvalidInputException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by zhenia on 27.02.17.
 */
public class SimpleAlgoScene {
    public static Scene setScene(){
        Text banner = new Text("Simple Banner"); // TODO place image

        Text[] varBanners = new Text[3];
        varBanners[0] = new Text("a:");
        varBanners[1] = new Text("b:");
        varBanners[2] = new Text("c:");

        TextField[] vars = new TextField[3];

        for (int i = 0; i < vars.length; i++) {
            vars[i] = new TextField();
            setListener(vars[i]);
        }

        Text errorList = new Text();

        Button go = new Button("GO!");

        GridPane root = setLayout(banner, varBanners, vars, errorList, go);

        go.setOnAction(event -> {
            try {
                Double[] variable = new Double[3];
                for (int i = 0; i < vars.length; i++) {
                    if(vars[i].getText() == null || vars[i].getText().length() == 0)
                        throw new InvalidInputException("Empty field(s)!");
                    variable[i] = Double.parseDouble(vars[i].getText());
                }

                double y = SimpleAlgo.calculate(variable[0], variable[1], variable[2]);

                errorList.setText("");

                for (int i = 0; i < vars.length; i++) {
                    vars[i].setStyle("-fx-background-color: transparent");
                }

                Stage modal = new Stage();

                modal.setScene(setModalScene(y, 10));

                modal.showAndWait();
            } catch (InvalidInputException e){
                errorList.setText(e.getMessage());

                errorList.setStyle("-fx-font-color: red"); // doesn't work! TODO

                for (int i = 0; i < vars.length; i++) {
                    vars[i].setStyle("-fx-background-color: red");
                }
            }
        });

        return new Scene(root, 500, 300);
    }

    private static GridPane setLayout(Text banner, Text[] varBanners, TextField[] vars, Text errorList, Button go){
        GridPane root = new GridPane();

        ColumnConstraints[] columns = new ColumnConstraints[5];

        for (int i = 0; i < columns.length; i++) {
            columns[i] = new ColumnConstraints();
            columns[i].setPercentWidth(15);
        }

        columns[2].setPercentWidth(40);

        RowConstraints[] rows = new RowConstraints[8];

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new RowConstraints();
            rows[i].setPercentHeight(100/rows.length);
        }

        root.getRowConstraints().addAll(rows);
        root.getColumnConstraints().addAll(columns);

        root.add(banner, 2, 1);

        for (int i = 0; i < 3; i++) {
            root.add(varBanners[i], 1, i+2);
            root.add(vars[i], 2, i+2);
        }

        root.add(errorList, 2, 5);
        root.add(go, 2, 6);

        go.setPrefSize(200, 50);

        root.setAlignment(Pos.BOTTOM_CENTER);

        return root;
    }

    private static void setListener(TextField var){
        var.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("\\d*")) var.setText(newValue.replaceAll("[^\\d]", ""));
        });
    }

    private static Scene setModalScene(double y, int operations){
        Text resBanner = new Text("Result: " + y);
        Text operBanner = new Text("Operations: " + operations);

        GridPane root = new GridPane();

        root.add(resBanner, 0, 0);
        root.add(operBanner, 0, 1);

        root.setAlignment(Pos.CENTER);

        return new Scene(root, 300, 200);
    }
}
