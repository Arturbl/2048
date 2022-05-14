package com.example._2048.model;

import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Table extends Parent {

    private static final int LINES = 4;
    private static final int COLS = 4;

    private final VBox table = new VBox();

    public Table(){
        for (int i = 0; i < LINES; i++) {
            HBox col = new HBox();
            for (int j = 0; j < COLS; j++) {
                Node node = new Node(i,j, this);
                col.getChildren().add(node);
            }
            table.getChildren().add(col);
        }
        generateNewBlock();
    }

    public void generateNewBlock() {
        Node node = selectBlock();
        node.paint();
    }

    private Node selectBlock() {
        int line = ThreadLocalRandom.current().nextInt(0, LINES);
        int col = ThreadLocalRandom.current().nextInt(0, COLS);
        Node node = getNode(line, col);
        if(node.getValue() == 0) {
            return node;
        }
        return selectBlock();
    }

    public Node getNode(int line, int col) {
        HBox hBox;
        Node rect;
        try {
            hBox = (HBox) table.getChildren().get(line);
            rect = (Node) hBox.getChildren().get(col);
        } catch (Exception e) {
            return null;
        }
        return rect;
    }

    public VBox getTable(){
        return table;
    }

    public LinkedList<Node> getLine(int lineIndex) {
        LinkedList<Node> line = new LinkedList<>();
        for (int c = 0; c < COLS; c++) {
            Node node = getNode(lineIndex, c);
            if(!node.isEmpty()) {
                line.addLast(node);
            }
        }
        return line;
    }

    public LinkedList<Node> getCol(int colIndex) {
        LinkedList<Node> column = new LinkedList<>();
        for (int l = 0; l < LINES; l++) {
            Node node = getNode(l, colIndex);
            if(!node.isEmpty()) {
                column.addLast(node);
            }
        }
        return column;
    }

    public void moveUp() {
        for(int col = 0; col < COLS; col++) {
            LinkedList<Node> column = getCol(col);
            Node previousNode = column.size() > 0 ? column.get(0) : null;
            if(previousNode != null) { // in case the column is empty, we do not need to calculate
                int index = 1;
                while(index < column.size()) {
                    Node currentNode = column.get(index);
                    if(previousNode.isEqual(currentNode)) {
                        previousNode.incrementValue();
                        
                    }
                    previousNode = getNode(index - 1, col);
                    index++;
                }
//                 if node 2 is equal to 1 then node1.increment(). Node 2 should be equal to node 3 and consecutively

            }
            System.out.println("\n");
        }
    }

    public void moveDown() {}

    public void moveRight() {}

    public void moveLeft() {}

}
