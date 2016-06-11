/**
 *
 * Graphical user interface code for the Order and Chaos game.
 *
 * Created by jakastavanja on 19/04/16.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import java.awt.Toolkit;


public class OACGUI extends Application {

    Stage window;
    char[][] game = new char[6][6];
    OAC backend = new OAC(game);
    char currentChar = 'X';
    Button[] b = new Button[37];
    int currentPlayer = 1; // 1 - Order, 2 - Chaos
    Label currentPlayerText;
    int moves = 0;
    Scene scene, winner;
    String winnerPlayer = "Chaos";
    Label winnerText;
    int[] alreadyPressed = new int[37];


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        initButtons();
        initGame();

        // printCurrentGame();

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        // scene for game | elements:

        eraseAllButtonText();
        window = primaryStage;
        window.setTitle("Order and Chaos");
        BorderPane border = new BorderPane();

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setId("topNavBar");

        Button buttonCurrent = new Button(String.valueOf(currentChar));
        buttonCurrent.setOnAction(e -> {
            if (currentChar == 'X') {
                currentChar = 'O';
                buttonCurrent.setText(String.valueOf(currentChar));
            } else {
                currentChar = 'X';
                buttonCurrent.setText(String.valueOf(currentChar));
            }
        });
        buttonCurrent.setId("buttonCurrentSymbol");
        // buttonCurrent.setPrefSize(100, 20);

        Button buttonReset = new Button("Reset");
        buttonReset.setOnAction(e -> {
            for(int i = 0; i < 6; i++) {
                for(int j = 0; j < 6; j++) {
                    eraseAllButtonText();
                }
                initGame();
            }
            currentPlayer = 1;
            currentPlayerText.setText("Current player: " + returnPlayerText());
        });
        buttonReset.setId("buttonResetGame");

        currentPlayerText = new Label();
        currentPlayerText.setText("Current player: " + returnPlayerText());
        currentPlayerText.setId("currentPlayerTextLabel");

        // buttonReset.setPrefSize(100, 20);
        hbox.getChildren().addAll(buttonCurrent, buttonReset, currentPlayerText);

        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);
        grid.setPadding(new Insets(10, 10, 10, 10));
        for (int i = 0; i < 6; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(16);
            grid.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 6; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(16);
            grid.getRowConstraints().add(row);
        }

        addButtons(grid);

        border.setTop(hbox);
        border.setCenter(grid);

        // scene for winner | elements:

        GridPane p = new GridPane();

        VBox winnerPane = new VBox();
        winnerPane.setSpacing(10);

        winnerText = new Label();
        winnerText.setText(winnerPlayer + " has won the game!");
        winnerText.setId("winnerTextWinScene");

        Button resetTheGameWhenWinner = new Button();
        resetTheGameWhenWinner.setOnAction(e -> {
            window.setScene(scene);
            for(int i = 0; i < 6; i++) {
                for(int j = 0; j < 6; j++) {
                    eraseAllButtonText();
                }
                initGame();
            }
            currentPlayer = 1;
            currentPlayerText.setText("Current player: " + returnPlayerText());
        });
        resetTheGameWhenWinner.setText("Play again!");

        winnerPane.getChildren().addAll(winnerText, resetTheGameWhenWinner);
        winnerPane.setAlignment(Pos.CENTER);
        p.getChildren().add(winnerPane);
        p.setAlignment(Pos.CENTER);


        // game scene itself

        scene = new Scene(border, 300, 300);
        scene.getStylesheets().add("style.css");

        // when someone wins, this opens

        winner = new Scene(p, 300, 300);
        winner.getStylesheets().add("style.css");


        // first, set the scene to the game

        window.setScene(scene);

        window.setX(primaryScreenBounds.getMinX() + (primaryScreenBounds.getWidth()/4.0) );
        window.setY(primaryScreenBounds.getMinY() + 100);
        window.setWidth(primaryScreenBounds.getWidth() / 1.9);
        window.setHeight(primaryScreenBounds.getHeight() / 1.15);


        window.show();

    }

    public void initButtons() {
        for(int i = 0; i < 37; i++) {
            b[i] = new Button();
            b[i].setMaxWidth(Double.MAX_VALUE);
            b[i].setMaxHeight(Double.MAX_VALUE);
            b[i].setFont(Font.loadFont("file:resources/fonts/mfjulysky.ttf", 60));
        }
    }

    public void initGame() {
        char [][] crke = {  {'a', 'b', 'c', 'd', 'e', 'f'},
                            {'g', 'h', 'i', 'j', 'k', 'l'},
                            {'m', 'n', 'q', 'p', 'q', 'r'},
                            {'s', 't', 'u', 'v', 'w', 'y'},
                            {'z', '1', '2', '3', '4', '5'},
                            {'6', '7', '8', '9', '0', 'J'} };
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                game[i][j] = crke[i][j];
            }
        }

        for(int i = 0; i < 37; i++) {
            alreadyPressed[i] = 0;
        }
    }

    public void eraseAllButtonText() {
        for(int i = 1; i < 37; i++) {
            b[i].setText("  ");
        }
    }

    public String returnPlayerText() {
        if (currentPlayer == 1) {
            return "Order";
        } else {
            return "Chaos";
        }
    }

    public void switchPlayer() {

        if(currentPlayer == 1) {
            currentPlayer = 2;
        } else {
            currentPlayer = 1;
        }
        currentPlayerText.setText("Current player: " + returnPlayerText());
    }

    public void checkGameOver() {
        Toolkit.getDefaultToolkit().beep();

        if(backend.checkForWin()) {
            winnerPlayer = "Order";
            winnerText.setText(winnerPlayer + " has won the game!");
            changeToWinScene();
        }
        switchPlayer();
        if (checkIfFull()) {
            winnerPlayer = "Chaos";
            winnerText.setText(winnerPlayer + " has won the game!");
            changeToWinScene();
        };
    }

    public void changeToWinScene() {
        window.setScene(winner);
    }

    public boolean checkIfFull() {
        boolean check = true;
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                if(game[i][j] == 'x' || game[i][j] == 'o') {
                    continue;
                } else {
                    check = false;
                }
            }
        }
        return check;
    }

    public void printCurrentGame() {

        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                System.out.println(game[i][j]);
            }
            System.out.println();
        }
    }

    public void addButtons(GridPane grid) {

        // SECTION FOR ADDING BUTTONS AND THEIR FUNCTIONALITIES

        // 1. row

        b[1].setOnAction(e -> {
            if(alreadyPressed[1] == 0) {
                backend.changeElement(0, 0, currentChar);
                b[1].setText(String.valueOf(backend.returnGame()[0][0]));
                checkGameOver();
                alreadyPressed[1] = 1;
            }
        });
        GridPane.setConstraints(b[1], 0, 0);

        b[2].setOnAction(e -> {
            if(alreadyPressed[2] == 0) {
                backend.changeElement(1, 0, currentChar);
                b[2].setText(String.valueOf(backend.returnGame()[1][0]));
                checkGameOver();
                alreadyPressed[2] = 1;
            }
        });
        GridPane.setConstraints(b[2], 1, 0);

        b[3].setOnAction(e -> {
            if(alreadyPressed[3] == 0) {
                backend.changeElement(2, 0, currentChar);
                b[3].setText(String.valueOf(backend.returnGame()[2][0]));
                checkGameOver();
                alreadyPressed[3] = 1;
            }
        });
        GridPane.setConstraints(b[3], 2, 0);

        b[4].setOnAction(e -> {
            if(alreadyPressed[4] == 0) {
                backend.changeElement(3, 0, currentChar);
                b[4].setText(String.valueOf(backend.returnGame()[3][0]));
                checkGameOver();
                alreadyPressed[4] = 1;
            }
        });
        GridPane.setConstraints(b[4], 3, 0);

        b[5].setOnAction(e -> {
            if(alreadyPressed[5] == 0) {
                backend.changeElement(4, 0, currentChar);
                b[5].setText(String.valueOf(backend.returnGame()[4][0]));
                checkGameOver();
                alreadyPressed[5] = 1;
            }
        });
        GridPane.setConstraints(b[5], 4, 0);

        b[6].setOnAction(e -> {
            if(alreadyPressed[6] == 0) {
                backend.changeElement(5, 0, currentChar);
                b[6].setText(String.valueOf(backend.returnGame()[5][0]));
                checkGameOver();
                alreadyPressed[6] = 1;
            }
        });
        GridPane.setConstraints(b[6], 5, 0);

        // 2. row

        b[7].setOnAction(e -> {
            if(alreadyPressed[7] == 0) {
                backend.changeElement(0, 1, currentChar);
                b[7].setText(String.valueOf(backend.returnGame()[0][1]));
                checkGameOver();
                alreadyPressed[7] = 1;
            }
        });
        GridPane.setConstraints(b[7], 0, 1);

        b[8].setOnAction(e -> {
            if(alreadyPressed[8] == 0) {
                backend.changeElement(1, 1, currentChar);
                b[8].setText(String.valueOf(backend.returnGame()[1][1]));
                checkGameOver();
                alreadyPressed[8] = 1;
            }
        });
        GridPane.setConstraints(b[8], 1, 1);

        b[9].setOnAction(e -> {
            if(alreadyPressed[9] == 0) {
                backend.changeElement(2, 1, currentChar);
                b[9].setText(String.valueOf(backend.returnGame()[2][1]));
                checkGameOver();
                alreadyPressed[9] = 1;
            }
        });
        GridPane.setConstraints(b[9], 2, 1);

        b[10].setOnAction(e -> {
            if(alreadyPressed[10] == 0) {
                backend.changeElement(3, 1, currentChar);
                b[10].setText(String.valueOf(backend.returnGame()[3][1]));
                checkGameOver();
                alreadyPressed[10] = 1;
            }
        });
        GridPane.setConstraints(b[10], 3, 1);

        b[11].setOnAction(e -> {
            if(alreadyPressed[11] == 0) {
                backend.changeElement(4, 1, currentChar);
                b[11].setText(String.valueOf(backend.returnGame()[4][1]));
                checkGameOver();
                alreadyPressed[11] = 1;
            }
        });
        GridPane.setConstraints(b[11], 4, 1);

        b[12].setOnAction(e -> {
            if(alreadyPressed[12] == 0) {
                backend.changeElement(5, 1, currentChar);
                b[12].setText(String.valueOf(backend.returnGame()[5][1]));
                checkGameOver();
                alreadyPressed[12] = 1;
            }
        });
        GridPane.setConstraints(b[12], 5, 1);

        // 3. row

        b[13].setOnAction(e -> {
            if(alreadyPressed[13] == 0) {
                backend.changeElement(0, 2, currentChar);
                b[13].setText(String.valueOf(backend.returnGame()[0][2]));
                checkGameOver();
                alreadyPressed[13] = 1;
            }
        });
        GridPane.setConstraints(b[13], 0, 2);

        b[14].setOnAction(e -> {
            if(alreadyPressed[14] == 0) {
                backend.changeElement(1, 2, currentChar);
                b[14].setText(String.valueOf(backend.returnGame()[1][2]));
                checkGameOver();
                alreadyPressed[14] = 1;
            }
        });
        GridPane.setConstraints(b[14], 1, 2);

        b[15].setOnAction(e -> {
            if(alreadyPressed[15] == 0) {
                backend.changeElement(2, 2, currentChar);
                b[15].setText(String.valueOf(backend.returnGame()[2][2]));
                checkGameOver();
                alreadyPressed[15] = 1;
            }
        });
        GridPane.setConstraints(b[15], 2, 2);

        b[16].setOnAction(e -> {
            if(alreadyPressed[16] == 0) {
                backend.changeElement(3, 2, currentChar);
                b[16].setText(String.valueOf(backend.returnGame()[3][2]));
                checkGameOver();
                alreadyPressed[16] = 1;
            }
        });
        GridPane.setConstraints(b[16], 3, 2);

        b[17].setOnAction(e -> {
            if(alreadyPressed[17] == 0) {
                backend.changeElement(4, 2, currentChar);
                b[17].setText(String.valueOf(backend.returnGame()[4][2]));
                checkGameOver();
                alreadyPressed[17] = 1;
            }
        });
        GridPane.setConstraints(b[17], 4, 2);

        b[18].setOnAction(e -> {
            if(alreadyPressed[18] == 0) {
                backend.changeElement(5, 2, currentChar);
                b[18].setText(String.valueOf(backend.returnGame()[5][2]));
                checkGameOver();
                alreadyPressed[18] = 1;
            }
        });
        GridPane.setConstraints(b[18], 5, 2);

        // 4. row

        b[19].setOnAction(e -> {
            if(alreadyPressed[19] == 0) {
                backend.changeElement(0, 3, currentChar);
                b[19].setText(String.valueOf(backend.returnGame()[0][3]));
                checkGameOver();
                alreadyPressed[19] = 1;
            }
        });
        GridPane.setConstraints(b[19], 0, 3);

        b[20].setOnAction(e -> {
            if(alreadyPressed[20] == 0) {
                backend.changeElement(1, 3, currentChar);
                b[20].setText(String.valueOf(backend.returnGame()[1][3]));
                checkGameOver();
                alreadyPressed[20] = 1;
            }
        });
        GridPane.setConstraints(b[20], 1, 3);

        b[21].setOnAction(e -> {
            if(alreadyPressed[21] == 0) {
                backend.changeElement(2, 3, currentChar);
                b[21].setText(String.valueOf(backend.returnGame()[2][3]));
                checkGameOver();
                alreadyPressed[21] = 1;
            }
        });
        GridPane.setConstraints(b[21], 2, 3);

        b[22].setOnAction(e -> {
            if(alreadyPressed[22] == 0) {
                backend.changeElement(3, 3, currentChar);
                b[22].setText(String.valueOf(backend.returnGame()[3][3]));
                checkGameOver();
                alreadyPressed[22] = 1;
            }
        });
        GridPane.setConstraints(b[22], 3, 3);

        b[23].setOnAction(e -> {
            if(alreadyPressed[23] == 0) {
                backend.changeElement(4, 3, currentChar);
                b[23].setText(String.valueOf(backend.returnGame()[4][3]));
                checkGameOver();
                alreadyPressed[23] = 1;
            }
        });
        GridPane.setConstraints(b[23], 4, 3);

        b[24].setOnAction(e -> {
            if(alreadyPressed[24] == 0) {
                backend.changeElement(5, 3, currentChar);
                b[24].setText(String.valueOf(backend.returnGame()[5][3]));
                checkGameOver();
                alreadyPressed[24] = 1;
            }
        });
        GridPane.setConstraints(b[24], 5, 3);

        // 5. row

        b[25].setOnAction(e -> {
            if(alreadyPressed[25] == 0) {
                backend.changeElement(0, 4, currentChar);
                b[25].setText(String.valueOf(backend.returnGame()[0][4]));
                checkGameOver();
                alreadyPressed[25] = 1;
            }
        });
        GridPane.setConstraints(b[25], 0, 4);

        b[26].setOnAction(e -> {
            if(alreadyPressed[26] == 0) {
                backend.changeElement(1, 4, currentChar);
                b[26].setText(String.valueOf(backend.returnGame()[1][4]));
                checkGameOver();
                alreadyPressed[26] = 1;
            }
        });
        GridPane.setConstraints(b[26], 1, 4);

        b[27].setOnAction(e -> {
            if(alreadyPressed[27] == 0) {
                backend.changeElement(2, 4, currentChar);
                b[27].setText(String.valueOf(backend.returnGame()[2][4]));
                checkGameOver();
                alreadyPressed[27] = 1;
            }
        });
        GridPane.setConstraints(b[27], 2, 4);

        b[28].setOnAction(e -> {
            if(alreadyPressed[28] == 0) {
                backend.changeElement(3, 4, currentChar);
                b[28].setText(String.valueOf(backend.returnGame()[3][4]));
                checkGameOver();
                alreadyPressed[28] = 1;
            }
        });
        GridPane.setConstraints(b[28], 3, 4);

        b[29].setOnAction(e -> {
            if(alreadyPressed[29] == 0) {
                backend.changeElement(4, 4, currentChar);
                b[29].setText(String.valueOf(backend.returnGame()[4][4]));
                checkGameOver();
                alreadyPressed[29] = 1;
            }
        });
        GridPane.setConstraints(b[29], 4, 4);

        b[30].setOnAction(e -> {
            if(alreadyPressed[30] == 0) {
                backend.changeElement(5, 4, currentChar);
                b[30].setText(String.valueOf(backend.returnGame()[5][4]));
                checkGameOver();
                alreadyPressed[30] = 1;
            }
        });
        GridPane.setConstraints(b[30], 5, 4);

        // 6. row

        b[31].setOnAction(e -> {
            if(alreadyPressed[31] == 0) {
                backend.changeElement(0, 5, currentChar);
                b[31].setText(String.valueOf(backend.returnGame()[0][5]));
                checkGameOver();
                alreadyPressed[31] = 1;
            }
        });
        GridPane.setConstraints(b[31], 0, 5);

        b[32].setOnAction(e -> {
            if(alreadyPressed[32] == 0) {
                backend.changeElement(1, 5, currentChar);
                b[32].setText(String.valueOf(backend.returnGame()[1][5]));
                checkGameOver();
                alreadyPressed[32] = 1;
            }
        });
        GridPane.setConstraints(b[32], 1, 5);

        b[33].setOnAction(e -> {
            if(alreadyPressed[33] == 0) {
                backend.changeElement(2, 5, currentChar);
                b[33].setText(String.valueOf(backend.returnGame()[2][5]));
                checkGameOver();
                alreadyPressed[33] = 1;
            }
        });
        GridPane.setConstraints(b[33], 2, 5);

        b[34].setOnAction(e -> {
            if(alreadyPressed[34] == 0) {
                backend.changeElement(3, 5, currentChar);
                b[34].setText(String.valueOf(backend.returnGame()[3][5]));
                checkGameOver();
                alreadyPressed[34] = 1;
            }
        });
        GridPane.setConstraints(b[34], 3, 5);

        b[35].setOnAction(e -> {
            if(alreadyPressed[35] == 0) {
                backend.changeElement(4, 5, currentChar);
                b[35].setText(String.valueOf(backend.returnGame()[4][5]));
                checkGameOver();
                alreadyPressed[35] = 1;
            }
        });
        GridPane.setConstraints(b[35], 4, 5);

        b[36].setOnAction(e -> {
            if(alreadyPressed[36] == 0) {
                backend.changeElement(5, 5, currentChar);
                b[36].setText(String.valueOf(backend.returnGame()[5][5]));
                checkGameOver();
                alreadyPressed[36] = 1;
            }
        });
        GridPane.setConstraints(b[36], 5, 5);


        grid.getChildren().addAll(b[1], b[2], b[3], b[4], b[5], b[6],
                b[7], b[8], b[9], b[10], b[11], b[12],
                b[13], b[14], b[15], b[16], b[17], b[18],
                b[19], b[20], b[21], b[22], b[23], b[24],
                b[25], b[26], b[27], b[28], b[29], b[30],
                b[31], b[32], b[33], b[34], b[35], b[36]);
    }

}