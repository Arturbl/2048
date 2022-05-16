package com.example._2048.model;

import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class Table extends Parent {

    private static final int LINES = 4;
    private static final int COLS = 4;

    private final VBox table = new VBox();

    public Table() {
        for (int i = 0; i < LINES; i++) {
            HBox col = new HBox();
            for (int j = 0; j < COLS; j++) {
                Node node = new Node(i, j, this);
                col.getChildren().add(node);
            }
            table.getChildren().add(col);
        }
        generateNewBlock();
    }

    public void generateNewBlock() {
        Node node = selectBlock();
        node.incrementValue();
        node.paint();
    }

    private Node selectBlock() {
        int line = ThreadLocalRandom.current().nextInt(0, LINES);
        int col = ThreadLocalRandom.current().nextInt(0, COLS);
        Node node = getNode(line, col);
        return node.getValue() == 0 ? node : selectBlock();
    }

    public Node getNode(int line, int col) {
        HBox hBox;
        Node node;
        try {
            hBox = (HBox) table.getChildren().get(line);
            node = (Node) hBox.getChildren().get(col);
        } catch (Exception e) {
            return null;
        }
        return node;
    }

    public VBox getTable() {
        return table;
    }

    public LinkedList<Node> getLine(int lineIndex) {
        LinkedList<Node> line = new LinkedList<>();
        for (int c = 0; c < COLS; c++) {
            // if the node has the same memory reference as the helper array, at the time of the switch, the node will be updated twice
            // call Node.copy() to create a new memory reference
            Node node = getNode(lineIndex, c).copy();
            line.addLast(node);
        }
        return line;
    }

    public LinkedList<Node> getCol(int colIndex) {
        LinkedList<Node> column = new LinkedList<>();
        for (int l = 0; l < LINES; l++) {
            // if the node has the same memory reference as the helper array, at the time of the switch, the node will be updated twice
            // call Node.copy() to create a new memory reference
            Node node = getNode(l, colIndex).copy();
            column.addLast(node);
        }
        return column;
    }

    // iterate over column or lines to insert filled elements at first positions
    private LinkedList<Node> iterateArray(LinkedList<Node> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size() - i - 1; j++) {
                if (nodes.get(j).isEmpty()) {
                    Node currentNode = nodes.get(j);
                    Node nextNode = nodes.get(j + 1);
                    nodes.set(j, nextNode);
                    nodes.set(j + 1, currentNode);
                }
            }
        }
        return nodes;
    }

    // isColumn, true if current method is calculation a column else false
    // index, colNumber is isColumn else lineNumber
    private LinkedList<Node> updateGUI(LinkedList<Node> nodes, boolean isColumn, int index) {
        int line = 0;
        int col = 0;
        for(int idx = 0; idx < nodes.size(); idx++) {
            Node node = nodes.get(idx);
            Node tableNode;
            if(isColumn) {
                tableNode = getNode(line, index);
                line++; // the only variable that changes is the line, the col remains the same
            } else {
                tableNode = getNode(index, col);
                col++;
            }
//            System.out.println("node" + node);
            if( tableNode.isEmpty() && !node.isEmpty() ) {
                tableNode.replace(node);
                node.reset();
                nodes.set(idx, tableNode);
//                System.out.println("    tablenode: " + tableNode);
            }
        }
        return nodes;
    }

    public void moveUp() {
        for(int col = 0; col < COLS; col++) {
            LinkedList<Node> orderedColumn =  iterateArray( getCol(col));
            LinkedList<Node> nodes = updateGUI(orderedColumn, true, col);
            nodes.forEach(System.out::println);
            System.out.println("\n");
        }
    }

    public void moveDown() {}

    public void moveRight() {}

    public void moveLeft() {
        for(int line = 0; line < LINES; line++) {
            LinkedList<Node> orderedLine =  iterateArray( getLine(line));
            LinkedList<Node> nodes = updateGUI(orderedLine, false, line);
            nodes.forEach(System.out::println);
            System.out.println("\n");
        }
    }

    public void print() {
        for (int i = 0; i < LINES; i++) {
            LinkedList<Node> line = new LinkedList<>();
            for (int j = 0; j < COLS; j++) {
                Node node = new Node(i, j, this);
                line.add(node);
            }
            line.forEach(System.out::println);
            System.out.println("\n");
        }
    }

}
