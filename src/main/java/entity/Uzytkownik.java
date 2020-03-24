package entity;

import java_applications.Main;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa pobierająca informacje o uzytkownikach z bazy danch;
 */

@Entity
@Table(name="uzytkownik")
public class Uzytkownik {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    @SequenceGenerator(name="seq",sequenceName="oracle_seq",allocationSize=1)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column (name= "nick")
    private String nick;

    @Column (name= "haslo")
    private String haslo;

    @Column (name= "imie")
    private String imie;

    @Column (name= "nazwisko")
    private String nazwisko;

    @Column (name= "email")
    private String email;

    @Column (name= "telefon")
    private Integer telefon;

    @Column (name= "adres")
    private String adres;

    @OneToMany(mappedBy = "uzytkownikId")
    /**
     * Prywatne pole przechowujące transakcje dango uzytkownika.
     */
    private List<Transakcje> transakcjes = new ArrayList<>();


    public Uzytkownik()
    {
        this.nick = null;
        this.haslo = null;
        this.imie = null;
        this.nazwisko = null;
        this.email = null;
        this.telefon = null;
        this.adres = null;
    }

    /**
     * Konstruktor klasy.
     *
     * @param nick
     * @param haslo
     * @param imie
     * @param nazwisko
     * @param email
     * @param telefon
     * @param adres
     *
     */

    public Uzytkownik(String nick, String haslo, String imie, String nazwisko, String email, Integer telefon, String adres)
    {
        this.nick = nick;
        this.haslo = haslo;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        this.telefon = telefon;
        this.adres = adres;
    }

    public List<Transakcje> getTransakcjes() {
        return transakcjes;
    }

    public void setTransakcjes(List<Transakcje> transakcjes) {
        this.transakcjes = transakcjes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getHaslo() { return haslo; }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEMail() {
        return email;
    }

    public void setEMail(String EMail) {
        this.email = EMail;
    }

    public Integer getTelefon() {
        return telefon;
    }

    public void setTelefon(Integer telefon) {
        this.telefon = telefon;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}
