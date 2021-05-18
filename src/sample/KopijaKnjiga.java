
package sample;
import java.util.*;
import java.time.LocalDate;


public class KopijaKnjiga {
   
   private int id;
   private String naslov;
   private String opis;
   private int letoIzdaje;
   private int stStrani;
   private boolean status;

   private Knjiga knjiga;
   private ArrayList<Izposoja> izposoje = new ArrayList<>();


   public KopijaKnjiga(int id, String naslov, String opis, int letoIzdaje, int stStrani, boolean status) {
      this.id = id;
      this.naslov = naslov;
      this.opis = opis;
      this.letoIzdaje = letoIzdaje;
      this.stStrani = stStrani;
      this.status = status;
   }

   public void addIzposoja (Izposoja i) {
      izposoje.add(i);
   }

   public void setParent(Knjiga k) {
      this.knjiga = k;
   }

   
   public String vrniPodrobnostiOKnjigi() {
      return id + ", " + naslov + ", " + letoIzdaje + ", " + opis + ", " + status;
   }
   
   
   public String vrniKnjigo() {
      return id + ", " + naslov +", "+ letoIzdaje + ", " + stStrani;
   }
   
   
   public String izposodiZaTermin(Stranka stranka, KopijaKnjiga kopija) {
      if (status) {
         Izposoja i = new Izposoja(LocalDate.now(), LocalDate.now().plusDays(30), false, true);
         i.kreirajIzposojo(stranka, kopija);
         status = !status;
         return "Knjiga: " + kopija + " izposojena stranki: " + stranka;
      }
      else {
         return "Ni na voljo!";
      }
   }

}