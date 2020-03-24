package controller;

import entity.Kategoria;
import entity.Produkt;
import entity.Transakcje;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerTransakcje extends Control implements Initializable {

    public TableView<Transakcje> tab2;
    public TableColumn<Transakcje, Integer> kolId;
    public TableColumn<Transakcje, String> kolSta;
    public TableColumn<Transakcje, Integer> kolKwo;
    public TextField addSta;
    public static Transakcje wybranyTransakcja;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit");
    EntityManager entityManager = emf.createEntityManager();

    List<Transakcje> tra = entityManager.createQuery("from Transakcje").getResultList();
    ObservableList<Transakcje> otra = FXCollections.observableArrayList(tra);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kolId.setCellValueFactory(new PropertyValueFactory("id"));
        kolSta.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStan()));
        kolKwo.setCellValueFactory(new PropertyValueFactory("kwota"));

        tab2.setItems(otra);

    }

    public void handleSta(ActionEvent actionEvent) {
        if(!addSta.getText().isEmpty())
        {
            if(tab2.getSelectionModel().getSelectedItem() != null)
            {
                entityManager.getTransaction().begin();
                tab2.getSelectionModel().getSelectedItem().setStan(addSta.getText());
                entityManager.flush();
                entityManager.getTransaction().commit();

                tra = entityManager.createQuery("from Transakcje").getResultList();
                otra = FXCollections.observableArrayList(tra);

                tab2.setItems(otra);
                tab2.getSortOrder().add(kolId);
                addSta.clear();
            }
        }
    }

    public void handlePro(ActionEvent actionEvent) {
        wybranyTransakcja = tab2.getSelectionModel().getSelectedItem();
        new Thread(() -> {
            Platform.runLater(() -> {
                if(wybranyTransakcja != null)
                {
                    try {
                        Parent root5 = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ShowProWindow.fxml"));
                        Stage stage = new Stage();
                        stage.setTitle("Produkty w transakcji");
                        stage.setScene(new Scene(root5));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }).start();
    }

    public Transakcje getWybranyTransakcja(){return wybranyTransakcja;}
}