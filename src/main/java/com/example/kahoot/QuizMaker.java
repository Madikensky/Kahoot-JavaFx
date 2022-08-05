package com.example.kahoot;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class QuizMaker extends Application {
    private DataInputStream dis;
    private Pane root;
    private DataOutputStream toServer;
    ArrayList<String> nicknames = new ArrayList<>();

    public void init() {
        root = new Pane();
    }
        @Override
        public void start (Stage stage) throws Exception {

            ImageView pinCode = new ImageView("C:\\Users\\Madiyar\\Desktop\\resources\\img\\pincode.png");

            TextField tfPin = new TextField();
            tfPin.setPromptText("Game PIN");
            tfPin.setLayoutX(400);
            tfPin.setLayoutY(380);

            Button connect = new Button("Connect to Server");
            connect.setLayoutX(410);
            connect.setLayoutY(500);
            connect.setTextFill(Color.WHITE);
            connect.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 13));
            connect.setStyle("-fx-background-color: black");
            connect.setOnAction(e -> {
                    try {
                        connectToServer();
                    }
                    catch (Exception ioE){
                        ioE.printStackTrace();
                    }
            });

            tfPin.setOnAction(e -> {
                    if (!tfPin.getText().contains("null")) {
                        ImageView nicknamePng = new ImageView("C:\\Users\\Madiyar\\Desktop\\resources\\img\\pincode.png");
                        Pane pane = new Pane();
                        Button okGo = new Button("OK, go!");
                        okGo.setTextFill(Color.WHITE);
                        okGo.setLayoutX(447);
                        okGo.setLayoutY(425);
                        okGo.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 12));
                        okGo.setStyle("-fx-background-color: black");
                        okGo.setOnAction(j -> {
                            Pane answers = new Pane();
                            Rectangle r1 = new Rectangle();
                            r1.setFill(Color.RED);
                            r1.setLayoutX(1);
                            r1.setLayoutY(1);
                            Rectangle r2 = new Rectangle();
                            r2.setFill(Color.BLUE);
                            r2.setLayoutX(100);
                            r2.setLayoutY(200);
                            Rectangle r3 = new Rectangle();
                            r3.setFill(Color.YELLOW);
                            r3.setLayoutX(204);
                            r3.setLayoutY(300);
                            Rectangle r4 = new Rectangle();
                            r4.setFill(Color.GREEN);
                            r4.setLayoutX(40);
                            r4.setLayoutY(250);
                            answers.getChildren().addAll(r1, r2, r3, r4);
                            stage.setScene(new Scene(pane, 400, 400));
                        });

                        Rectangle rectangle = new Rectangle(170, 40, 170, 40);
                        rectangle.setFill(Color.BLACK);
                        rectangle.setLayoutX(219);
                        rectangle.setLayoutY(380);

                        TextField tfNickname = new TextField();
                        tfNickname.setPromptText("Nickname");
                        tfNickname.setLayoutX(400);
                        tfNickname.setLayoutY(380);
                        tfNickname.setOnAction(c -> {
                            nicknames.add(tfNickname.getText());
                            String joined = String.join(",", nicknames);
                            try {
                                toServer.writeUTF(joined);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        });

                        pane.getChildren().addAll(nicknamePng, tfNickname, rectangle, okGo);
                        stage.setScene(new Scene(pane));

                    } else {
                        Pane pane = new Pane();
                        ImageView restart = new ImageView("C:\\Users\\Madiyar\\Desktop\\resources\\img\\restart.jpg");
                        Button tryAgain = new Button("Try Again");
                        Text text = new Text("You entered invalid PIN, please, try again!");
                        text.setFill(Color.WHITE);
                        text.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 18));
                        text.setLayoutX(320);
                        text.setLayoutY(50);
                        tryAgain.setOnAction(t -> {
                            System.exit(1);
                        });
                        tryAgain.setLayoutX(310);
                        tryAgain.setLayoutY(470);
                        tryAgain.setMinSize(400, 44);
                        pane.getChildren().addAll(restart, tryAgain, text);
                        stage.setScene(new Scene(pane));
                    }

                try {
                    toServer.writeUTF(tfPin.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            Button enter = new Button("Enter");
            enter.setTextFill(Color.WHITE);
            enter.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 13));
            enter.setStyle("-fx-background-color: black");
            enter.setLayoutX(447);
            enter.setLayoutY(445);

            Rectangle rectangle2 = new Rectangle(170, 40, 170, 40);
            rectangle2.setFill(Color.BLACK);
            rectangle2.setLayoutX(220);
            rectangle2.setLayoutY(455);

            Rectangle rectangle = new Rectangle(170, 40, 170, 40);
            rectangle.setFill(Color.BLACK);
            rectangle.setLayoutX(219);
            rectangle.setLayoutY(400);

            root.getChildren().addAll(pinCode, tfPin, rectangle, enter, rectangle2, connect);
            stage.setScene(new Scene(root));
            stage.setTitle("Player");
            stage.show();
            root.requestFocus();
        }
    public void connectToServer() throws Exception{
        Socket client = new Socket("127.0.0.1", 2021);
        toServer = new DataOutputStream(client.getOutputStream());
    }
}


