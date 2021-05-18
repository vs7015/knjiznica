
package sample;
import java.time.LocalDate;
import java.util.*;


public class Izposoja {
   
   private LocalDate datumIzposoje;
   private LocalDate datumZaVracilo;
   private boolean rezervirana;
   private boolean izposojena;

   private Stranka izposojenec;
   private KopijaKnjiga knjiga;

   public Izposoja(LocalDate datumIzposoje, LocalDate datumZaVracilo, boolean rezervirana, boolean izposojena) {
      this.datumIzposoje = datumIzposoje;
      this.datumZaVracilo = datumZaVracilo;
      this.rezervirana = rezervirana;
      this.izposojena = izposojena;
   }

   public void setStranka(Stranka s) {
      this.izposojenec = s;
   }

   public void setKopijaKnjiga(KopijaKnjiga k) {
      this.knjiga = k;
   }

   public void kreirajIzposojo(Stranka stranka, KopijaKnjiga kopija) {
      this.setStranka(stranka);
      this.setKopijaKnjiga(kopija);
      kopija.addIzposoja(this);
      stranka.addIzposoja(this);
   }

   public String toString() {
      return datumIzposoje + " " + datumZaVracilo + " " + izposojenec.toString() + " " + knjiga.toString();
   }

}