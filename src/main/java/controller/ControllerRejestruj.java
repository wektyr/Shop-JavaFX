package controller;

import entity.Uzytkownik;
import java_applications.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
 * Klasa rozpoczynająca działanie okienka z rejestracją.
 */

public class ControllerRejestruj {
    private boolean nicAcc = false;
    private boolean hasAcc = false;
    private boolean emaAcc = false;
    private boolean imiAcc = false;
    private boolean nazAcc = false;
    private boolean telAcc = false;
    private boolean adrAcc = false;

    private static Stage stage;

    @FXML
    private TextField addNic, addHas, addImi, addNaz, addTel, addEma, addAdr;

    @FXML
    private Label errorNicLabel, errorHasLabel, errorEmaLabel, errorImiLabel, errorNazLabel, errorTelLabel, errorAdrLabel;

    @FXML
    private void initialize()
    {
        addNic.textProperty().addListener((observableValue, oldValue, newValue) -> {
            nicAcc = false;
            hasAcc = false;
            emaAcc = false;
            imiAcc = false;
            nazAcc = false;
            telAcc = false;
            adrAcc = false;
        });
        addHas.textProperty().addListener((observableValue, oldValue, newValue) -> {
            nicAcc = false;
            hasAcc = false;
            emaAcc = false;
            imiAcc = false;
            nazAcc = false;
            telAcc = false;
            adrAcc = false;
        });
        addImi.textProperty().addListener((observableValue, oldValue, newValue) -> {
            nicAcc = false;
            hasAcc = false;
            emaAcc = false;
            imiAcc = false;
            nazAcc = false;
            telAcc = false;
            adrAcc = false;
        });
        addNaz.textProperty().addListener((observableValue, oldValue, newValue) -> {
            nicAcc = false;
            hasAcc = false;
            emaAcc = false;
            imiAcc = false;
            nazAcc = false;
            telAcc = false;
            adrAcc = false;
        });
        addEma.textProperty().addListener((observableValue, oldValue, newValue) -> {
            nicAcc = false;
            hasAcc = false;
            emaAcc = false;
            imiAcc = false;
            nazAcc = false;
            telAcc = false;
            adrAcc = false;
        });
        addTel.textProperty().addListener((observableValue, oldValue, newValue) -> {
            nicAcc = false;
            hasAcc = false;
            emaAcc = false;
            imiAcc = false;
            nazAcc = false;
            telAcc = false;
            adrAcc = false;
        });
        addAdr.textProperty().addListener((observableValue, oldValue, newValue) -> {
            nicAcc = false;
            hasAcc = false;
            emaAcc = false;
            imiAcc = false;
            nazAcc = false;
            telAcc = false;
            adrAcc = false;
        });
    }

    /**
     * Metoda obługuje kliknięcie przycisku z rejestracją.
     */
    @FXML
    private void handleZar() {
        dataCheck();
        if (nicAcc && hasAcc && emaAcc && imiAcc && nazAcc && telAcc && adrAcc) {
            Integer tmpTel = null;
            String tmpAdr = null;
            Uzytkownik account = new Uzytkownik();
            if(!addTel.getText().isEmpty())
            {
                tmpTel = parseInt(addTel.getText());
            }
            if(!addAdr.getText().isEmpty())
            {
                tmpAdr = addAdr.getText();
            }
            account.setNick(addNic.getText().trim());
            account.setHaslo(addHas.getText().trim());
            account.setEMail(addEma.getText().trim());
            account.setImie(addImi.getText().trim());
            account.setNazwisko(addNaz.getText().trim());
            account.setTelefon(tmpTel);
            account.setAdres(tmpAdr);
            try {
                Main.getEntityManager().getTransaction().begin();
                Main.getEntityManager().persist(account);
                Main.getEntityManager().flush();
                Main.getEntityManager().getTransaction().commit();
            } catch (Exception e) {
                Main.getEntityManager().getTransaction().rollback();
                e.printStackTrace();
            }
            Alerts.registrationCompleted().showAndWait();
            handleCof();
        }
    }

    /**
     * Metoda obługuje cofnięcie się do poprzedniego okna.
     */
    @FXML
    private void handleCof()
    {
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/LogujWindow.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.getWindow().setScene(scene);
    }

