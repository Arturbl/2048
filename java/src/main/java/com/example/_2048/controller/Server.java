package com.example._2048.controller;

import com.example._2048.model.Table;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Server extends Thread {

    private Table table;

    private  ServerSocket serverSocket ;
    private  Socket clientSocket ;
    private  BufferedReader in;

    public Server(Table table) {
        this.table = table;
        try {
            serverSocket = new ServerSocket(8000);
            System.out.println("Server running on ip: " + serverSocket.getLocalSocketAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while(true) {
            try {
                clientSocket = serverSocket.accept();
                in = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            connectToClient();
        }
    }

    public void connectToClient(){
        Thread clientThread = new Thread(new Runnable() {
            String msg ;
            @Override
            public void run() {
//                System.out.println("Connected with " + clientSocket.getLocalAddress());
                try {
                    msg = in.readLine();
                    while(msg!=null){
                        System.out.println(msg);
                        if(msg.contains("up")) Platform.runLater(() -> table.moveUp());
                        if(msg.contains("down")) Platform.runLater(() -> table.moveDown());
                        if(msg.contains("right")) Platform.runLater(() -> table.moveRight());
                        if(msg.contains("left")) Platform.runLater(() -> table.moveLeft());
                        List<String> commands = Arrays.asList("up", "down", "right", "left");
                        if(commands.contains(msg)) Platform.runLater(() -> table.generateNewBlock());
                        msg = in.readLine();
                    }
//                    System.out.println("Connection lost with " + clientSocket.getLocalAddress());
//                    out.close();
                    clientSocket.close();
//                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        clientThread.start();
    }

}