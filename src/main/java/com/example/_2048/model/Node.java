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
        text.setText("");
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
    }

    public void paint() {
        Paint color = Colors.getColor(value);
        text.setText(Integer.toString(value));
        rectangle.setFill(color);
    }

    // create another memory reference to switch nodes in movements (table.iterateArray() method)
    public Node copy() {
        Node node = new Node(line, col, table);
        node.value = value;
        node.text = text;
        node.rectangle = rectangle;
        return node;
    }

    public void replace(Node newNode) {
        setValue(newNode.getValue());
        text.setText(newNode.text.getText());
        rectangle.setFill(newNode.rectangle.getFill());
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

    public void setLine(int line) {
        this.line = line;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
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
