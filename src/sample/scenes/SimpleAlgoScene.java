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
import sample.Main;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by zhenia on 27.02.17.
 */
public class SimpleAlgoScene {
    public static Scene setScene(){
        Text banner = new Text("Simple Banner");

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

        Button go = setButtonGo(vars, errorList);

        Button getFromFile = setButtonGetFromFile(vars, errorList);

        GridPane root = setLayout(banner, varBanners, vars, errorList, go);

        root.add(getFromFile, 2, 6);

        Main.wndNum = 2;

        return new Scene(root, 500, 350);
    }

    private static Button setButtonGetFromFile(TextField[] vars, Text errorList) {
        Button get = new Button("Get from file");

        get.setPrefSize(200, 50);

        get.setOnAction(event -> {
            try(Scanner sc = new Scanner(new File("/home/zhenia/IdeaProjects/amo_1.1/src/simple.txt"))) {
                errorList.setText("");

                for (int i = 0; i < vars.length; i++) {
                    vars[i].setStyle("-fx-background-color: transparent");
                    vars[i].setText("");
                }

                for (int i = 0; i < vars.length; i++) {
                    vars[i].setText(String.valueOf(sc.nextDouble()));
                }
            } catch (IOException | NoSuchElementException e) {
                errorList.setText(e.getMessage());

                for (int i = 0; i < vars.length; i++) {
                    vars[i].setStyle("-fx-background-color: red");
                }
            }
        });

        return get;
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

        for (int i = 0; i < 3; i++) {
            root.add(varBanners[i], 1, i+2);
            root.add(vars[i], 2, i+2);
        }

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

    private static Button setButtonGo(TextField[] vars, Text errorList){
        Button go = new Button("GO!");

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
            } catch (InvalidInputException | NumberFormatException e){
                errorList.setText(e.getMessage());

                for (int i = 0; i < vars.length; i++) {
                    vars[i].setStyle("-fx-background-color: red");
                }
            }
        });

        return go;
    }
}
