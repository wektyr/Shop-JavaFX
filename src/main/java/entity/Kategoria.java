package entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa pobierająca informacje o kategoriach z bazy danch;
 */

@Entity
@Table(name="kategoria")
public class Kategoria {
        @Id
        @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
        @SequenceGenerator(name="seq",sequenceName="oracle_seq",allocationSize=1)
        @Column(name = "id", updatable = false, nullable = false)
        private int id;

        @Column (name= "typ")
        private String typ;

        @OneToMany(mappedBy = "kategoriaId")
        /**
         * Prywatne pole przechowujące produkty danej kategorii.
         */
        private List<Produkt> produkty = new ArrayList<>();


        public Kategoria()
        {
                this.typ = null;
        }

        public Kategoria(String typ)
        {
                this.typ = typ;
        }

        public List<Produkt> getProdukty() {
                return produkty;
        }

        public void setProdukty(List<Produkt> produkty) {
                this.produkty = produkty;
        }

        public int getId() { return id; }

        public void setId(int id) {
                this.id = id;
        }

        public String getTyp() {
                return typ;
        }

        public void setTyp(String typ) {
                this.typ = typ;
        }
}
