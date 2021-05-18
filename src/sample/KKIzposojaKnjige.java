
package sample;
import java.util.*;


public class KKIzposojaKnjige {
   
   public ArrayList<Stranka> stranka;
   public ArrayList<KopijaKnjiga> kopijaKnjiga;
   public ArrayList<Knjiga> knjiga;


   public KKIzposojaKnjige (ArrayList<Stranka> stranka, ArrayList<Knjiga> knjiga, ArrayList<KopijaKnjiga> kopijaKnjiga) {
      this.stranka = stranka;
      this.knjiga = knjiga;
      this.kopijaKnjiga = kopijaKnjiga;
   }

   public String[] vrniSeznamKnjig() {
      String[] seznamKnjig = new String[knjiga.size()];
      for (int i =0; i < knjiga.size(); i++) {
         seznamKnjig[i] = knjiga.get(i).vrniSeznamKnjig();
      }
      return seznamKnjig;
   }


   public String vrniPodrobnostiKnjige(int indeks, int izbira) {
      return knjiga.get(indeks).vrniPodrobnostiOKnjigi(izbira);
   }
   
   
   public String vrniNapako() {
      // TODO: implement
      return null;
   }


   public Stranka prijavi(String ime, String priimek, String geslo) {
      String[] podatki;
      for (int i = 0; i < stranka.size(); i++) {
         podatki = stranka.get(i).toString().split(" ");
         if (podatki.length > 2) {
            if (podatki[0].equals(ime) && podatki[1].equals(priimek) && podatki[2].equals(geslo)) {
               stranka.get(i).prijava();
               return stranka.get(i);
            }
         }
      }
      return null;
   }


   public String izposodi(String k, Stranka prijavljena) {
      boolean lahko = prijavljena.preveriCeLahkoIzposodi();
      KopijaKnjiga izposoda = null;
      String potrdilo = null;
      if (lahko) {
         for (int i = 0; i < kopijaKnjiga.size(); i++) {
            if (kopijaKnjiga.get(i).vrniPodrobnostiOKnjigi().contains(k)) {
               izposoda = kopijaKnjiga.get(i);
            }
         }
         potrdilo = izposoda.izposodiZaTermin(prijavljena, izposoda);
      }
      return potrdilo;
   }


   public String[] vrniSeznamNajdenihKnjig(String iskaniNiz) {
      String[] seznam;
      for (int i = 0; i < knjiga.size(); i++) {
         seznam = knjiga.get(i).vrniSeznamNajdenihKnjig(iskaniNiz);
         if (seznam.length > 0 && seznam[0] != null) {
            String[] tmp = new String[seznam.length+1];
            for (int j = 0; j < tmp.length-1; j++) {
               tmp[j] = seznam[j];
            }
            tmp[tmp.length-1] = i+"";
            return tmp;
         }
      }
      return null;
   }
   
   
   
   /*public java.util.Collection getStranka() {
      if (stranka == null)
         stranka = new java.util.HashSet();
      return stranka;
   }
   
   
   public java.util.Iterator getIteratorStranka() {
      if (stranka == null)
         stranka = new java.util.HashSet();
      return stranka.iterator();
   }
   
   
   public void setStranka(java.util.Collection newStranka) {
      removeAllStranka();
      for (java.util.Iterator iter = newStranka.iterator(); iter.hasNext();)
         addStranka((Stranka)iter.next());
   }
   
   
   public void addStranka(Stranka newStranka) {
      if (newStranka == null)
         return;
      if (this.stranka == null)
         this.stranka = new java.util.HashSet();
      if (!this.stranka.contains(newStranka))
         this.stranka.add(newStranka);
   }
   
   
   public void removeStranka(Stranka oldStranka) {
      if (oldStranka == null)
         return;
      if (this.stranka != null)
         if (this.stranka.contains(oldStranka))
            this.stranka.remove(oldStranka);
   }
   
   
   public void removeAllStranka() {
      if (stranka != null)
         stranka.clear();
   }
   
   public java.util.Collection getKopijaKnjiga() {
      if (kopijaKnjiga == null)
         kopijaKnjiga = new java.util.HashSet();
      return kopijaKnjiga;
   }
   
   
   public java.util.Iterator getIteratorKopijaKnjiga() {
      if (kopijaKnjiga == null)
         kopijaKnjiga = new java.util.HashSet();
      return kopijaKnjiga.iterator();
   }
   
   
   public void setKopijaKnjiga(java.util.Collection newKopijaKnjiga) {
      removeAllKopijaKnjiga();
      for (java.util.Iterator iter = newKopijaKnjiga.iterator(); iter.hasNext();)
         addKopijaKnjiga((KopijaKnjiga)iter.next());
   }
   
   
   public void addKopijaKnjiga(KopijaKnjiga newKopijaKnjiga) {
      if (newKopijaKnjiga == null)
         return;
      if (this.kopijaKnjiga == null)
         this.kopijaKnjiga = new java.util.HashSet();
      if (!this.kopijaKnjiga.contains(newKopijaKnjiga))
         this.kopijaKnjiga.add(newKopijaKnjiga);
   }
   
   
   public void removeKopijaKnjiga(KopijaKnjiga oldKopijaKnjiga) {
      if (oldKopijaKnjiga == null)
         return;
      if (this.kopijaKnjiga != null)
         if (this.kopijaKnjiga.contains(oldKopijaKnjiga))
            this.kopijaKnjiga.remove(oldKopijaKnjiga);
   }
   
   
   public void removeAllKopijaKnjiga() {
      if (kopijaKnjiga != null)
         kopijaKnjiga.clear();
   }
   
   public java.util.Collection getKnjiga() {
      if (knjiga == null)
         knjiga = new java.util.HashSet();
      return knjiga;
   }
   
   
   public java.util.Iterator getIteratorKnjiga() {
      if (knjiga == null)
         knjiga = new java.util.HashSet();
      return knjiga.iterator();
   }
   
   
   public void setKnjiga(java.util.Collection newKnjiga) {
      removeAllKnjiga();
      for (java.util.Iterator iter = newKnjiga.iterator(); iter.hasNext();)
         addKnjiga((Knjiga)iter.next());
   }
   
   
   public void addKnjiga(Knjiga newKnjiga) {
      if (newKnjiga == null)
         return;
      if (this.knjiga == null)
         this.knjiga = new java.util.HashSet();
      if (!this.knjiga.contains(newKnjiga))
         this.knjiga.add(newKnjiga);
   }
   
   
   public void removeKnjiga(Knjiga oldKnjiga) {
      if (oldKnjiga == null)
         return;
      if (this.knjiga != null)
         if (this.knjiga.contains(oldKnjiga))
            this.knjiga.remove(oldKnjiga);
   }
   
   
   public void removeAllKnjiga() {
      if (knjiga != null)
         knjiga.clear();
   }*/

}