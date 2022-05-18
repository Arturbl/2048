package com.example._2048.model;

import com.example._2048.controller.KeyController;
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

    private int numPaintedNodes;

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
            KeyController.updateCanMove();
            delay(150);
            Node node = selectBlock();
            node.incrementValue();
            node.getRectangle().setFill(Color.LIGHTSALMON);
            delay(150);
            node.paint();
            KeyController.updateCanMove();
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

    private LinkedList<Node> sumNodes(LinkedList<Node> nodes, int index) {
        if(index + 1 < nodes.size()) { // currentNode.isEmpty() if this happens, then the end of possible equals nodes has ended
            Node currentNode = nodes.get(index);
            if(!currentNode.isEmpty()) {
                Node nextNode = nodes.get(index + 1);
                if(currentNode.isEqual(nextNode)) {
                    getNode(currentNode.getLine(), currentNode.getCol()).incrementValue();
                    currentNode.incrementValue();
                    resetNode(getNode(nextNode.getLine(), nextNode.getCol()));
                    resetNode(nextNode);
                    return sortArray(nodes);
                }
                return sumNodes(nodes, index + 1);
            }
            return nodes;
        }
        return nodes;
    }

    // sort array over column or lines to insert filled elements at first positions
    private LinkedList<Node> sortArray(LinkedList<Node> nodes) {
        // ao dar sort, em vez de trocar posicoes, os nodes vazios devem passar para o final
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size() - i - 1; j++) {
                Node currentNode = nodes.get(j);
                if (currentNode.isEmpty()) {
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
    private void updateGUI(LinkedList<Node> nodes, boolean isColumn, int index) {
        LinkedList<Node> updatedNodes = new LinkedList<>(); // prevent updating the same node twice
        int line = 0;
        int col = 0;
        for (Node node : nodes) {
            Node tableNode = isColumn ? getNode(line, index) : getNode(index, col); // node in matrix, original node referenfce in memory
            if (tableNode.isEmpty() && !node.isEmpty() && !updatedNodes.contains(tableNode)) {
                tableNode.replace(node);
                updatedNodes.add(tableNode);
                // node != table.getNode() because of the memory reference
                resetNode(getNode(node.getLine(), node.getCol())); // we have to reset the node on the table reference memory
            }
            line++;
            col++;
        }
    }

    public void moveUp() {
        for(int col = 0; col < COLS; col++) {
            LinkedList<Node> orderedColumn =  sortArray( getCol(col) );
            LinkedList<Node> finalColumn = sumNodes(orderedColumn, 0);
            updateGUI(finalColumn, true, col);
        }
    }

    public void moveLeft() {
        for(int line = 0; line < LINES; line++) {
            LinkedList<Node> orderedLine =  sortArray( getLine(line) );
            LinkedList<Node> finalColumn = sumNodes(orderedLine, 0);
            updateGUI(finalColumn, false, line);
        }
    }

    public void moveDown() {
        for(int col = 0; col < COLS; col++) {
            LinkedList<Node> orderedColumn =  sortArray( getCol(col) );
            LinkedList<Node> finalColumn = reverse(sumNodes(orderedColumn, 0));
            updateGUI(finalColumn, true, col);
        }
    }

    public void moveRight() {
        for(int line = 0; line < LINES; line++) {
            LinkedList<Node> orderedLine =  sortArray( getLine(line) );
            LinkedList<Node> finalColumn = sumNodes(orderedLine, 0);
            updateGUI(finalColumn, false, line);
        }
    }

    private LinkedList<Node> reverse(LinkedList<Node> linkedList) {
        LinkedList<Node> reverse = new LinkedList<>();
        linkedList.forEach(node -> {
            if(!node.isEmpty()) {
                reverse.addLast(node);
            }
         });
        return reverse;
    }

    public void print() {
        for (int i = 0; i < LINES; i++) {
            LinkedList<Node> line = new LinkedList<>();
            for (int j = 0; j < COLS; j++) {
                Node node = getNode(i, j);
                line.add(node);
            }
            line.forEach(System.out::println);
            System.out.println("\n");
        }
    }

}
