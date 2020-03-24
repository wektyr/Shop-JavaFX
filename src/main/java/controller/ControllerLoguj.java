package controller;

import entity.Uzytkownik;
import java_applications.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javax.persistence.*;
import java.io.IOException;
import java.util.Objects;
import javafx.stage.Stage;

/**
 * Klasa rozpoczynająca działanie okienka z logowaniem.
 */

public class ControllerLoguj {

    private Uzytkownik user;
    private boolean logAcc = false;
    private boolean hasAcc = false;
    private static Stage stage;

    @FXML
    private TextField addLog, addHas;

    @FXML
    private Label errorLogLabel,  errorHasLabel;

    @FXML
    private void initialize() {
        addLog.textProperty().addListener((observableValue, oldValue, newValue) -> {
            logAcc = false;
            hasAcc = false;
        });
        addHas.textProperty().addListener((observableValue, oldValue, newValue) -> {
            hasAcc = false;
            logAcc = false;
        });
    }

    /**
     * Metoda obługuje zalogowanie się.
     */
    @FXML
    private void handleZal(){
        dataCheck();
        if (logAcc && hasAcc) {
            Main.setLoggedUser(user);
            Scene scene = null;
            try {
                scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/MenuWindow.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Main.getWindow().setScene(scene);
        }
    }

    /**
     * Metoda obługuje rejestracje.
     */
    @FXML
    private void handleZar() {
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/RejestrujWindow.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.getWindow().setScene(scene);
    }

    /**
     * Metoda sprawdza wprowadzone dane.
     */
    private void dataCheck() {
        String errorBorder = "-fx-border-color: red;\n";
        String acceptedBorder = "-fx-border-color: green;\n";
        if (!addLog.getText().trim().equals("")) {
            if (accountExists(addLog.getText().trim())) {
                errorLogLabel.setVisible(false);
                addLog.setStyle(acceptedBorder);
                logAcc = true;
            } else {
                addLog.setStyle(errorBorder);
                errorLogLabel.setText("Login nie istnieje!");
                errorLogLabel.setVisible(true);
            }
        } else {
            addLog.setStyle(errorBorder);
            errorLogLabel.setText("Pole nie może być puste!");
            errorLogLabel.setVisible(true);
        }
        if (!addHas.getText().trim().equals("")) {
            if (!addLog.getText().trim().equals("") && user != null) {
                if (addHas.getText().trim().equals(user.getHaslo())) {
                    errorHasLabel.setVisible(false);
                    addHas.setStyle(acceptedBorder);
                    hasAcc = true;
                } else {
                    addHas.setStyle(errorBorder);
                    errorHasLabel.setText("Złe hasło!");
                    errorHasLabel.setVisible(true);
                }
            } else {
                addHas.setStyle(errorBorder);
                errorHasLabel.setText("Zły login!");
                errorHasLabel.setVisible(true);
            }
        } else {
            addHas.setStyle(errorBorder);
            errorHasLabel.setText("Pole nie może być puste!");
            errorHasLabel.setVisible(true);
        }
    }

    /**
     * Metoda sprawdza czy dane już są w bazie danych.
     */
    private boolean accountExists(String account) {
        try {
            TypedQuery<Uzytkownik> typedQuery = Main.getEntityManager().createQuery("FROM Uzytkownik WHERE nick = ?1", Uzytkownik.class);
            typedQuery.setParameter(1, account);
            user = typedQuery.getSingleResult();
        } catch (NoResultException noResult) {
            return false;
        }
        return true;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        ControllerLoguj.stage = stage;
    }

}
