package controller;

import entity.Kategoria;
import entity.Produkt;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerKategoria extends Control implements Initializable {

    public TextField addKat;
    public TableView<Kategoria> tab2;
    public TableColumn<Kategoria, Integer> kolId;
    public TableColumn<Kategoria, String> kolKat;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit");
    EntityManager entityManager = emf.createEntityManager();

    List<Kategoria> kat = entityManager.createQuery("from Kategoria").getResultList();
    ObservableList<Kategoria> okat = FXCollections.observableArrayList(kat);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kolId.setCellValueFactory(new PropertyValueFactory("id"));
        kolKat.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTyp()));

        tab2.setItems(okat);

    }

    public void handleKat(ActionEvent actionEvent){
        if(!addKat.getText().isEmpty())
        {
            Kategoria tmpKategoria = new Kategoria(addKat.getText());

            entityManager.getTransaction().begin();
            entityManager.persist(tmpKategoria);
            entityManager.flush();
            entityManager.getTransaction().commit();

            kat = entityManager.createQuery("from Kategoria").getResultList();
            okat = FXCollections.observableArrayList(kat);

            tab2.setItems(okat);
            tab2.getSortOrder().add(kolId);

            addKat.clear();
        }

    }
}
