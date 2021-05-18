package sample;
import java.util.*;


public class Knjiga {
   
   private int id;
   private String naslov;
   private String opis;
   private int letoIzdaje;
   private int stStrani;

   private ArrayList<KopijaKnjiga> kopijaKnjiga = new ArrayList<>();


   public Knjiga (int id, String naslov, String opis, int letoIzdaje, int stStrani) {
      this.id = id;
      this.naslov = naslov;
      this.opis = opis;
      this.letoIzdaje = letoIzdaje;
      this.stStrani = stStrani;
   }
   

   public void addKopija(KopijaKnjiga k) {
      kopijaKnjiga.add(k);
   }

   public String vrniSeznamKnjig() {
      return naslov + ", " + letoIzdaje + ", " + stStrani;
   }

   public String[] vrniSeznamNajdenihKnjig(String iskaniNiz) {
      String[] seznam = new String[kopijaKnjiga.size()];
      for (int i = 0; i < kopijaKnjiga.size(); i++) {
         String niz = kopijaKnjiga.get(i).vrniKnjigo();
         if (niz.split(", ")[1].contains(iskaniNiz)) {
            seznam[i] = niz;
         }
      }
      return seznam;
   }

   public String vrniPodrobnostiOKnjigi(int izbira) {
      return kopijaKnjiga.get(izbira).vrniPodrobnostiOKnjigi();
   }

}