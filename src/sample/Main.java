package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.swing.text.LabelView;
import java.awt.datatransfer.FlavorEvent;
import java.io.IOException;


public class Main extends Application {

    Button button;
    final int WIDTH = 400;
    final int HEIGHT = 400;
    private int wordIndex;
    private boolean newGame = false;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Menu scene
        VBox root = new VBox();
        Text texte = new Text("HANGMAN");
        texte.setFont(Font.font("arial", 25));
        Button startGame = new Button();
        startGame.setText("START GAME");
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(texte,startGame);
        primaryStage.setTitle("HANGMAN");
        Scene scene1 = new Scene(root, WIDTH, HEIGHT);

        //Game scene
        VBox game = new VBox();
        Text topTitle = new Text("HANGMAN");
        topTitle.setFont(Font.font("arial", 55));
        Text title = new Text("Guess the word:");
        title.setFont(Font.font("arial", 16));

        //Word
        String word = new Word().wordGenerator();
        String dash = " _ ";
        dash = dash.repeat(word.length());
        Text letters = new Text(dash);
        title.setFont(Font.font("arial", 16));

        System.out.println(word);

        //User guess
        LimitedTextField input = new LimitedTextField(1);
        input.setPrefWidth(WIDTH/4);
        input.setMaxWidth(WIDTH/4);
        Button guess = new Button("GUESS");

        //Lives
        HBox lives = new HBox();
        lives.setSpacing(20);
        lives.setAlignment(Pos.CENTER);
        Label life1 = new Label("O");
        life1.setFont(Font.font("arial", 16));
        life1.setTextFill(Color.GREEN);
        Label life2 = new Label("O");
        life2.setFont(Font.font("arial", 16));
        life2.setTextFill(Color.GREEN);
        Label life3 = new Label("O");
        life3.setFont(Font.font("arial", 16));
        life3.setTextFill(Color.GREEN);
        lives.getChildren().addAll(life1,life2,life3);


        game.setSpacing(10);
        game.setAlignment(Pos.CENTER);
        game.getChildren().addAll(topTitle, title,letters,input,guess, lives);
        Scene scene2 = new Scene(game, WIDTH, HEIGHT);


        primaryStage.setScene(scene1);
        primaryStage.show();

        //Button Actions
        startGame.setOnMouseClicked(mouseEvent -> {
            primaryStage.setScene(scene2);
        });

        guess.setOnMouseClicked(mouseEvent -> {

            String userGuess = input.getText();
            if (input.getText().matches("[0-9]") || input.getText().isEmpty() || input.getText().isBlank()){
                System.out.println("You must enter a character");
            }else if (word.contains(userGuess)){
                wordIndex = word.indexOf(userGuess);
                System.out.println("yes!");
                //todo make letters appear
                //todo add hints
            }else{
                System.out.println("no!");
                if (life1.getTextFill() == Color.GREEN){
                    life1.setText("X");
                    life1.setTextFill(Color.RED);
                } else if (life1.getTextFill() == Color.RED && life2.getTextFill() == Color.GREEN){
                    life2.setText("X");
                    life2.setTextFill(Color.RED);
                } else if (life2.getTextFill() == Color.RED && life3.getTextFill() == Color.GREEN){
                    life3.setText("X");
                    life3.setTextFill(Color.RED);

                    Label gameOver = new Label("Game Over!");
                    gameOver.toFront();
                    gameOver.setFont(Font.font("arial", 40));
                    gameOver.setTextFill(Color.RED);
                    game.getChildren().add(gameOver);

                    guess.setDisable(true);

                    final Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    Window stage = null;
                    dialog.initOwner(stage);
                    VBox dialogVbox = new VBox(20);
                    dialogVbox.setAlignment(Pos.CENTER);
                    Button restart = new Button("RESTART GAME");
                    dialogVbox.getChildren().addAll(new Text("You loose!"), restart);
                    Scene dialogScene = new Scene(dialogVbox, 100, 100);
                    dialog.setScene(dialogScene);
                    dialog.show();


                    restart.setOnMouseClicked(mouseEvent1->{
                        newGame = true;
                        primaryStage.setScene(scene2);
                        primaryStage.show();
                        dialog.close();
                    });

                }
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
