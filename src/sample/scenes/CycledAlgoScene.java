package sample.scenes;

import algos.BranchedAlgo;
import algos.CycledAlgo;
import exceptions.InvalidInputException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by zhenia on 27.02.17.
 */
public class CycledAlgoScene {
    public static Scene setScene(){
        Text banner = new Text("Cycled Banner"); // TODO place image

        Text[] varBanners = new Text[4];
        varBanners[0] = new Text("n:");
        varBanners[1] = new Text("f:");
        varBanners[2] = new Text("g:");
        varBanners[3] = new Text("vector c:");

        TextField[] vars = new TextField[4];

        vars[0] = new TextField();
        TextField tmpTF = vars[0];
        tmpTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("\\d*")) tmpTF.setText(newValue.replaceAll("[^\\d]", ""));
        });

        vars[1] = new TextField();
        setListener(vars[1]);

        vars[2] = new TextField();
        setListener(vars[2]);

        vars[3] = new TextField();
        TextField tmpTF1 = vars[3];
        tmpTF1.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("\\d*|\\.|\\s|-")) tmpTF1.setText(newValue.replaceAll("[^\\d^.^\\s^-]", ""));
        });

        Text errorList = new Text();

        Button go = setButtonGo(vars,errorList);

        GridPane root = setLayout(banner, varBanners, vars, errorList, go);

        return new Scene(root, 500, 350);
    }

    private static GridPane setLayout(Text banner, Text[] varBanners, TextField[] vars, Text errorList, Button go){
        GridPane root = new GridPane();

        ColumnConstraints[] columns = new ColumnConstraints[5];

        for (int i = 0; i < columns.length; i++) {
            columns[i] = new ColumnConstraints();
            columns[i].setPercentWidth(15);
        }

        columns[2].setPercentWidth(40);

        RowConstraints[] rows = new RowConstraints[9];

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new RowConstraints();
            rows[i].setPercentHeight(100/rows.length);
        }

        root.getRowConstraints().addAll(rows);
        root.getColumnConstraints().addAll(columns);

        root.add(banner, 2, 1);

        for (int i = 0; i < vars.length; i++) {
            root.add(varBanners[i], 1, i+2);
            root.add(vars[i], 2, i+2);
        }

        root.add(errorList, 2, 6);
        root.add(go, 2, 7);

        go.setPrefSize(200, 50);

        root.setAlignment(Pos.BOTTOM_CENTER);

        return root;
    }

    private static void setListener(TextField var){
        var.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("\\d*|\\.|-")) var.setText(newValue.replaceAll("[^\\d^.^-]", ""));
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

    private static Button setButtonGo(TextField[] vars, Text errorList){
        Button go = new Button("GO!");

        go.setOnAction(event -> {
            try {
                Double[] variable = new Double[2];
                Integer n;

                for (int i = 0; i < vars.length; i++) {
                    if (vars[i].getText() == null || vars[i].getText().length() == 0)
                        throw new InvalidInputException("Empty field(s)!");
                }

                variable[0] = Double.parseDouble(vars[1].getText());
                variable[1] = Double.parseDouble(vars[2].getText());

                n = Integer.parseInt(vars[0].getText());

                if(n < 0) throw new InvalidInputException("Incorrect n");

                Double[] c = new Double[n];

                String[] doubleNumStrArr = vars[3].getText().split(" ");

                if(doubleNumStrArr.length < n) throw new InvalidInputException("Incorrect vector!");

                for (int i = 0; i < n; i++) {
                    c[i] = Double.parseDouble(doubleNumStrArr[i]);
                }

                double y = CycledAlgo.calculate(n, c, variable[0], variable[1]);

                errorList.setText("");

                for (int i = 0; i < vars.length; i++) {
                    vars[i].setStyle("-fx-background-color: transparent");
                }

                Stage modal = new Stage();

                modal.setScene(setModalScene(y, CycledAlgo.operations));

                modal.showAndWait();
            } catch (InvalidInputException | NumberFormatException e){
                errorList.setText(e.getMessage());

                errorList.setStyle("-fx-font-color: red"); // doesn't work! TODO

                for (int i = 0; i < vars.length; i++) {
                    vars[i].setStyle("-fx-background-color: red");
                }
            }
        });

        return go;
    }
}