    /**
     * Metoda sprawdza poprawność wprowadzonych danych.
     */
    private void dataCheck() {
        String errorBorder = "-fx-border-color: red;\n";
        String acceptedBorder = "-fx-border-color: green;\n";
        EntityManager em = Main.getEntityManager();
        if (!addNic.getText().trim().equals("")) {
            if (addNic.getText().trim().length() >= 3) {
                if (isUnique(em,"nick", addNic.getText().trim())) {
                    errorNicLabel.setVisible(false);
                    addNic.setStyle(acceptedBorder);
                    nicAcc = true;
                }
                else {
                    addNic.setStyle(errorBorder);
                    errorNicLabel.setText("Nick został zajęty!");
                    errorNicLabel.setVisible(true);
                }
            }
            else {
                addNic.setStyle(errorBorder);
                errorNicLabel.setText("Nick musi mieć przynajmniej 3 litery!");
                errorNicLabel.setVisible(true);
            }
        }
        else {
            addNic.setStyle(errorBorder);
            errorNicLabel.setText("Pole nie może być puste!");
            errorNicLabel.setVisible(true);
        }
        if (!addHas.getText().trim().equals("")) {
            if (addHas.getText().trim().length() >= 3) {
                errorHasLabel.setVisible(false);
                addHas.setStyle(acceptedBorder);
                hasAcc = true;
            }
            else {
                addHas.setStyle(errorBorder);
                errorHasLabel.setText("Hasło musi mieć przynajmniej 3 litery!");
                errorHasLabel.setVisible(true);
            }
        }
        else {
            addHas.setStyle(errorBorder);
            errorHasLabel.setText("Pole nie może być puste!");
            errorHasLabel.setVisible(true);
        }
        if (!addEma.getText().trim().equals("")) {
                if (isUnique(em, "email", addEma.getText().trim())) {
                    errorEmaLabel.setVisible(false);
                    addEma.setStyle(acceptedBorder);
                    emaAcc = true;
                }
                else {
                    addEma.setStyle(errorBorder);
                    errorEmaLabel.setText("Email został zajęty!");
                    errorEmaLabel.setVisible(true);
                }
        }
        else {
            addEma.setStyle(errorBorder);
            errorEmaLabel.setText("Pole nie może być puste!");
            errorEmaLabel.setVisible(true);
        }
        if (!addImi.getText().trim().equals("")) {
            errorImiLabel.setVisible(false);
            addImi.setStyle(acceptedBorder);
            imiAcc = true;
        }
        else {
            addImi.setStyle(errorBorder);
            errorImiLabel.setText("Pole nie może być puste!");
            errorImiLabel.setVisible(true);
        }
        if (!addNaz.getText().trim().equals("")) {
            errorNazLabel.setVisible(false);
            addNaz.setStyle(acceptedBorder);
            nazAcc = true;
        }
        else {
            addNaz.setStyle(errorBorder);
            errorNazLabel.setText("Pole nie może być puste!");
            errorNazLabel.setVisible(true);
        }
        if (!addTel.getText().trim().equals("")) {
            if (addTel.getText().trim().length() < 9) {
                addTel.setStyle(errorBorder);
                errorTelLabel.setText("Za mało cyfr!");
                errorTelLabel.setVisible(true);
            }
            if (addTel.getText().trim().length() > 9){
                addTel.setStyle(errorBorder);
                errorTelLabel.setText("Za dużo cyfr!");
                errorTelLabel.setVisible(true);
            }
            if (addTel.getText().trim().length() == 9){
                errorTelLabel.setVisible(false);
                addTel.setStyle(acceptedBorder);
                telAcc = true;
            }
        }
        else {
            errorTelLabel.setVisible(false);
            addTel.setStyle(acceptedBorder);
            telAcc = true;
        }
        if (!addAdr.getText().trim().equals("")) {
            if (addAdr.getText().trim().length() >= 10) {
                errorAdrLabel.setVisible(false);
                addAdr.setStyle(acceptedBorder);
                adrAcc = true;
            }
            else {
                addAdr.setStyle(errorBorder);
                errorAdrLabel.setText(" Adres musi mieć co najmniej 10 liter!");
                errorAdrLabel.setVisible(true);
            }
        }
        else {
            errorAdrLabel.setVisible(false);
            addAdr.setStyle(acceptedBorder);
            adrAcc = true;
        }
    }

    /**
     * Metoda sprawdza czy dane nie są już zajęte.
     */
    private boolean isUnique(EntityManager em, String what,  String value) {
        try {
            TypedQuery<Uzytkownik> typedQuery;
            if (what.equals("nick")) {
                typedQuery = em.createQuery("FROM Uzytkownik WHERE nick = ?1", Uzytkownik.class);
            }
            else if (what.equals("email")) {
                typedQuery = em.createQuery("FROM Uzytkownik WHERE email = ?1", Uzytkownik.class);
            }
            else {
                return false;
            }
            typedQuery.setParameter(1, value);
            Uzytkownik result = typedQuery.getSingleResult();
        } catch (NoResultException noResult) {
            return true;
        }
        return false;
    }

}
