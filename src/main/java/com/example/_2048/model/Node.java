package com.example._2048.model;

import com.example._2048.util.Colors;
import com.example._2048.util.Sizing;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Node extends StackPane {

    private final Table table; // entire matrix
    private final int line;
    private final int col;
    private Text text;  // represents the text inside each node
    private Rectangle rectangle; // represents the node background

    private int value; // represents the value inside each node (multiples of 2)

    public Node(int line, int col, Table table) {
        this.line = line;
        this.col = col;
        this.table = table;
        this.value = 0;

        // initialize node GUI
        text = new Text();
        text.setText("");
        text.setStyle("-fx-font-size: 20;");
        rectangle = initRectangle();
        this.getChildren().addAll(rectangle, text);
    }

    private Rectangle initRectangle() {
        Rectangle rect = new Rectangle();
        rect.setStyle("-fx-stroke: black; -fx-stroke-width: 0.5;");
        rect.setFill(Colors.getColor(value));
        rect.setWidth(Sizing.getWindowWidth() / 4);
        rect.setHeight(Sizing.getWindowHeight() / 4);
        return rect;
    }

    public void incrementValue() {
        value = value == 0 ? 2 : value * 2;
        text.setText(Integer.toString(value));
        rectangle.setFill(Colors.getColor(value));
    }

    public void paint() {
        Paint color = Colors.getColor(value);
        text.setText(Integer.toString(value));
        rectangle.setFill(color);
    }

    public void replace(Node newNode) {
        setValue(newNode.getValue());
        text.setText(newNode.text.getText());
        rectangle.setFill(Colors.getColor(getValue()));
    }


    // check if node is filled
    public boolean isEmpty() {
        return value == 0;
    }

    public boolean isEqual(Node node) {
        return value == node.getValue();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }



    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

    public Text getText() {
        return text;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public String toString() {
        return "Node{" + "line=" + line + ", col=" + col + "; value: " + value + '}';
    }

}
