package controller;

import entity.Kategoria;
import entity.Produkt;
import entity.Uzytkownik;
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

import static java.lang.Integer.parseInt;

public class ControllerUzytkownik extends Control implements Initializable {

    public TextField addNic, addHas, addImi, addNaz, addEma, addTel, addAdr;
    public TableView<Uzytkownik> tab2;
    public TableColumn<Uzytkownik, Integer> kolId;
    public TableColumn<Uzytkownik, String> kolNic;
    public TableColumn<Uzytkownik, String> kolImi;
    public TableColumn<Uzytkownik, String> kolNaz;
    public TableColumn<Uzytkownik, String> kolEma;
    public TableColumn<Uzytkownik, Integer> kolTel;
    public TableColumn<Uzytkownik, String> kolAdr;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit");
    EntityManager entityManager = emf.createEntityManager();

    List<Uzytkownik> uzy = entityManager.createQuery("from Uzytkownik").getResultList();
    ObservableList<Uzytkownik> ouzy = FXCollections.observableArrayList(uzy);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kolId.setCellValueFactory(new PropertyValueFactory("id"));
        kolNic.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNick()));
        kolImi.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getImie()));
        kolNaz.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNazwisko()));
        kolEma.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEMail()));
        kolTel.setCellValueFactory(new PropertyValueFactory("telefon"));
        kolAdr.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getAdres()));

        tab2.setItems(ouzy);

    }
    public void handleUzy(ActionEvent actionEvent){
        if(!addNic.getText().isEmpty() && !addHas.getText().isEmpty() && !addImi.getText().isEmpty() && !addNaz.getText().isEmpty() && !addEma.getText().isEmpty())
        {
            Integer tmpTel = null;
            String tmpAdr = null;
            if(!addTel.getText().isEmpty())
            {
                tmpTel = parseInt(addTel.getText());
            }
            if(!addAdr.getText().isEmpty())
            {
                tmpAdr = addAdr.getText();
            }
            Uzytkownik tmpUzytkownik = new Uzytkownik(addNic.getText(), addHas.getText(), addImi.getText(), addNaz.getText(), addEma.getText(), tmpTel, tmpAdr);
            entityManager.getTransaction().begin();
            entityManager.persist(tmpUzytkownik);
            entityManager.flush();
            entityManager.getTransaction().commit();

            uzy = entityManager.createQuery("from Uzytkownik").getResultList();
            ouzy = FXCollections.observableArrayList(uzy);

            tab2.setItems(ouzy);
            tab2.getSortOrder().add(kolId);

            addNic.clear();
            addHas.clear();
            addImi.clear();
            addNaz.clear();
            addEma.clear();
            addTel.clear();
            addAdr.clear();
        }

    }
}
