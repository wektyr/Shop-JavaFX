package entity;

import entity.Produkt;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa pobierająca informacje o transakcjach z bazy danch;
 */

@Entity
@Table(name="transakcje")
public class Transakcje {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    @SequenceGenerator(name="seq",sequenceName="oracle_seq",allocationSize=1)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn (name= "uzytkownik_id")
    /**
     * Prywatne pole przechowujące użytkownika danej transakcji.
     */
    private Uzytkownik uzytkownikId;


    @Column (name= "stan")
    private String stan;

    @Column (name= "kwota")
    private double kwota;

    @ManyToMany
    @JoinTable(
            name = "trans_prod",
            joinColumns = {@JoinColumn(name = "transakcje_id")},
            inverseJoinColumns = {@JoinColumn(name = "produkt_id")}
    )
    /**
     * Prywatne pole przechowujące produkty danej transakcji.
     */
    private List<Produkt> produkty = new ArrayList<>();

    public Transakcje(){}

    /**
     * Konstruktor klasy.
     *
     * @param stan
     * @param kwota
     * @param produkty
     *
     */

    public Transakcje(String stan, double kwota, List<Produkt>produkty){
        this.stan = stan;
        this.kwota = kwota;
        this.produkty = produkty;
    }


    public List<Produkt> getProdukty() {
        return produkty;
    }

    public void setProdukty(List<Produkt> produkty) {
        this.produkty = produkty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Uzytkownik getUzytkownikId() { return uzytkownikId; }

    public void setUzytkownikId(Uzytkownik uzytkownikId) { this.uzytkownikId = uzytkownikId; }

    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public double getKwota() { return kwota; }

    public void setKwota(double kwota) { this.kwota = kwota; }
}
