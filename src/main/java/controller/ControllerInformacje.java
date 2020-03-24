package controller;

import java_applications.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.Objects;

/**
 * Klasa rozpoczynająca działanie okienka z informacjami o użytkowniku.
 */

public class ControllerInformacje {

    private static String what;

    @FXML
    private Label uzyNicLabel, uzyImiLabel, uzyNazLabel, uzyEmaLabel, uzyTelLabel, uzyAdrLabel;


    @FXML
    private void initialize()
    {
        uzyNicLabel.setText(Main.getLoggedUser().getNick());
        uzyImiLabel.setText(Main.getLoggedUser().getImie());
        uzyNazLabel.setText(Main.getLoggedUser().getNazwisko());
        uzyEmaLabel.setText(Main.getLoggedUser().getEMail());
        uzyTelLabel.setText(Integer.toString(Main.getLoggedUser().getTelefon()));
        uzyAdrLabel.setText(Main.getLoggedUser().getAdres());
    }

    /**
     * Metoda obługuje cofnięcie do poprzedniego okienka.
     */
    @FXML
    private void handleCof()
    {
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/MenuWindow.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.getWindow().setScene(scene);
    }

    /**
     * Metoda obługuje zmianę okna na umożliwiającę zmianę emaila użytkownika.
     */
    @FXML
    private void handleEma()
    {
        Scene scene = null;
        try {
            setwhat("email");
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/ZmianaWindow.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setwhat("email");
        Main.getWindow().setScene(scene);
    }

    /**
     * Metoda obługuje zmianę okna na umożliwiającę zmianę numeru telefonu użytkownika.
     */
    @FXML
    private void handleTel()
    {
        Scene scene = null;
        try {
            setwhat("telefon");
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/ZmianaWindow.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setwhat("telefon");
        Main.getWindow().setScene(scene);
    }

    /**
     * Metoda obługuje zmianę okna na umożliwiającę zmianę adresu użytkownika.
     */
    @FXML
    private void handleAdr()
    {
        Scene scene = null;
        try {
            setwhat("adres");
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/ZmianaWindow.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setwhat("adres");
        Main.getWindow().setScene(scene);
    }

    /**
     * Metoda obługuje zmianę okna na umożliwiającę zmianę hasła użytkownika.
     */
    @FXML
    private void handleHas()
    {
        Scene scene = null;
        try {
            setwhat("haslo");
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/ZmianaWindow.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setwhat("haslo");
        Main.getWindow().setScene(scene);
    }

    public static String getwhat() {
        return what;
    }
    public static void setwhat(String what) { ControllerInformacje.what = what; }
}
