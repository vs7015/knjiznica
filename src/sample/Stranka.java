
package sample;
import java.util.*;


public class Stranka {
   
   private String ime;
   private String priimek;
   private String uporabniskoIme;
   private String geslo;
   private int stKnjigIzposojenih;
   private boolean prijavljen;

   private ArrayList<Izposoja> izposoje = new ArrayList<>();

   public Stranka (String ime, String priimek, String uporabniskoIme, String geslo, int stKnjigIzposojenih) {
      this.ime = ime;
      this.priimek = priimek;
      this.uporabniskoIme = uporabniskoIme;
      this.geslo = geslo;
      this.stKnjigIzposojenih = stKnjigIzposojenih;
      this.prijavljen = false;
   }


   public void addIzposoja(Izposoja i) {
      this.izposoje.add(i);
      stKnjigIzposojenih++;
   }

   public String toString() {
      return ime + " " + priimek + " " + geslo;
   }



   public boolean preveriCeLahkoIzposodi() {
      return this.prijavljen && stKnjigIzposojenih < 5;
   }
   
   
   public void prijava() {
      this.prijavljen = true;
   }

}