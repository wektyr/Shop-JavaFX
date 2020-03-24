package controller;

import entity.Produkt;
import entity.Transakcje;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Klasa rozpoczynająca działanie okna pokazującego produkty w ramach danej transakcji.
 */
public class ControllerShowPro extends Control implements Initializable {

    private Transakcje tmp = new ControllerTransakcjeUzy().getWybranyTransakcja();

    public TableView<Produkt> tab3;
    public TableColumn<Produkt, String> kolNaz;
    public TableColumn<Produkt, String> kolKat;
    public TableColumn<Produkt, String> kolMar;
    public TableColumn<Produkt, Integer> kolCen;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kolNaz.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNazwa()));
        kolMar.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMarka()));
        kolKat.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTyp()));
        kolCen.setCellValueFactory(new PropertyValueFactory("cena"));

        if(tmp.getProdukty() != null) {
            tab3.getItems().setAll(new ControllerTransakcjeUzy().getWybranyTransakcja().getProdukty());
        }
    }
}
