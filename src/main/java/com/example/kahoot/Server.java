package com.example.kahoot;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import javafx.util.Duration;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Random;
import java.util.Scanner;

public class Server extends Application {
    private QuizFx quiz;
    private Stage monitor;
    private int correctAnswers;
    private Pane root;
    private Pane pane;
    private int a = 200;
    private int b = 300;
    private int clientsCount;
    private Timeline timeline;


    private Random random = new Random();
    int pin = random.nextInt(100000, 999999);
    private DataInputStream fromClient;


    public void init() {
        root = new Pane();
    }

    public BorderPane specialQuestion(int ind) {

        Question question = quiz.getQ(ind);

        Button next = new Button("-->");
        Button previous = new Button("<--");
        next.setMinSize(20, 20);
        previous.setMinSize(20, 20);
        next.setLayoutX(565);
        next.setLayoutY(110);
        previous.setLayoutX(1);
        previous.setLayoutY(110);


        next.setVisible(false); //PROJECT 3
        previous.setVisible(false); //PROJECT 3

        BorderPane borderPane = new BorderPane();

        ImageView imageView = new ImageView(new Image("C:\\Kahoot\\src\\main\\java\\com\\example\\kahoot\\resources\\img\\logo.png"));
        imageView.setFitHeight(200);
        imageView.setFitWidth(280);
        imageView.setX(160);
        imageView.setY(50);

        Label label = new Label(question.getDescription());
        label.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 16));
        label.setWrapText(true);

        Rectangle rectangle1 = new Rectangle(317, 70, 317, 70);
        rectangle1.setFill(Color.ORANGE);
        rectangle1.setX(1);
        rectangle1.setY(303);


        rectangle1.setVisible(true); //PROJECT 3

        Rectangle rectangle2 = new Rectangle(317, 70, 317, 70);
        rectangle2.setFill(Color.RED);
        rectangle2.setX(1);
        rectangle2.setY(230);

        rectangle2.setVisible(true); //PROJECT 3

        Rectangle rectangle3 = new Rectangle(320, 70, 320, 70);
        rectangle3.setFill(Color.BLUE);
        rectangle3.setX(320);
        rectangle3.setY(230);

        rectangle3.setVisible(true); //PROJECT 3

        Rectangle rectangle4 = new Rectangle(320, 70, 320, 70);
        rectangle4.setFill(Color.GREEN);
        rectangle4.setX(320);
        rectangle4.setY(303);

        Circle circle = new Circle(40);
        circle.setFill(Color.PURPLE);
        circle.setLayoutX(40);
        circle.setLayoutY(150);

        timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                monitor.setScene(new Scene(specialQuestion(ind + 1), 600, 400));
            }
        }));



        rectangle4.setVisible(true); //PROJECT 3

        HBox hBox = new HBox();

        //PROJECT 3

        if (question instanceof Test) {

            Text one = new Text(((Test) question).getOptionAt(0));
            one.setLayoutX(30);
            one.setLayoutY(250);

            one.setVisible(true); //PROJECT 3

            Text two = new Text(((Test) question).getOptionAt(1));
            two.setLayoutX(240);
            two.setLayoutY(250);

            two.setVisible(true); //PROJECT 3

            Text three = new Text(((Test) question).getOptionAt(2));
            three.setLayoutX(30);
            three.setLayoutY(70);

            three.setVisible(true); //PROJECT 3

            Text four = new Text(((Test) question).getOptionAt(3));
            four.setLayoutX(240);
            four.setLayoutY(70);

            four.setVisible(true); //PROJECT 3


            VBox vb1 = new VBox(10);
            vb1.getChildren().addAll(new Pane(one), new Pane(three));
            VBox vb2 = new VBox(10);
            vb2.getChildren().addAll(new Pane(two), new Pane(four));
            hBox = new HBox(10);
            hBox.getChildren().addAll(vb1, vb2);

        }
        Pane pane = new Pane();
        pane.getChildren().addAll(rectangle1, rectangle2, rectangle3, rectangle4, hBox, next, previous, circle);


        if (question instanceof FillIn) {
            ImageView smallPng = new ImageView("CC:\\Kahoot\\src\\main\\java\\com\\example\\kahoot\\resources\\img\\k.png");
            ImageView imageFill = new ImageView(new Image("C:\\Kahoot\\src\\main\\java\\com\\example\\kahoot\\resources\\img\\fillin.png"));

            smallPng.setLayoutX(115);
            smallPng.setLayoutY(1);
            smallPng.setFitWidth(32);
            smallPng.setFitHeight(32);

            imageFill.setFitHeight(210);
            imageFill.setFitWidth(300);
            imageFill.setX(140);
            imageFill.setY(40);

            rectangle1.setVisible(false);
            rectangle2.setVisible(false);
            rectangle3.setVisible(false);
            rectangle4.setVisible(false);
            imageView.setVisible(false);

            TextField textField = new TextField();
            textField.setLayoutX(100);
            textField.setLayoutY(240);
            textField.setMinSize(400, 20);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent k) {
                    if (k.getCode().equals(KeyCode.ENTER)) {
                        if (textField.getText().equals((question.getAnswer()))) {
                            correctAnswers++;
                        }
                    }
                }
            });
            Text text = new Text("Type your answer here:");
            text.setX(230);
            text.setY(220);


            pane.getChildren().addAll(textField, text);
            borderPane.getChildren().addAll(imageFill, smallPng);
        }
        borderPane.setBottom(pane);
        borderPane.setTop(new StackPane(label));
        borderPane.getChildren().addAll(imageView);

        if (ind == quiz.getN() - 1) {
            next.setVisible(false);
            Pane last = new Pane();
            Button result = new Button("✓");
            borderPane.setRight(result);

            Text text = new Text("Your Result:");
            text.setX(240);
            text.setY(20);
            text.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 18));

            Button close = new Button("Close Test");
            close.setTextFill(Color.WHITE);
            close.setStyle("-fx-background-color: red");
            close.setLayoutX(160);
            close.setLayoutY(200);
            close.setMinSize(280, 40);
            close.setOnAction(e -> {
                monitor.close();
            });
            Button show = new Button("Show answers");
            show.setTextFill(Color.WHITE);
            show.setStyle("-fx-background-color: blue");
            show.setLayoutX(160);
            show.setLayoutY(150);
            show.setMinSize(280, 40);

            Text numANS = new Text();
            numANS.setText((correctAnswers * 100) / 5 + "%");
            numANS.setX(280);
            numANS.setY(50);
            Text number = new Text("Number of correctAnswers: " + correctAnswers + "/5");
            number.setX(210);
            number.setY(80);

            ImageView resultImage = new ImageView("C:\\Kahoot\\src\\main\\java\\com\\example\\kahoot\\resources\\img\\result.png");
            resultImage.setFitHeight(150);
            resultImage.setFitWidth(260);
            resultImage.setLayoutX(160);
            resultImage.setLayoutY(240);

            last.getChildren().addAll(text, close, show, numANS, number, resultImage);

            result.setOnAction(e -> {
                monitor.setScene(new Scene(last, 600, 400));
            });

        }

        return borderPane;
    }

    @Override
    public void start(Stage stage) throws Exception {
        new Thread(() -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    pane = new Pane();
                    Text text = new Text("Join with the Game PIN: " + pin);
                    Text wait = new Text("Waiting other players...");
                    Button start = new Button();
                    wait.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 25));
                    wait.setFill(Color.WHITE);
                    wait.setLayoutX(120);
                    wait.setLayoutY(630);
                    text.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 35));
                    text.setFill(Color.WHITE);
                    text.setLayoutX(300);
                    text.setLayoutY(40);
                    ImageView loading = new ImageView("C:\\Kahoot\\src\\main\\java\\com\\example\\kahoot\\resources\\img\\background.jpg");
                    pane.getChildren().addAll(loading, text, wait);
                    stage.setScene(new Scene(pane, 1150, 640));
                    stage.show();
                }
            });
            try {

                ServerSocket serverSocket = new ServerSocket(2021);
                clientsCount = 1;

                while (true) {
                    Socket conn = serverSocket.accept();
                    if (clientsCount == 1)
                        System.out.println(clientsCount + "'st" + " player connected to the server!");
                    else if (clientsCount == 2)
                        System.out.println(clientsCount + "'nd" + " player connected to the server!");
                    else if (clientsCount == 3)
                        System.out.println(clientsCount + "'rd" + " player connected to the server!");
                    else
                        System.out.println(clientsCount + "'th" + " player connected to the server!");
                    clientsCount++;

                     new Thread(() -> {
                        try {
                            DataInputStream fromClient = new DataInputStream(conn.getInputStream());
                            if (fromClient.readUTF().equals(pin + "")) {
                                a = a + 100;
                                Text players = new Text(fromClient.readUTF());
                                Text count = new Text((clientsCount - 1) + "" + "\n" + "Player");
                                Button start = new Button("Start");
                                start.setOnAction(e -> {
                                    monitor.setScene(new Scene(specialQuestion(0), 600, 400));
                                    timeline.setCycleCount(Timeline.INDEFINITE);
                                    timeline.play();
                                });

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        players.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 25));
                                        players.setFill(Color.WHITE);
                                        players.setLayoutX(a);
                                        players.setLayoutY(b);

                                        count.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 35));
                                        count.setLayoutX(100);
                                        count.setLayoutY(300);
                                        count.setFill(Color.WHITE);

                                        start.setTextFill(Color.WHITE);
                                        start.setStyle("-fx-background-color: gray");
                                        start.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 25));
                                        start.setLayoutX(1050);
                                        start.setLayoutY(300);

                                        pane.getChildren().addAll(players, count, start);
                                        stage.setScene(new Scene(pane, 1150, 640));
                                        stage.show();
                                    }
                                });
                            }
                        } catch (IOException e) {
                        }
                    }).start();
                }
            } catch (IOException e) {
            }
        }).start();


        quiz = new QuizFx();
        quiz = QuizFx.loadFromFile("C:\\Kahoot\\src\\main\\java\\com\\example\\kahoot\\NewQuiz.txt");
        monitor = stage;
        monitor.setScene(new Scene(specialQuestion(0), 600, 400));
        monitor.setTitle("Server");
        monitor.show();
    }
}