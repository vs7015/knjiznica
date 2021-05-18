
package sample;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.time.LocalDate;
import java.util.*;


public class ZMStrankaSiIzposodiKnjigo implements Initializable {

   public KKIzposojaKnjige kKIzposojaKnjige;
   public Stranka prijavljena;

   // javafx
   public TextField ime;
   public TextField priimek;
   public PasswordField geslo;
   public Button prijava;
   public Label status;
   public Label user;
   public Button buttonKnjige1;
   public TableView<String> tabela;
   public Button isci;
   public TextField iskalnoPolje;
   public TextArea opis;
   public Label knjigaNaVoljo;
   public Button izposodi;

   public ZMStrankaSiIzposodiKnjigo () {

   }

   public ZMStrankaSiIzposodiKnjigo (KKIzposojaKnjige kkIzposojaKnjige, Button prijava, TextField ime, TextField priimek, PasswordField geslo,
                                     Label user, Label status, Button buttonKnjige1,
                                     TableView<String> tabela, Button isci, TextField iskalnoPolje, TextArea opis, Label knjigaNaVoljo, Button izposodi) {
      this.kKIzposojaKnjige = kkIzposojaKnjige;
      this.prijava = prijava;
      this.ime = ime;
      this.priimek = priimek;
      this.geslo = geslo;
      this.user = user;
      this.status = status;
      this.buttonKnjige1 = buttonKnjige1;
      this.tabela = tabela;
      this.isci = isci;
      this.iskalnoPolje = iskalnoPolje;
      this.opis = opis;
      this.knjigaNaVoljo = knjigaNaVoljo;
      this.izposodi = izposodi;
   }


   public void pricniIzposojo(String k) {
      izposodi.setOnAction(e -> {
         String potrdilo = kKIzposojaKnjige.izposodi(k, prijavljena);
         izpisiPotrdilo(potrdilo);
      });
   }


