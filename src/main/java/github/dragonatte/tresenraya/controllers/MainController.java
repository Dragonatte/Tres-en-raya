package github.dragonatte.tresenraya.controllers;

import github.dragonatte.tresenraya.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Arrays;

public class MainController {
    //#region Properties
    private boolean turn;
    //#endregion

    //#region final Properties
    private final String[] board;
    //#endregion
    
    @FXML
    public GridPane gridPane;

    /**
     * Constructor
     */
    public MainController() {
        this.turn = true;
        board = new String[9];
        Arrays.fill(board, "");
    }

    /**
     * Initializes the controller class.
     */


    @FXML
    public void initialize() {
        Logger logger = Logger.getLogger(MainController.class.getName());

        if (MainApp.CUSTOM_FONT != null) {
            gridPane.getChildren().forEach(node -> {
                Button btn = (Button) node;
                btn.getStyleClass().add("btn");
                btn.setDisable(false);
                btn.setFont(MainApp.CUSTOM_FONT);
            });
        } else {
            logger.log(Level.SEVERE, "La fuente personalizada no se pudo cargar correctamente.");
        }
    }

    /**
     * Handle button click event
     * @param event ActionEvent
     */
    @FXML
    public void handleBtnClick(ActionEvent event) {
        Button btn = (Button) event.getSource();
        btn.setText(turn? "X" : "O");
        btn.getStyleClass().removeAll();
        btn.getStyleClass().add(turn? "btnX" : "btnO");
        btn.applyCss();

        gridPane.getChildren().forEach(node -> {
            int i = gridPane.getChildren().indexOf(node);
            Button ibtn = (Button) node;
            BOARD[i] = ibtn.getText();
        });

        btn.setDisable(true);

        checkWin(turn? 'X' : 'O');
        if (Arrays.stream(board).noneMatch(String::isEmpty)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("TRES EN RAYA");
            alert.setHeaderText("¡EMPATE!");
            alert.setContentText("¡VUELVE A INTENTARLO!");
            alert.showAndWait();
            reset();
        }
        turn = !turn;
    }

    /**
     * Check if someone wins
     * @param c char
     */
    private void checkWin(char c) {
        String cStr = String.valueOf(c);
        if (checkLine(0, 1, 2, cStr) || checkLine(3, 4, 5, cStr) || checkLine(6, 7, 8, cStr) ||
            checkLine(0, 3, 6, cStr) || checkLine(1, 4, 7, cStr) || checkLine(2, 5, 8, cStr) ||
            checkLine(0, 4, 8, cStr) || checkLine(2, 4, 6, cStr)
        ) alertWin(c);
        
    }

    /**
     * Check if a line is complete
     * @param a int
     * @param b int
     * @param c int
     * @param symbol String
     * @return boolean
     */
    private boolean checkLine(int a, int b, int c, String symbol) {
        return BOARD[a].equals(symbol) && BOARD[b].equals(symbol) && BOARD[c].equals(symbol);
    }

    /**
     * Alert win
     * @param c char
     */
    private void alertWin(char c) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("TRES EN RAYA");
        alert.setHeaderText("¡GANA: " + c + "!");
        alert.setContentText("¡ENHORABUENA!");
        alert.showAndWait();
        reset();
    }

    /**
     * Reset the game
     */
    public void reset() {
        gridPane.getChildren().forEach(node -> {
            Button btn = (Button) node;
            btn.getStyleClass().add("btn");
            btn.applyCss();
            btn.setText("");
            btn.setDisable(false);
        });
        Arrays.fill(BOARD, "");
    }
}