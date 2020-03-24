package controller;

import entity.Uzytkownik;
import java_applications.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.Alerts;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import java.io.IOException;
import java.util.Objects;

import static java.lang.Integer.parseInt;

/**
 * Klasa rozpoczynająca działanie okienka umożliwiającego zmianę danej użytkownika.
 */

public class ControllerZmiana {

    private boolean Acc = false;
    private static Stage stage;

    @FXML
    private Button button;

    @FXML
    private TextField add;

    @FXML
    private Label errorLabel;

    @FXML
    private Label Label;

    @FXML
    private void initialize() {
        if(ControllerInformacje.getwhat().equals("email"))
        {
            Label.setText("Podaj email:");
            button.setText("Zatwierdź nowy email");
        }
        if(ControllerInformacje.getwhat().equals("telefon"))
        {
            Label.setText("Podaj telefon:");
            button.setText("Zatwierdź nowy telefon");
        }
        if(ControllerInformacje.getwhat().equals("adres"))
        {
            Label.setText("Podaj adres:");
            button.setText("Zatwierdź nowy adres");
        }
        if(ControllerInformacje.getwhat().equals("haslo"))
        {
            Label.setText("Podaj haslo:");
            button.setText("Zatwierdź nowe hasło");
        }
        add.textProperty().addListener((observableValue, oldValue, newValue) -> {
            Acc = false;
        });
    }

    /**
     * Metoda obługuje kliknięcie przycisku zmieniającego daną.
     */
    @FXML
    private void handlebut() {
        dataCheck();
        if (Acc) {
            if(ControllerInformacje.getwhat().equals("email"))
                Main.getLoggedUser().setEMail(add.getText().trim());
            if(ControllerInformacje.getwhat().equals("telefon"))
            {
                Integer tmpTel = null;
                if(!add.getText().isEmpty())
                {
                    tmpTel = parseInt(add.getText());
                }
                Main.getLoggedUser().setTelefon(tmpTel);
            }
            if(ControllerInformacje.getwhat().equals("adres"))
            {
                String tmpAdr = null;
                if(!add.getText().isEmpty())
                {
                    tmpAdr = add.getText();
                }
                Main.getLoggedUser().setAdres(tmpAdr);
            }
            if(ControllerInformacje.getwhat().equals("haslo"))
                Main.getLoggedUser().setHaslo(add.getText().trim());
            try {
                Main.getEntityManager().getTransaction().begin();
                Main.getEntityManager().persist(Main.getLoggedUser());
                Main.getEntityManager().flush();
                Main.getEntityManager().getTransaction().commit();
            } catch (Exception e) {
                Main.getEntityManager().getTransaction().rollback();
                e.printStackTrace();
            }
            if(ControllerInformacje.getwhat().equals("email")) Alerts.EmailChangeCompleted().showAndWait();
            if(ControllerInformacje.getwhat().equals("telefon")) Alerts.TelefonChangeCompleted().showAndWait();
            if(ControllerInformacje.getwhat().equals("adres")) Alerts.AdresChangeCompleted().showAndWait();
            if(ControllerInformacje.getwhat().equals("haslo")) Alerts.HasloChangeCompleted().showAndWait();
            handleCof();
        }
    }

    /**
     * Metoda sprawdza poprawność wpisanych danych.
     */
    private void dataCheck() {
        String errorBorder = "-fx-border-color: red;\n";
        String acceptedBorder = "-fx-border-color: green;\n";
        EntityManager em = Main.getEntityManager();
        if(ControllerInformacje.getwhat().equals("email")) {
            if (!add.getText().trim().equals("")) {
                if (isUnique(em, add.getText().trim())) {
                    errorLabel.setVisible(false);
                    add.setStyle(acceptedBorder);
                    Acc = true;
                } else {
                    add.setStyle(errorBorder);
                    errorLabel.setText("Email został zajęty!");
                    errorLabel.setVisible(true);
                }
            }
            else {
                add.setStyle(errorBorder);
                errorLabel.setText("Pole nie może być puste!");
                errorLabel.setVisible(true);
            }
        }
        if(ControllerInformacje.getwhat().equals("telefon")){
            if (!add.getText().trim().equals("")) {
                if (add.getText().trim().length() < 9) {
                    add.setStyle(errorBorder);
                    errorLabel.setText("Za mało cyfr!");
                    errorLabel.setVisible(true);
                }
                if (add.getText().trim().length() > 9){
                    add.setStyle(errorBorder);
                    errorLabel.setText("Za dużo cyfr!");
                    errorLabel.setVisible(true);
                }
                if (add.getText().trim().length() == 9){
                    errorLabel.setVisible(false);
                    add.setStyle(acceptedBorder);
                    Acc = true;
                }
            }
            else {
                errorLabel.setVisible(false);
                add.setStyle(acceptedBorder);
                Acc = true;
            }
        }
        if(ControllerInformacje.getwhat().equals("adres")){
            if (!add.getText().trim().equals("")) {
                if (add.getText().trim().length() >= 10) {
                    errorLabel.setVisible(false);
                    add.setStyle(acceptedBorder);
                    Acc = true;
                }
                else {
                    add.setStyle(errorBorder);
                    errorLabel.setText(" Adres musi mieć co najmniej 10 liter!");
                    errorLabel.setVisible(true);
                }
            }
            else {
                errorLabel.setVisible(false);
                add.setStyle(acceptedBorder);
                Acc = true;
            }

        }
        if(ControllerInformacje.getwhat().equals("haslo")){
            if (!add.getText().trim().equals("")) {
                if (add.getText().trim().length() >= 3) {
                    errorLabel.setVisible(false);
                    add.setStyle(acceptedBorder);
                    Acc = true;
                }
                else {
                    add.setStyle(errorBorder);
                    errorLabel.setText("Hasło musi mieć przynajmniej 3 litery!");
                    errorLabel.setVisible(true);
                }
            }
            else {
                add.setStyle(errorBorder);
                errorLabel.setText("Pole nie może być puste!");
                errorLabel.setVisible(true);
            }
        }
    }

    /**
     * Metoda sprawdza czy email jest unikalny.
     */
    private boolean isUnique(EntityManager em, String value) {
        try {
            TypedQuery<Uzytkownik> typedQuery = em.createQuery("FROM Uzytkownik WHERE email = ?1", Uzytkownik.class);
            typedQuery.setParameter(1, value);
            Uzytkownik result = typedQuery.getSingleResult();
        } catch (NoResultException noResult) {
            return true;
        }
        return false;
    }

    /**
     * Metoda obługuje cofnięcie się do poprzedniego okna.
     */
    @FXML
    private void handleCof()
    {
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/InformacjeWindow.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.getWindow().setScene(scene);
    }
}