   public void prikaziSeznamKnjig(String [] spisek) {
      TableColumn<String, String> columnNaslov = new TableColumn<>("Knjiga");
      columnNaslov.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));

      tabela.getColumns().add(columnNaslov);
      ObservableList<String> items = FXCollections.observableArrayList();


      for (int i = 0; i < spisek.length; i++){
         items.add(spisek[i]);
      }
      tabela.setItems(items);
      isciKnjigo();
   }


   public void izpisiPodrobnostiKnjige(String k) {
      boolean naVoljo = Boolean.parseBoolean(k.substring(k.length() - 6).replace(", ", "").trim());
      String oKnjigi = k.replace(k.substring(k.length() - 7), "");
      if (naVoljo)
         knjigaNaVoljo.setText("Status knjige: Knjiga je na voljo!");
      else
         knjigaNaVoljo.setText("Status knjige: Knjiga ni na voljo!");
      opis.setText(oKnjigi);
      pricniIzposojo(k);
   }


   public void izberiKnjigo(int indeks) {
      tabela.getSelectionModel().selectedIndexProperty().addListener((obser, oldV, newV) -> {
         if (newV != null) {
            int izbira = tabela.getSelectionModel().getSelectedIndex();
            String k = kKIzposojaKnjige.vrniPodrobnostiKnjige(indeks, izbira);
            izpisiPodrobnostiKnjige(k);
         }
      });
   }
   
   
   public void izpisiPorociloONapaki(String potrdilo) {
      if (potrdilo == null)
         status.setText("Status: Vaš limit za število knjig v izposoji je že dosežen!");
      else if (potrdilo.equals("Ni na voljo!"))
         status.setText("Status: Knjiga je v izposoji/rezervirana!");
   }


   public void izpisiPotrdilo(String potrdilo) {
      if (potrdilo == null || potrdilo.equals("Ni na voljo!"))
         izpisiPorociloONapaki(potrdilo);
      else
         status.setText("Status: Knjiga izposojena!");
   }


   public void strankaSePrijavi() {
      prijava.setOnAction(e -> {
         String name = ime.getText();
         String surname = priimek.getText();
         String pass = geslo.getText();
         prijavljena = kKIzposojaKnjige.prijavi(name, surname, pass);
         if (prijavljena != null) {
            prikaziSporociloOUspesniPrijavi(prijavljena);
         }
      });
   }


   public void isciKnjigo() {
      isci.setOnAction(e -> {
         String iskaniNiz = iskalnoPolje.getText();
         String[] knjige = kKIzposojaKnjige.vrniSeznamNajdenihKnjig(iskaniNiz);
         izpisiSeznamNajdenihKnjig(knjige);
      });
   }


   public void izpisiSeznamNajdenihKnjig(String[] knjige) {
      ObservableList<String> items = FXCollections.observableArrayList();
      items.removeAll();

      for (int i = 0; i < knjige.length-1; i++) {
         items.add(knjige[i]);
      }
      tabela.setItems(items);



      int indeks = Integer.parseInt(knjige[knjige.length-1]);
      izberiKnjigo(indeks);
   }


   public void zahtevajSpisekKnjig() {
      buttonKnjige1.setOnAction(e -> {
         String[] spisek = kKIzposojaKnjige.vrniSeznamKnjig();
         prikaziSeznamKnjig(spisek);
      });
   }


   public void prikaziSporociloOUspesniPrijavi(Stranka prijavljena) {
      user.setText("Uporabnik: " + prijavljena.toString().split(" ")[0] + " " + prijavljena.toString().split(" ")[1]);
      status.setText("Status: Prijava uspešna! Prijavljen " + prijavljena.toString().split(" ")[0] + " " + prijavljena.toString().split(" ")[1]);
      zahtevajSpisekKnjig();
   }

   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
      /** napolni Stranke */
      ArrayList<Stranka> stranke = new ArrayList<>();
      stranke.add(new Stranka("Janez", "Novak", "JN11", "Geslo2020", 0));
      stranke.add(new Stranka("Tone", "Marone", "Tomy", "Gani", 0));
      stranke.add(new Stranka("Gasper", "Jankovc", "GJxx", "12345", 0));

      /** napolni knjige */
      ArrayList<Knjiga> knjige = new ArrayList<>();
      knjige.add(new Knjiga(1, "Tujec", "" +
              "Camusov Tujec je izšel leta 1942, ob 70. letnici izida pa je Mladinska knjiga poslala na trg roman z novim, svežim prevodom Suzane Koncut in z ilustracijami Alenke Sottler. To delo Alberta Camusa tudi po zatonu eksistencializma še vedno buri duhove in izziva poznavalce literature k vedno novim interpretacijam in študijam. Pripoveduje o uradniku Mersaultu, ki pasivno vdano sprejema svet in dogodke okrog sebe in šele v zaporu, kamor je vržen zaradi umora Arabca, doživi preobrazbo, ko se upre religioznemu in pravnemu sistemu. V soočenju s skorajšnjo smrtjo se učloveči, postane absurdni, lucidni junak, ki je pripravljen plačati za svoj zločin, ne da pa duše. Alenka Sottler je z enostavnimi, fotografsko bežnimi črno belimi ilustracijami pričarala eksistencialnega duha romana in prav sozvočje teksta in ilustracije odlikuje pričujočo izdajo.",
              2012, 106));
      knjige.add(new Knjiga(2, "Iskanje izgubljenega časa", "V duhu Sokratove trditve, da neraziskanega življenja ni vredno živeti, je ameriški psihoanalitik Stephen Grosz skozi izkušnje, ki jih je pridobil v petindvajsetih letih svojega izvajanja psihoanalitične prakse, v svoji odmevni knjigi Iskanje izgubljenega človeka ustvaril fascinantno zbirko človeških zgodb, ki se skrivajo tako za najbolj običajnimi kot tudi nenavadnimi človeškimi vedenji. Avtorjev namen je bil skozi prigode resničnih posameznikov odkriti in razložiti skrite motive, ki vplivajo na naše vedenje in prikazati pomembnost tega, da naše pripovedovanje nekdo posluša. Grosz z bralcem deli zgodbe pacientov, ki jih je srečal v toku svoje prakse v več kot 50 tisoč urah pogovorov, te pa virtuozno prelije v pripovedi, s katerimi nas natančno pelje skozi psihoanalitični proces.",
              2015, 200));
      knjige.add(new Knjiga(3, "Harry Potter in kamen modrosti", "Harry Potter je enajstletni fantič, ki živi pri stricu in teti. Spati mora v shrambi pod stopnicami, debeli in zlobni bratranec\n" +
              "Dudley pa ga ima za živo igračo. Ko nekega dne Harry prejme skrivnostno pismo, mu v sivino vsakdanjika posije svetla luč,\n" +
              "saj izve, da je čarovnik. Znajde se na Bradavičarki, akademiji za čarovnike, kjer spozna nove prijatelje. Toda za debelimi\n" +
              "zidovi starodavne šole prežijo nanj tudi nevarnosti.  \n",
              244, 2010));
      knjige.add(new Knjiga(4, "Tisto", "Sedem najstnikov doživi grozljivo izkušnjo v domačem kraju Derry v ameriški zvezni državi Maine. Spopasti se morajo z nečim groznim, kar že stoletja ustrahuje in mori. Vsakih 27 let se zbudi in se hrani z ljudmi, najraje z otroki. Ko Georgie postane žrtev, se njegov brat Bill in šest njegovih prijateljev odloči, da bodo izsledili zlo. Skoraj trideset let kasneje se groza ponovi. Že odraslih sedem prijateljev se vrne, da bi enkrat za vselej obračunali s tistim. Kljub nevarnostim jih nazaj vleče takšna sila, da se ji ne morejo upreti: »Bill je verjel, da je vse pobila ista oseba ... če je sploh bila oseba. Včasih ni bil čisto prepričan.« Knjiga, ki je kljub obsegu bralec ne more odložiti, saj ga vleče in ne izpusti.",
              2019, 1300));
      knjige.add(new Knjiga(5, "Visoška kronika", "To je kronika o družini Kalan, ki jo je Ivan Tavčar začel pisati potem, ko je bil že skoraj četrt stoletja lastnik poljanske graščine Visoko. Oče Polikarp je med vračanjem s tridesetletne vojne ubil prijatelja in se polastil ukradene vojaške blagajne. S tem denarjem si je kupil lepo posestvo, vendar zaradi občutka krivde ni našel sreče. Na smrtni postelji je starejšemu sinu Izidorju naročil, naj gre poiskati hčerko umorjenega prijatelja Agato, jo pripelje na Visoko in jo vzame za ženo. Agato zavrnjeni sosed Marks obsodi čarovništva. Da bi dokazala svojo nedolžnost, mora prebresti deročo reko. Ko jo sredi reke zajame močan tok, jo reši Izidorjev brat Jurij in si jo tako pridobi za ženo. Izidor odide v vojsko in prepusti posestvo bratu.", 2005, 204));

      /** napolni kopije knjig */
      ArrayList<KopijaKnjiga> kopijeKnjig = new ArrayList<>();
      kopijeKnjig.add(new KopijaKnjiga(10, "Tujec", "" +
              "Camusov Tujec je izšel leta 1942, ob 70. letnici izida pa je Mladinska knjiga poslala na trg roman z novim, svežim prevodom Suzane Koncut in z ilustracijami Alenke Sottler. To delo Alberta Camusa tudi po zatonu eksistencializma še vedno buri duhove in izziva poznavalce literature k vedno novim interpretacijam in študijam. Pripoveduje o uradniku Mersaultu, ki pasivno vdano sprejema svet in dogodke okrog sebe in šele v zaporu, kamor je vržen zaradi umora Arabca, doživi preobrazbo, ko se upre religioznemu in pravnemu sistemu. V soočenju s skorajšnjo smrtjo se učloveči, postane absurdni, lucidni junak, ki je pripravljen plačati za svoj zločin, ne da pa duše. Alenka Sottler je z enostavnimi, fotografsko bežnimi črno belimi ilustracijami pričarala eksistencialnega duha romana in prav sozvočje teksta in ilustracije odlikuje pričujočo izdajo.\n",
              2012, 106, false));
      kopijeKnjig.add(new KopijaKnjiga(11, "Tujec", "" +
              "Camusov Tujec je izšel leta 1942, ob 70. letnici izida pa je Mladinska knjiga poslala na trg roman z novim, svežim prevodom Suzane Koncut in z ilustracijami Alenke Sottler. To delo Alberta Camusa tudi po zatonu eksistencializma še vedno buri duhove in izziva poznavalce literature k vedno novim interpretacijam in študijam. Pripoveduje o uradniku Mersaultu, ki pasivno vdano sprejema svet in dogodke okrog sebe in šele v zaporu, kamor je vržen zaradi umora Arabca, doživi preobrazbo, ko se upre religioznemu in pravnemu sistemu. V soočenju s skorajšnjo smrtjo se učloveči, postane absurdni, lucidni junak, ki je pripravljen plačati za svoj zločin, ne da pa duše. Alenka Sottler je z enostavnimi, fotografsko bežnimi črno belimi ilustracijami pričarala eksistencialnega duha romana in prav sozvočje teksta in ilustracije odlikuje pričujočo izdajo.\n",
              2012, 106, true));
      kopijeKnjig.add(new KopijaKnjiga(12, "Tujec", "" +
              "Camusov Tujec je izšel leta 1942, ob 70. letnici izida pa je Mladinska knjiga poslala na trg roman z novim, svežim prevodom Suzane Koncut in z ilustracijami Alenke Sottler. To delo Alberta Camusa tudi po zatonu eksistencializma še vedno buri duhove in izziva poznavalce literature k vedno novim interpretacijam in študijam. Pripoveduje o uradniku Mersaultu, ki pasivno vdano sprejema svet in dogodke okrog sebe in šele v zaporu, kamor je vržen zaradi umora Arabca, doživi preobrazbo, ko se upre religioznemu in pravnemu sistemu. V soočenju s skorajšnjo smrtjo se učloveči, postane absurdni, lucidni junak, ki je pripravljen plačati za svoj zločin, ne da pa duše. Alenka Sottler je z enostavnimi, fotografsko bežnimi črno belimi ilustracijami pričarala eksistencialnega duha romana in prav sozvočje teksta in ilustracije odlikuje pričujočo izdajo.\n",
              2012, 106, true));
      kopijeKnjig.add(new KopijaKnjiga(13, "Tujec", "" +
              "Camusov Tujec je izšel leta 1942, ob 70. letnici izida pa je Mladinska knjiga poslala na trg roman z novim, svežim prevodom Suzane Koncut in z ilustracijami Alenke Sottler. To delo Alberta Camusa tudi po zatonu eksistencializma še vedno buri duhove in izziva poznavalce literature k vedno novim interpretacijam in študijam. Pripoveduje o uradniku Mersaultu, ki pasivno vdano sprejema svet in dogodke okrog sebe in šele v zaporu, kamor je vržen zaradi umora Arabca, doživi preobrazbo, ko se upre religioznemu in pravnemu sistemu. V soočenju s skorajšnjo smrtjo se učloveči, postane absurdni, lucidni junak, ki je pripravljen plačati za svoj zločin, ne da pa duše. Alenka Sottler je z enostavnimi, fotografsko bežnimi črno belimi ilustracijami pričarala eksistencialnega duha romana in prav sozvočje teksta in ilustracije odlikuje pričujočo izdajo.\n",
              2012, 106, true));
      kopijeKnjig.add(new KopijaKnjiga(14, "Tujec", "" +
              "Camusov Tujec je izšel leta 1942, ob 70. letnici izida pa je Mladinska knjiga poslala na trg roman z novim, svežim prevodom Suzane Koncut in z ilustracijami Alenke Sottler. To delo Alberta Camusa tudi po zatonu eksistencializma še vedno buri duhove in izziva poznavalce literature k vedno novim interpretacijam in študijam. Pripoveduje o uradniku Mersaultu, ki pasivno vdano sprejema svet in dogodke okrog sebe in šele v zaporu, kamor je vržen zaradi umora Arabca, doživi preobrazbo, ko se upre religioznemu in pravnemu sistemu. V soočenju s skorajšnjo smrtjo se učloveči, postane absurdni, lucidni junak, ki je pripravljen plačati za svoj zločin, ne da pa duše. Alenka Sottler je z enostavnimi, fotografsko bežnimi črno belimi ilustracijami pričarala eksistencialnega duha romana in prav sozvočje teksta in ilustracije odlikuje pričujočo izdajo.\n",
              2012, 106, true));

      //povezava knjige Tujec
      kopijeKnjig.get(0).setParent(knjige.get(0));
      kopijeKnjig.get(1).setParent(knjige.get(0));
      kopijeKnjig.get(2).setParent(knjige.get(0));
      kopijeKnjig.get(3).setParent(knjige.get(0));
      kopijeKnjig.get(4).setParent(knjige.get(0));
      //
      knjige.get(0).addKopija(kopijeKnjig.get(0));
      knjige.get(0).addKopija(kopijeKnjig.get(1));
      knjige.get(0).addKopija(kopijeKnjig.get(2));
      knjige.get(0).addKopija(kopijeKnjig.get(3));
      knjige.get(0).addKopija(kopijeKnjig.get(4));


      kopijeKnjig.add(new KopijaKnjiga(20, "Iskanje izgubljenega časa", "V duhu Sokratove trditve, da neraziskanega življenja ni vredno živeti, je ameriški psihoanalitik Stephen Grosz skozi izkušnje, ki jih je pridobil v petindvajsetih letih svojega izvajanja psihoanalitične prakse, v svoji odmevni knjigi Iskanje izgubljenega človeka ustvaril fascinantno zbirko človeških zgodb, ki se skrivajo tako za najbolj običajnimi kot tudi nenavadnimi človeškimi vedenji. Avtorjev namen je bil skozi prigode resničnih posameznikov odkriti in razložiti skrite motive, ki vplivajo na naše vedenje in prikazati pomembnost tega, da naše pripovedovanje nekdo posluša. Grosz z bralcem deli zgodbe pacientov, ki jih je srečal v toku svoje prakse v več kot 50 tisoč urah pogovorov, te pa virtuozno prelije v pripovedi, s katerimi nas natančno pelje skozi psihoanalitični proces.",
              2015, 200, true));
      kopijeKnjig.add(new KopijaKnjiga(21, "Iskanje izgubljenega časa", "V duhu Sokratove trditve, da neraziskanega življenja ni vredno živeti, je ameriški psihoanalitik Stephen Grosz skozi izkušnje, ki jih je pridobil v petindvajsetih letih svojega izvajanja psihoanalitične prakse, v svoji odmevni knjigi Iskanje izgubljenega človeka ustvaril fascinantno zbirko človeških zgodb, ki se skrivajo tako za najbolj običajnimi kot tudi nenavadnimi človeškimi vedenji. Avtorjev namen je bil skozi prigode resničnih posameznikov odkriti in razložiti skrite motive, ki vplivajo na naše vedenje in prikazati pomembnost tega, da naše pripovedovanje nekdo posluša. Grosz z bralcem deli zgodbe pacientov, ki jih je srečal v toku svoje prakse v več kot 50 tisoč urah pogovorov, te pa virtuozno prelije v pripovedi, s katerimi nas natančno pelje skozi psihoanalitični proces.",
              2015, 200, false));
      kopijeKnjig.add(new KopijaKnjiga(22, "Iskanje izgubljenega časa", "V duhu Sokratove trditve, da neraziskanega življenja ni vredno živeti, je ameriški psihoanalitik Stephen Grosz skozi izkušnje, ki jih je pridobil v petindvajsetih letih svojega izvajanja psihoanalitične prakse, v svoji odmevni knjigi Iskanje izgubljenega človeka ustvaril fascinantno zbirko človeških zgodb, ki se skrivajo tako za najbolj običajnimi kot tudi nenavadnimi človeškimi vedenji. Avtorjev namen je bil skozi prigode resničnih posameznikov odkriti in razložiti skrite motive, ki vplivajo na naše vedenje in prikazati pomembnost tega, da naše pripovedovanje nekdo posluša. Grosz z bralcem deli zgodbe pacientov, ki jih je srečal v toku svoje prakse v več kot 50 tisoč urah pogovorov, te pa virtuozno prelije v pripovedi, s katerimi nas natančno pelje skozi psihoanalitični proces.",
              2015, 200, true));


      // povezava knjige ISC
      kopijeKnjig.get(5).setParent(knjige.get(1));
      kopijeKnjig.get(6).setParent(knjige.get(1));
      kopijeKnjig.get(7).setParent(knjige.get(1));
      knjige.get(1).addKopija(kopijeKnjig.get(5));
      knjige.get(1).addKopija(kopijeKnjig.get(6));
      knjige.get(1).addKopija(kopijeKnjig.get(7));

      kopijeKnjig.add(new KopijaKnjiga(30, "Harry Potter in kamen modrosti", "Harry Potter je enajstletni fantič, ki živi pri stricu in teti. Spati mora v shrambi pod stopnicami, debeli in zlobni bratranec\n" +
              "Dudley pa ga ima za živo igračo. Ko nekega dne Harry prejme skrivnostno pismo, mu v sivino vsakdanjika posije svetla luč,\n" +
              "saj izve, da je čarovnik. Znajde se na Bradavičarki, akademiji za čarovnike, kjer spozna nove prijatelje. Toda za debelimi\n" +
              "zidovi starodavne šole prežijo nanj tudi nevarnosti.  \n",
              244, 2010, true));
      kopijeKnjig.add(new KopijaKnjiga(31, "Harry Potter in kamen modrosti", "Harry Potter je enajstletni fantič, ki živi pri stricu in teti. Spati mora v shrambi pod stopnicami, debeli in zlobni bratranec\n" +
              "Dudley pa ga ima za živo igračo. Ko nekega dne Harry prejme skrivnostno pismo, mu v sivino vsakdanjika posije svetla luč,\n" +
              "saj izve, da je čarovnik. Znajde se na Bradavičarki, akademiji za čarovnike, kjer spozna nove prijatelje. Toda za debelimi\n" +
              "zidovi starodavne šole prežijo nanj tudi nevarnosti.  \n",
              244, 2010, true));

      //povezava knjige HP
      kopijeKnjig.get(8).setParent(knjige.get(2));
      kopijeKnjig.get(9).setParent(knjige.get(2));
      knjige.get(2).addKopija(kopijeKnjig.get(8));
      knjige.get(2).addKopija(kopijeKnjig.get(9));

      kopijeKnjig.add(new KopijaKnjiga(40, "Tisto", "Sedem najstnikov doživi grozljivo izkušnjo v domačem kraju Derry v ameriški zvezni državi Maine. Spopasti se morajo z nečim groznim, kar že stoletja ustrahuje in mori. Vsakih 27 let se zbudi in se hrani z ljudmi, najraje z otroki. Ko Georgie postane žrtev, se njegov brat Bill in šest njegovih prijateljev odloči, da bodo izsledili zlo. Skoraj trideset let kasneje se groza ponovi. Že odraslih sedem prijateljev se vrne, da bi enkrat za vselej obračunali s tistim. Kljub nevarnostim jih nazaj vleče takšna sila, da se ji ne morejo upreti: »Bill je verjel, da je vse pobila ista oseba ... če je sploh bila oseba. Včasih ni bil čisto prepričan.« Knjiga, ki je kljub obsegu bralec ne more odložiti, saj ga vleče in ne izpusti.",
              2019, 1300, true));
      kopijeKnjig.add(new KopijaKnjiga(41, "Tisto", "Sedem najstnikov doživi grozljivo izkušnjo v domačem kraju Derry v ameriški zvezni državi Maine. Spopasti se morajo z nečim groznim, kar že stoletja ustrahuje in mori. Vsakih 27 let se zbudi in se hrani z ljudmi, najraje z otroki. Ko Georgie postane žrtev, se njegov brat Bill in šest njegovih prijateljev odloči, da bodo izsledili zlo. Skoraj trideset let kasneje se groza ponovi. Že odraslih sedem prijateljev se vrne, da bi enkrat za vselej obračunali s tistim. Kljub nevarnostim jih nazaj vleče takšna sila, da se ji ne morejo upreti: »Bill je verjel, da je vse pobila ista oseba ... če je sploh bila oseba. Včasih ni bil čisto prepričan.« Knjiga, ki je kljub obsegu bralec ne more odložiti, saj ga vleče in ne izpusti.",
              2019, 1300, true));
      kopijeKnjig.add(new KopijaKnjiga(42, "Tisto", "Sedem najstnikov doživi grozljivo izkušnjo v domačem kraju Derry v ameriški zvezni državi Maine. Spopasti se morajo z nečim groznim, kar že stoletja ustrahuje in mori. Vsakih 27 let se zbudi in se hrani z ljudmi, najraje z otroki. Ko Georgie postane žrtev, se njegov brat Bill in šest njegovih prijateljev odloči, da bodo izsledili zlo. Skoraj trideset let kasneje se groza ponovi. Že odraslih sedem prijateljev se vrne, da bi enkrat za vselej obračunali s tistim. Kljub nevarnostim jih nazaj vleče takšna sila, da se ji ne morejo upreti: »Bill je verjel, da je vse pobila ista oseba ... če je sploh bila oseba. Včasih ni bil čisto prepričan.« Knjiga, ki je kljub obsegu bralec ne more odložiti, saj ga vleče in ne izpusti.",
              2019, 1300, true));
      kopijeKnjig.add(new KopijaKnjiga(43, "Tisto", "Sedem najstnikov doživi grozljivo izkušnjo v domačem kraju Derry v ameriški zvezni državi Maine. Spopasti se morajo z nečim groznim, kar že stoletja ustrahuje in mori. Vsakih 27 let se zbudi in se hrani z ljudmi, najraje z otroki. Ko Georgie postane žrtev, se njegov brat Bill in šest njegovih prijateljev odloči, da bodo izsledili zlo. Skoraj trideset let kasneje se groza ponovi. Že odraslih sedem prijateljev se vrne, da bi enkrat za vselej obračunali s tistim. Kljub nevarnostim jih nazaj vleče takšna sila, da se ji ne morejo upreti: »Bill je verjel, da je vse pobila ista oseba ... če je sploh bila oseba. Včasih ni bil čisto prepričan.« Knjiga, ki je kljub obsegu bralec ne more odložiti, saj ga vleče in ne izpusti.",
              2019, 1300, false));

      //povezava knjige IT
      kopijeKnjig.get(10).setParent(knjige.get(3));
      kopijeKnjig.get(11).setParent(knjige.get(3));
      kopijeKnjig.get(12).setParent(knjige.get(3));
      kopijeKnjig.get(13).setParent(knjige.get(3));
      knjige.get(3).addKopija(kopijeKnjig.get(10));
      knjige.get(3).addKopija(kopijeKnjig.get(11));
      knjige.get(3).addKopija(kopijeKnjig.get(12));
      knjige.get(3).addKopija(kopijeKnjig.get(13));

      kopijeKnjig.add(new KopijaKnjiga(50, "Visoška kronika", "To je kronika o družini Kalan, ki jo je Ivan Tavčar začel pisati potem, ko je bil že skoraj četrt stoletja lastnik poljanske graščine Visoko. Oče Polikarp je med vračanjem s tridesetletne vojne ubil prijatelja in se polastil ukradene vojaške blagajne. S tem denarjem si je kupil lepo posestvo, vendar zaradi občutka krivde ni našel sreče. Na smrtni postelji je starejšemu sinu Izidorju naročil, naj gre poiskati hčerko umorjenega prijatelja Agato, jo pripelje na Visoko in jo vzame za ženo. Agato zavrnjeni sosed Marks obsodi čarovništva. Da bi dokazala svojo nedolžnost, mora prebresti deročo reko. Ko jo sredi reke zajame močan tok, jo reši Izidorjev brat Jurij in si jo tako pridobi za ženo. Izidor odide v vojsko in prepusti posestvo bratu.", 2005, 204, true));
      kopijeKnjig.add(new KopijaKnjiga(51, "Visoška kronika", "To je kronika o družini Kalan, ki jo je Ivan Tavčar začel pisati potem, ko je bil že skoraj četrt stoletja lastnik poljanske graščine Visoko. Oče Polikarp je med vračanjem s tridesetletne vojne ubil prijatelja in se polastil ukradene vojaške blagajne. S tem denarjem si je kupil lepo posestvo, vendar zaradi občutka krivde ni našel sreče. Na smrtni postelji je starejšemu sinu Izidorju naročil, naj gre poiskati hčerko umorjenega prijatelja Agato, jo pripelje na Visoko in jo vzame za ženo. Agato zavrnjeni sosed Marks obsodi čarovništva. Da bi dokazala svojo nedolžnost, mora prebresti deročo reko. Ko jo sredi reke zajame močan tok, jo reši Izidorjev brat Jurij in si jo tako pridobi za ženo. Izidor odide v vojsko in prepusti posestvo bratu.", 2005, 204, true));
      kopijeKnjig.add(new KopijaKnjiga(52, "Visoška kronika", "To je kronika o družini Kalan, ki jo je Ivan Tavčar začel pisati potem, ko je bil že skoraj četrt stoletja lastnik poljanske graščine Visoko. Oče Polikarp je med vračanjem s tridesetletne vojne ubil prijatelja in se polastil ukradene vojaške blagajne. S tem denarjem si je kupil lepo posestvo, vendar zaradi občutka krivde ni našel sreče. Na smrtni postelji je starejšemu sinu Izidorju naročil, naj gre poiskati hčerko umorjenega prijatelja Agato, jo pripelje na Visoko in jo vzame za ženo. Agato zavrnjeni sosed Marks obsodi čarovništva. Da bi dokazala svojo nedolžnost, mora prebresti deročo reko. Ko jo sredi reke zajame močan tok, jo reši Izidorjev brat Jurij in si jo tako pridobi za ženo. Izidor odide v vojsko in prepusti posestvo bratu.", 2005, 204, true));
      kopijeKnjig.add(new KopijaKnjiga(53, "Visoška kronika", "To je kronika o družini Kalan, ki jo je Ivan Tavčar začel pisati potem, ko je bil že skoraj četrt stoletja lastnik poljanske graščine Visoko. Oče Polikarp je med vračanjem s tridesetletne vojne ubil prijatelja in se polastil ukradene vojaške blagajne. S tem denarjem si je kupil lepo posestvo, vendar zaradi občutka krivde ni našel sreče. Na smrtni postelji je starejšemu sinu Izidorju naročil, naj gre poiskati hčerko umorjenega prijatelja Agato, jo pripelje na Visoko in jo vzame za ženo. Agato zavrnjeni sosed Marks obsodi čarovništva. Da bi dokazala svojo nedolžnost, mora prebresti deročo reko. Ko jo sredi reke zajame močan tok, jo reši Izidorjev brat Jurij in si jo tako pridobi za ženo. Izidor odide v vojsko in prepusti posestvo bratu.", 2005, 204, true));
      kopijeKnjig.add(new KopijaKnjiga(54, "Visoška kronika", "To je kronika o družini Kalan, ki jo je Ivan Tavčar začel pisati potem, ko je bil že skoraj četrt stoletja lastnik poljanske graščine Visoko. Oče Polikarp je med vračanjem s tridesetletne vojne ubil prijatelja in se polastil ukradene vojaške blagajne. S tem denarjem si je kupil lepo posestvo, vendar zaradi občutka krivde ni našel sreče. Na smrtni postelji je starejšemu sinu Izidorju naročil, naj gre poiskati hčerko umorjenega prijatelja Agato, jo pripelje na Visoko in jo vzame za ženo. Agato zavrnjeni sosed Marks obsodi čarovništva. Da bi dokazala svojo nedolžnost, mora prebresti deročo reko. Ko jo sredi reke zajame močan tok, jo reši Izidorjev brat Jurij in si jo tako pridobi za ženo. Izidor odide v vojsko in prepusti posestvo bratu.", 2005, 204, true));

      //povezava knjige Visoska kronika
      kopijeKnjig.get(14).setParent(knjige.get(4));
      kopijeKnjig.get(15).setParent(knjige.get(4));
      kopijeKnjig.get(16).setParent(knjige.get(4));
      kopijeKnjig.get(17).setParent(knjige.get(4));
      kopijeKnjig.get(18).setParent(knjige.get(4));
      knjige.get(4).addKopija(kopijeKnjig.get(14));
      knjige.get(4).addKopija(kopijeKnjig.get(15));
      knjige.get(4).addKopija(kopijeKnjig.get(16));
      knjige.get(4).addKopija(kopijeKnjig.get(17));
      knjige.get(4).addKopija(kopijeKnjig.get(18));




      ArrayList<Izposoja> izposoje = new ArrayList<>();
      izposoje.add(new Izposoja(LocalDate.of(2021, 2, 7), LocalDate.of(2021, 3, 5), false, false));
      izposoje.add(new Izposoja(LocalDate.of(2021, 2, 7), LocalDate.of(2021, 3, 7), false, false));
      izposoje.add(new Izposoja(LocalDate.of(2021, 2, 7), LocalDate.of(2021, 3, 10), false, false));
      izposoje.add(new Izposoja(LocalDate.of(2021, 2, 7), null, false, false));
      izposoje.add(new Izposoja(LocalDate.of(2021, 2, 7), null, false, false));
      izposoje.add(new Izposoja(LocalDate.of(2021, 2, 7), null, false, true));

      //povezave tujec 1
      izposoje.get(0).setStranka(stranke.get(0));
      izposoje.get(0).setKopijaKnjiga(kopijeKnjig.get(0));
      stranke.get(0).addIzposoja(izposoje.get(0));
      kopijeKnjig.get(0).addIzposoja(izposoje.get(0));
      //povezave ISC2
      izposoje.get(1).setStranka(stranke.get(2));
      izposoje.get(1).setKopijaKnjiga(kopijeKnjig.get(6));
      stranke.get(2).addIzposoja(izposoje.get(1));
      kopijeKnjig.get(6).addIzposoja(izposoje.get(1));
      //povezave IT 4
      izposoje.get(2).setStranka(stranke.get(2));
      izposoje.get(2).setKopijaKnjiga(kopijeKnjig.get(13));
      stranke.get(2).addIzposoja(izposoje.get(2));
      kopijeKnjig.get(13).addIzposoja(izposoje.get(2));


      /** inicializacija */
      KKIzposojaKnjige kk1 = new KKIzposojaKnjige(stranke, knjige, kopijeKnjig);
      ZMStrankaSiIzposodiKnjigo tok = new ZMStrankaSiIzposodiKnjigo(kk1, prijava, ime, priimek, geslo, user, status, buttonKnjige1,
              tabela, isci, iskalnoPolje, opis, knjigaNaVoljo, izposodi);
      tok.strankaSePrijavi();
   }
}