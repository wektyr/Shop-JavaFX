package controller;

import java_applications.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.Objects;

/**
 * Klasa rozpoczynająca działanie okienka menu.
 */

public class ControllerMenu {

    @FXML
    private void initialize() {}

    /**
     * Metoda obługuje zmianę okna na historię transakcji.
     */
    @FXML
    private void handleHis(){
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/TransakcjeUzyWindow.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.getWindow().setScene(scene);
    }

    /**
     * Metoda obługuje zmianę okna na informacje o użytkowniku.
     */
    @FXML
    private void handleInf(){
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/InformacjeWindow.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.getWindow().setScene(scene);
    }

    /**
     * Metoda obługuje zmianę okna na okno umożliwiające zakupy.
     */
    @FXML
    private void handlePrz(){
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/KupnoWindow.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.getWindow().setScene(scene);
    }

    /**
     * Metoda obługuje zmianę okna na okno z logowaniem.
     */
    @FXML
    private void handleWyl(){
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/LogujWindow.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.getWindow().setScene(scene);
    }
}
