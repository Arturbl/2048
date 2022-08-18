package com.example._2048.model;

import com.example._2048.util.Colors;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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
        generateNewBlock();
    }

    private void delay(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void generateNewBlock() {
        new Thread(() -> {
            delay(150);
            Node node = selectBlock();
            node.incrementValue();
            node.getRectangle().setFill(Color.LIGHTSALMON);
            delay(150);
            node.paint();
            print();
        }).start();
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

    private void resetNode(Node node) {
        node.getText().setText("");
        node.getRectangle().setFill(Colors.getColor(0));
        node.setValue(0);
    }

    public LinkedList<Node> getLine(int lineIndex) {
        LinkedList<Node> line = new LinkedList<>();
        for (int c = 0; c < COLS; c++) {
            Node node = getNode(lineIndex, c);
            line.addLast(node);
        }
        return line;
    }

    public LinkedList<Node> getCol(int colIndex) {
        LinkedList<Node> column = new LinkedList<>();
        for (int l = 0; l < LINES; l++) {
            Node node = getNode(l, colIndex);
            column.addLast(node);
        }
        return column;
    }

    private LinkedList<Node> sumNodes(LinkedList<Node> nodes, int index, boolean reverse) {
        boolean condition = reverse ? index - 1 >= 0 : index + 1 < nodes.size();
        if (condition) {
            Node currentNode = nodes.get(index);
            int idx = reverse ? index - 1 : index + 1;
            Node nextNode = nodes.get(idx);
            if (!currentNode.isEmpty() && !nextNode.isEmpty() && currentNode.isEqual(nextNode)) {
                if(reverse) {
                    nextNode.incrementValue();
                    resetNode(currentNode);
                } else {
                    currentNode.incrementValue();
                    resetNode(nextNode);
                }
                return reverse ? reverseArray(nodes) : sortArray(nodes);
            }
            return sumNodes(nodes, idx, reverse);
        }
        return nodes;
    }

    // sort array over column or lines to insert filled elements at first positions
    private LinkedList<Node> sortArray(LinkedList<Node> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size() - i - 1; j++) {
                Node currentNode = nodes.get(j);
                if (currentNode.isEmpty()) {
                    Node nextNode = nodes.get(j + 1);
                    currentNode.replace(nextNode);
                    resetNode(nextNode);
                }
            }
        }
        return nodes;
    }

    private LinkedList<Node> reverseArray(LinkedList<Node> nodes) {
        for (int i = nodes.size() - 1; i > 0; i--) {
            for (int j = nodes.size() - i; j > 0; j--) {
                Node currentNode = nodes.get(j);
                if (currentNode.isEmpty()) {
                    Node previousNode = nodes.get(j - 1);
                    currentNode.replace(previousNode);
                    resetNode(previousNode);
                }
            }
        }
        return nodes;
    }


    public void moveUp() {
        for (int col = 0; col < COLS; col++) {
            LinkedList<Node> orderedColumn = sortArray(getCol(col));
            sumNodes(orderedColumn, 0, false);
        }
    }

    public void moveLeft() {
        for (int line = 0; line < LINES; line++) {
            LinkedList<Node> orderedLine = sortArray(getLine(line));
            sumNodes(orderedLine, 0, false);
        }
    }

    public void moveDown() {
        for (int col = 0; col < COLS; col++) {
            LinkedList<Node> orderedColumn = reverseArray(getCol(col));
            sumNodes(orderedColumn, orderedColumn.size() - 1, true);
        }
    }

    public void moveRight() {
        for (int line = 0; line < LINES; line++) {
            LinkedList<Node> orderedLine = reverseArray(getLine(line));
            sumNodes(orderedLine, orderedLine.size() - 1, true);
        }
    }

    public void print() {
        for (int i = 0; i < LINES; i++) {
            LinkedList<Node> line = new LinkedList<>();
            for (int j = 0; j < COLS; j++) {
                Node node = getNode(i, j);
                line.add(node);
            }
//            line.forEach(System.out::println);
//            System.out.println("\n");
        }
    }
}