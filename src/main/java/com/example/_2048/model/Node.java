package com.example._2048.model;

import com.example._2048.util.Colors;
import com.example._2048.util.Sizing;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Node extends StackPane {

    private final Table table; // entire matrix
    private int line;
    private int col;
    Text text;  // represents the text inside each node
    Rectangle rectangle; // represents the node background

    private int value; // represents the value inside each node (multiples of 2)

    public Node(int line, int col, Table table) {
        this.line = line;
        this.col = col;
        this.table = table;
        this.value = 0;

        // initialize node GUI
        text = new Text();
        rectangle = new Rectangle();
        rectangle.setStyle("-fx-stroke: black; -fx-stroke-width: 0.5;");
        rectangle.setFill(Color.LIGHTGREY);
        rectangle.setWidth( Sizing.getWindowWidth() / 4);
        rectangle.setHeight( Sizing.getWindowHeight() / 4);
        this.getChildren().addAll(rectangle, text);
    }

    public void incrementValue() {
        value = (value == 0 ? 2 : value * 2);
        text.setText(Integer.toString(value));
    }

    public void paint() {
        incrementValue();
        Paint color = Colors.getColor(value);
        text.setText(Integer.toString(value));
        rectangle.setFill(color);
    }


    // check if node is filled
    public boolean isEmpty() {
        return value == 0;
    }

    public boolean isEqual(Node node) {
        return value == node.getValue() && value != 0;
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

    @Override
    public String toString() {
        return "Node{" + "line=" + line + ", col=" + col + '}';
    }


}
