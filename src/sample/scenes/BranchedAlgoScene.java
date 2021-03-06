package sample.scenes;

import algos.BranchedAlgo;
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
import sample.Main;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by zhenia on 27.02.17.
 */
public class BranchedAlgoScene {
    public static Scene setScene(){
        Text banner = new Text("Branched Banner"); // TODO place image

        Text[] varBanners = new Text[3];
        varBanners[0] = new Text("d:");
        varBanners[1] = new Text("m:");
        varBanners[2] = new Text("i:");

        TextField[] vars = new TextField[2];

        for (int i = 0; i < vars.length; i++) {
            vars[i] = new TextField();
            setListener(vars[i]);
        }

        TextField varI = new TextField();

        varI.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("\\d*|-")) varI.setText(newValue.replaceAll("[^\\d^-]", ""));
        });

        Text errorList = new Text();

        Button go = setButtonGo(vars, varI, errorList);

        Button getFromFile = setButtonGetFromFile(vars, varI, errorList);

        GridPane root = setLayout(banner, varBanners, vars, varI, errorList, go);

        root.add(getFromFile, 2, 6);

        Main.wndNum = 3;

        return new Scene(root, 500, 350);
    }

    private static GridPane setLayout(Text banner, Text[] varBanners, TextField[] vars, TextField varI, Text errorList, Button go){
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

        root.add(varBanners[2], 1, 4);
        root.add(varI, 2, 4);

        root.add(errorList, 2, 5);
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

    private static Button setButtonGo(TextField[] vars, TextField varI, Text errorList){
        Button go = new Button("GO!");

        go.setOnAction(event -> {
            try {
                Double[] variable = new Double[vars.length];
                for (int i = 0; i < vars.length; i++) {
                    if(vars[i].getText() == null || vars[i].getText().length() == 0)
                        throw new InvalidInputException("Empty field(s)!");
                    variable[i] = Double.parseDouble(vars[i].getText());
                }

                int variableI;

                variableI = Integer.parseInt(varI.getText());

                double y = BranchedAlgo.calculate(variable[0], variable[1], variableI);

                errorList.setText("");

                for (int i = 0; i < vars.length; i++) {
                    vars[i].setStyle("-fx-background-color: transparent");
                }

                varI.setStyle("-fx-background-color: transparent");

                Stage modal = new Stage();

                modal.setScene(setModalScene(y, BranchedAlgo.operations));

                modal.showAndWait();
            } catch (InvalidInputException | NumberFormatException e){
                errorList.setText(e.getMessage());

                errorList.setStyle("-fx-font-color: red"); // doesn't work! TODO

                for (int i = 0; i < vars.length; i++) {
                    vars[i].setStyle("-fx-background-color: red");
                }

                varI.setStyle("-fx-background-color: red");
            }
        });

        return go;
    }

    private static Button setButtonGetFromFile(TextField[] vars, TextField varI, Text errorList) {
        Button get = new Button("Get from file");

        get.setPrefSize(200, 50);

        get.setOnAction(event -> {
            try(Scanner sc = new Scanner(new File("/home/zhenia/IdeaProjects/amo_1.1/src/branched.txt"))) {
                errorList.setText("");

                for (int i = 0; i < vars.length; i++) {
                    vars[i].setStyle("-fx-background-color: transparent");
                    vars[i].setText("");
                }

                varI.setStyle("-fx-background-color: transparent");
                varI.setText("");

                for (int i = 0; i < vars.length; i++) {
                    vars[i].setText(String.valueOf(sc.nextDouble()));
                }

                varI.setText(String.valueOf(sc.nextInt()));

            } catch (IOException | NoSuchElementException e) {
                errorList.setText(e.getMessage());

                for (int i = 0; i < vars.length; i++) {
                    vars[i].setStyle("-fx-background-color: red");
                }
                varI.setStyle("-fx-background-color: red");
            }
        });

        return get;
    }
}
