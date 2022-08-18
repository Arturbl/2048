package com.example._2048.controller;

import com.example._2048.model.Table;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Server extends Thread {

    private Table table;

    private  ServerSocket serverSocket ;
    private  Socket clientSocket ;
    private  BufferedReader in;
    private  PrintWriter out;

    public Server(Table table) {
        this.table = table;
        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("Server running on ip: " + serverSocket.toString());
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        System.out.println("Server is running on port: 5000");
        Thread clientThread = new Thread(new Runnable() {
            String msg ;
            @Override
            public void run() {
                try {
                    msg = in.readLine();
                    while(msg!=null){
                        System.out.println(msg);
                        Platform.runLater(() -> {
                            if(Objects.equals(msg, "up")) table.moveUp();
                            if(Objects.equals(msg, "down")) table.moveDown();
                            if(Objects.equals(msg, "right")) table.moveRight();
                            if(Objects.equals(msg, "left")) table.moveLeft();
                            table.generateNewBlock();
                        });
                        msg = in.readLine();
                    }
                    System.out.println("Connection lost");
                    out.close();
                    clientSocket.close();
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        clientThread.start();
    }

}