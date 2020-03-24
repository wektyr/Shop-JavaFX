package entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa pobierająca informacje o produktach z bazy danch;
 */

@Entity
@Table(name="produkt")
public class Produkt {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    @SequenceGenerator(name="seq",sequenceName="oracle_seq",allocationSize=1)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "nazwa")
    private String nazwa;

    @Column(name = "marka")
    private String marka;

    @Column (name= "typ")
    private String typ;

    @ManyToOne
    @JoinColumn(name = "kategoria_id")
    /**
     * Prywatne pole przechowujące kategorię danego produktu.
     */
    private Kategoria kategoriaId;

    @Column(name = "ilosc")
    private int ilosc;

    @Column(name = "cena")
    private int cena;

    @Column(name = "czy_dostepny")
    private boolean czy_dostepny;

    @ManyToMany(mappedBy = "produkty")
    /**
     * Prywatne pole przechowujące transakcje danego produktu.
     */
    private List<Transakcje> transakcjes = new ArrayList<>();

    public Produkt()
    {
        this.nazwa=null;
        this.marka=null;
        this.cena=0;
        this.ilosc=0;
    }

    /**
     * Konstruktor klasy.
     *
     * @param nazwa
     * @param marka
     * @param cena
     * @param ilosc
     *
     */

    public Produkt(String nazwa, String marka, int cena, int ilosc)
    {
        this.czy_dostepny = true;
        this.nazwa = nazwa;
        this.marka = marka;
        this.cena = cena;
        this.ilosc = ilosc;
    }

    public List<Transakcje> getTransakcjes() {
        return transakcjes;
    }

    public void setTransakcjes(List<Transakcje> transakcjes) {
        this.transakcjes = transakcjes;
    }

    public int getCena() { return cena; }

    public void setCena(int cena) { this.cena = cena; }

    public boolean isCzy_dostepny() { return czy_dostepny; }

    public void setCzy_dostepny(boolean czy_dostepny) { this.czy_dostepny = czy_dostepny; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public Kategoria getKategoriaId() {
        return kategoriaId;
    }

    public void setKategoriaId(Kategoria kategoriaId) { this.kategoriaId = kategoriaId; }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public String getTyp() { return typ; }

    public void setTyp(String typ) { this.typ = typ; }
}