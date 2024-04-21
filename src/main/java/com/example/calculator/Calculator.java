package com.example.calculator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Calculator extends Application {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 420;
    private static final int DISPLAY_WIDTH = 230;
    private static final int DISPLAY_HEIGHT = 50;
    private static final int DISPLAY_X = 50;
    private static final int DISPLAY_Y = 50;
    private static final int BUTTON_SIZE = 50;
    private static final int BUTTON_X = (WIDTH - 4 * BUTTON_SIZE) / 2;
    private static final int BUTTON_Y = DISPLAY_Y + DISPLAY_HEIGHT + 10;
    private static final int BUTTON_SPACING = 10;
    private static final String[] LABELS = {"7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "x", "0", ".", "/", "=", "CE"};
    private static final String[] OPERATIONS = {"+", "-", "x", "/"};

    private String expression = "";
    private double result = 0;

    @Override
    public void start(Stage primaryStage) {

        Pane pane = new Pane();

        Rectangle display = new Rectangle(DISPLAY_X, DISPLAY_Y, DISPLAY_WIDTH, DISPLAY_HEIGHT);
        display.setFill(Color.WHITE);
        display.setStroke(Color.BLACK);
        Text text = new Text(DISPLAY_X + 10, DISPLAY_Y + 30, "");
        text.setFont(Font.font(20));

        Button[] buttons = new Button[17];

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(LABELS[i]);
            buttons[i].setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
            buttons[i].setLayoutX(BUTTON_X + (i % 4) * (BUTTON_SIZE + BUTTON_SPACING));
            buttons[i].setLayoutY(BUTTON_Y + (i / 4) * (BUTTON_SIZE + BUTTON_SPACING));

            buttons[i].setOnAction(e -> handleButtonClick(((Button) e.getSource()).getText(), text));
        }

        pane.getChildren().add(display);
        pane.getChildren().add(text);
        pane.getChildren().addAll(buttons);

        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleButtonClick(String label, Text text) {
        if (label.equals("CE")) {
            expression = "";
        } else if (isOperation(label)) {
            if (!expression.isEmpty() && !isOperation(expression.substring(expression.length() - 1))) {
                expression += label;
            }
        } else if (label.equals("=")) {
            if (!expression.isEmpty() && !isOperation(expression.substring(expression.length() - 1))) {
                result = evaluate(expression);

                if (Double.isFinite(result)) {
                    expression = String.valueOf(result);
                } else {
                    expression = "INVALID VALUES";
                }
            }
        } else {
            expression += label;
        }

        text.setText(expression);
    }


    private boolean isOperation(String s) {
        for (String op : OPERATIONS) {
            if (s.equals(op)) {
                return true;
            }
        }
        return false;
    }

    private double evaluate(String s) {
        s = s.replaceAll("\\s", "");

        for (String op : OPERATIONS) {
            if (s.contains(op)) {
                String[] parts = s.split("[-+*/]");

                if (parts.length != 2) {
                    return Double.NaN;
                }

                double left = Double.parseDouble(parts[0]);
                double right = Double.parseDouble(parts[1]);

                switch (op) {
                    case "+":
                        return left + right;
                    case "-":
                        return left - right;
                    case "x":
                        return left * right;
                    case "/":
                        if (right != 0) {
                            return left / right;
                        } else {
                            return Double.NaN;
                        }
                }
            }
        }
        return Double.parseDouble(s);
    }
    public static void main(String[] args) {
        launch(args);
    }
}