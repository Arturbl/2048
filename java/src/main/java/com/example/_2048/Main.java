package com.example._2048;

import com.example._2048.controller.Server;
import com.example._2048.model.Table;
import com.example._2048.util.Colors;
import com.example._2048.util.Sizing;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        Colors.initColors(); // initialize color palette
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        Table table = new Table();

        Thread serialListener = new Server(table);
        serialListener.start();

        Scene scene = new Scene(table.getTable(), Sizing.getWindowWidth(), Sizing.getWindowHeight());
        stage.setTitle("2048");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}