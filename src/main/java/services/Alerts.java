package services;

import controller.ControllerInformacje;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Usługa obługująca alerty.
 */
public class Alerts {
    public static Stage getStage(String stageFXML, int width, int height, String title) {
        Parent root;
        try {
            Stage stage = new Stage();
            root = FXMLLoader.load(Objects.requireNonNull(Alerts.class.getClassLoader().getResource(stageFXML)));
            Scene scene = new Scene(root, width, height);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); /* aby bylo glownym okienkiem programu */
            return stage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Alert EmailChangeCompleted() {
        return alert("Informacja", "Zmiana emaila udana","Twoje konto zostało zaktualizowane.");
    }

    public static Alert TelefonChangeCompleted() {
        return alert("Informacja", "Zmiana numeru telefonu udana","Twoje konto zostało zaktualizowane.");
    }

    public static Alert AdresChangeCompleted() {
        return alert("Informacja", "Zmiana adresu udana", "Twoje konto zostało zaktualizowane.");
    }

    public static Alert HasloChangeCompleted() {
        return alert("Informacja", "Zmiana hasła udana", "Twoje konto zostało zaktualizowane.");
    }

    public static Alert TransakcjaCompleted() {
        return alert("Informacja", "Transakjca udana", "W historii transakcji można sprawdzić status transakcji.");
    }

    public static Alert registrationCompleted() {
        return alert("Informacja", "Rejestracja udana", "Twoje konto zostało założone.\nTeraz możesz się zalogować.");
    }

    private static Alert alert(String title, String headerText, String contentText)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setGraphic(null);
        alert.setContentText(contentText);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Alerts.class.getResource("/css/myDialog.css").toExternalForm());
        return alert;
    }
}
