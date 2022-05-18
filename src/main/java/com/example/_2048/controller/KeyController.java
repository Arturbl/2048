package com.example._2048.controller;

import com.example._2048.model.Table;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyController implements EventHandler {

    private static boolean canMove; // if a new node is showing up (delay), the user has to wait until its complete

    private Table table;

    public KeyController(Table table) {
        canMove = true;
        this.table = table;
    }

    public static void updateCanMove() {
        canMove = !canMove;
    }

    @Override
    public void handle(Event e) {
        if(e.getEventType() == KeyEvent.KEY_PRESSED && canMove) {
            KeyEvent event = (KeyEvent) e;

            if(event.getCode() == KeyCode.UP) {
                table.moveUp();
            }

            if(event.getCode() == KeyCode.DOWN) {
                table.moveDown();
            }

            if(event.getCode() == KeyCode.RIGHT) {
                table.moveRight();
            }

            if(event.getCode() == KeyCode.LEFT) {
                table.moveLeft();
            }

            table.generateNewBlock();
        }
    }

}
