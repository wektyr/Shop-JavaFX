package controller;

import entity.Kategoria;
import entity.Produkt;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerStart extends Control implements Initializable {

    public Button buttonUzy, buttonAddPro, buttonRemPro, buttonKat, buttonTra, buttonAddIlo;
    public TextField addNazwa, addMarka, addIlosc, addCena, iloscc;
    public ChoiceBox checkKategoria;
    public Label iloscProduktow;
    public Produkt tmp;

    public TableView<Produkt> tab;
    public TableColumn<Produkt, Integer> kolId;
    public TableColumn<Produkt, String> kolNaz;
    public TableColumn<Produkt, String> kolKat;
    public TableColumn<Produkt, String> kolMar;
    public TableColumn<Produkt, Integer> kolIlo;
    public TableColumn<Produkt, Integer> kolCen;
    public TableColumn<Produkt, Boolean> kolCzy;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit");
    EntityManager entityManager = emf.createEntityManager();

    List<Kategoria> kat = entityManager.createQuery("from Kategoria").getResultList();
    ObservableList<Kategoria> okat = FXCollections.observableArrayList(kat);

    List<Produkt> pro = entityManager.createQuery("from Produkt").getResultList();
    ObservableList<Produkt> opro = FXCollections.observableArrayList(pro);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kolId.setCellValueFactory(new PropertyValueFactory("id"));
        kolNaz.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNazwa()));
        kolMar.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMarka()));
        kolKat.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTyp()));
        kolIlo.setCellValueFactory(new PropertyValueFactory("ilosc"));
        kolCen.setCellValueFactory(new PropertyValueFactory("cena"));
        kolCzy.setCellValueFactory(new PropertyValueFactory("czy_dostepny"));

        tab.setItems(opro);
        tab.getSortOrder().add(kolId);

        BigDecimal sumaPro = (BigDecimal) entityManager.createNativeQuery(
                "SELECT ilosc_produktow FROM DUAL").getSingleResult();

        iloscProduktow.setText("Suma Produktow: " + sumaPro.toString());

        for(Kategoria tmp : okat)
        {
            checkKategoria.getItems().add(tmp.getTyp());
        }
    }

    public void handleUzy(ActionEvent actionEvent) {

        new Thread(() -> {
            Platform.runLater(() -> {
                try {
                    Parent root2 = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/UzytkownikWindow.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Uzytkownicy");
                    stage.setScene(new Scene(root2));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }

    public void handleTra(ActionEvent actionEvent) {
        new Thread(() -> {
            Platform.runLater(() -> {
                try {
                    Parent root3 = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/TransakcjeWindow.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Transakcje");
                    stage.setScene(new Scene(root3));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }
    public void handleKat(ActionEvent actionEvent) {
        new Thread(() -> {
            Platform.runLater(() -> {
                try {
                    Parent root4 = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/KategoriaWindow.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Kategorie");
                    stage.setScene(new Scene(root4));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }

    public void handleKup(ActionEvent actionEvent) {
        new Thread(() -> {
            Platform.runLater(() -> {
                try {
                    Parent root4 = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/KupnoWindow.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Kupowanie");
                    stage.setScene(new Scene(root4));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }).start();
    }

    public void handleAddIlo(ActionEvent actionEvent) {
        if(!iloscc.getText().isEmpty())
        {
            if(tab.getSelectionModel().getSelectedItem() != null)
            {
                entityManager.getTransaction().begin();
                tab.getSelectionModel().getSelectedItem().setIlosc(Integer.parseInt(iloscc.getText())+tab.getSelectionModel().getSelectedItem().getIlosc());
                entityManager.flush();
                entityManager.getTransaction().commit();

                pro = entityManager.createQuery("from Produkt").getResultList();
                opro = FXCollections.observableArrayList(pro);

                tab.setItems(opro);
                tab.getSortOrder().add(kolIlo);
                iloscc.clear();
            }
        }
    }

    public void handleAddPro(ActionEvent actionEvent) {
        if(!addNazwa.getText().isEmpty() && !addMarka.getText().isEmpty() && !addCena.getText().isEmpty() && !addIlosc.getText().isEmpty())
        {
            Produkt tmpProdukt = new Produkt(addNazwa.getText(), addMarka.getText(), Integer.parseInt(addCena.getText()), Integer.parseInt(addIlosc.getText()));

            entityManager.getTransaction().begin();

            Kategoria tmpKat = new Kategoria();
            for(Kategoria tmp : kat)
            {
                if (tmp.getTyp().equals(checkKategoria.getValue().toString()))
                {
                    tmpKat = tmp;
                    break;
                }
            }
            tmpProdukt.setTyp(checkKategoria.getValue().toString());
            tmpProdukt.setKategoriaId(tmpKat);
            entityManager.persist(tmpProdukt);
            entityManager.flush();
            entityManager.getTransaction().commit();

            pro = entityManager.createQuery("from Produkt").getResultList();
            opro = FXCollections.observableArrayList(pro);

            tab.setItems(opro);
            tab.getSortOrder().add(kolId);

            addNazwa.clear();
            addMarka.clear();
            addIlosc.clear();
            addCena.clear();
        }
    }

    public void handleRemPro(ActionEvent actionEvent) {
        if(tab.getSelectionModel().getSelectedItem() != null)
        {
            entityManager.getTransaction().begin();
            entityManager.remove(tab.getSelectionModel().getSelectedItem());
            entityManager.getTransaction().commit();
            tab.getItems().removeAll(tab.getSelectionModel().getSelectedItem());
        }
    }
}
