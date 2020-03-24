package controller;

import entity.Transakcje;
import java_applications.Main;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Klasa rozpoczynająca działanie okienka pokazującego transakcje użytkownika.
 */
public class ControllerTransakcjeUzy {
    public TableView<Transakcje> tab2;
    public TableColumn<Transakcje, Integer> kolId;
    public TableColumn<Transakcje, String> kolSta;
    public TableColumn<Transakcje, Integer> kolKwo;
    private static Transakcje wybranyTransakcja;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit");
    EntityManager entityManager = emf.createEntityManager();

    List<Transakcje> tra = Main.getEntityManager().createQuery("from Transakcje ").getResultList();
    ObservableList<Transakcje> otra = FXCollections.observableArrayList(tra);

    @FXML
    private void initialize() {
        kolId.setCellValueFactory(new PropertyValueFactory("id"));
        kolSta.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStan()));
        kolKwo.setCellValueFactory(new PropertyValueFactory("kwota"));

        tab2.setItems(otra);
        if(Main.getLoggedUser().getTransakcjes() != null) {
            tab2.getItems().setAll(Main.getLoggedUser().getTransakcjes());
        }
    }

    /**
     * Metoda obługuje kliknięcie przycisku z pokazującego produkty w ramach transakcji.
     */
    @FXML
    private void handlePro() {
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

    /**
     * Metoda obługuje cofnięcie się do poprzedniego okna.
     */
    @FXML
    private void handleCof(){
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/MenuWindow.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.getWindow().setScene(scene);
    }

    public Transakcje getWybranyTransakcja(){return wybranyTransakcja;}
}
