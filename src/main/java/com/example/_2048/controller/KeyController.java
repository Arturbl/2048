package com.example._2048.controller;

import com.example._2048.model.Table;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyController implements EventHandler {

    private Table table;

    public KeyController(Table table) {
        this.table = table;
    }

    @Override
    public void handle(Event e) {
        if(e.getEventType() == KeyEvent.KEY_PRESSED) {
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
