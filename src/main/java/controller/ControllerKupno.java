package controller;

import entity.Kategoria;
import entity.Produkt;
import entity.Transakcje;
import java_applications.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.Alerts;
import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Klasa rozpoczynająca działanie okienka umożliwiające zakupy.
 */

public class ControllerKupno {

    private boolean Acc = false;
    private int koszty = 0;
    public TableView<Produkt> tab, tab2;
    public TableColumn<Produkt, String> kolNaz, kolNaz2;
    public TableColumn<Produkt, String> kolKat, kolKat2;
    public TableColumn<Produkt, String> kolMar, kolMar2;
    public TableColumn<Produkt, Integer> kolCen, kolCen2;
    private List<Produkt> produkty = new ArrayList<>();

    @FXML
    private ChoiceBox checkKategoria;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit");
    EntityManager entityManager = emf.createEntityManager();

    List<Produkt> pro = Main.getEntityManager().createQuery("from Produkt").getResultList();
    ObservableList<Produkt> opro = FXCollections.observableArrayList(pro);

    List<Kategoria> kat = Main.getEntityManager().createQuery("from Kategoria").getResultList();
    ObservableList<Kategoria> okat = FXCollections.observableArrayList(kat);

    @FXML
    private void initialize() {
        kolNaz.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNazwa()));
        kolMar.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMarka()));
        kolKat.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTyp()));
        kolCen.setCellValueFactory(new PropertyValueFactory("cena"));

       // tab2.getSortOrder().add(kolId);
        tab.setItems(opro);

        kolNaz2.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNazwa()));
        kolMar2.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMarka()));
        kolKat2.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTyp()));
        kolCen2.setCellValueFactory(new PropertyValueFactory("cena"));

        for(Kategoria tmp : okat)
        {
            checkKategoria.getItems().add(tmp.getTyp());
        }
    }

    /**
     * Metoda obługuje dodanie produktu do koszyka.
     */
    @FXML
    private void handleAddKosz(){
        if(tab.getSelectionModel().getSelectedItem().getIlosc() != 0)
        {
            if (tab.getSelectionModel().getSelectedItem() != null)
            {
                dataCheck();
                if(Acc) {
                    koszty += tab.getSelectionModel().getSelectedItem().getCena();
                    produkty.add(tab.getSelectionModel().getSelectedItem());

                    ObservableList<Produkt> oprodukty = FXCollections.observableArrayList(produkty);
                    tab2.setItems(oprodukty);
                }
            }
        }
    }

    /**
     * Metoda sprawdza czy produkt znajduje się w koszyku.
     */
    private void dataCheck() {
        Acc = true;
        Produkt tmp;
        int i;
        for( i=0; i<produkty.size(); i++)
        {
            tmp = produkty.get(i);
            if(tmp == tab.getSelectionModel().getSelectedItem())
            {
                Acc = false;
                break;
            }
            Acc = true;
        }
    }

    /**
     * Metoda obługuje usunięcie produktu z koszyka.
     */
    @FXML
    private void handleDelKosz(){
        if (tab2.getSelectionModel().getSelectedItem() != null)
        {
            Produkt tmp;
            int i;
            koszty -= tab2.getSelectionModel().getSelectedItem().getCena();
            for( i=0; i<produkty.size(); i++)
            {
                tmp = produkty.get(i);
                if(tmp == tab2.getSelectionModel().getSelectedItem())
                {
                    produkty.remove(tmp);
                }
            }
            ObservableList<Produkt> oprodukty = FXCollections.observableArrayList(produkty);
            tab2.setItems(oprodukty);
        }
    }

    /**
     * Metoda obługuje zakończenie transakcji.
     */
    @FXML
    private void handleAddKon(){
        if(!produkty.isEmpty())
        {
            Transakcje tmpTransakcje = new Transakcje("w trakcie", koszty, produkty);
            tmpTransakcje.setUzytkownikId(Main.getLoggedUser());
            try {
                Main.getEntityManager().getTransaction().begin();
                Main.getEntityManager().persist(tmpTransakcje);
                Main.getEntityManager().flush();
                Main.getEntityManager().getTransaction().commit();
            }
            catch (Exception e) {
            Main.getEntityManager().getTransaction().rollback();
            e.printStackTrace();
            }
            Alerts.TransakcjaCompleted().showAndWait();
            handleCof();
        }
    }

    /**
     * Metoda obługuje cofnięcie do poprzedniego okienka.
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

    /**
     * Metoda obługuje filtr produktów.
     */
    @FXML
    private void handleBox(){
        List<Produkt> prod = new ArrayList<>();
        int i;
        for(i=0;i<pro.size();i++)
        {
            if (checkKategoria.getValue().toString().equals(pro.get(i).getTyp()))
            {
                prod.add(pro.get(i));
            }
        }
        ObservableList<Produkt> oprod = FXCollections.observableArrayList(prod);
        tab.setItems(oprod);
    }
}
