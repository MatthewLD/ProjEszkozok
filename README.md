<h1>Projekt eszközök - beadandó.</h1>
<h2>1. A beadandó témája</h2>
Jelen szoftver egy leegyszerűsített szerepjátékot valósít meg. A játékos irányíthat egy általa létrehozott karaktert egy képzeletbeli világban, ahol más élőlények ellen küzdhet meg és szerezhet tapasztalatot, vagy barátságos lény esetén barátkozhat vele, esetleg missziókat vállalhat.
<h3>2. Dokumentáció</h3>
<h4>2.1. Megvalósítás</h4>
A szoftver kétféle "megoldással" született: kódolásos, illetve azon kívül eső, de szintén számítógépes munkával.
<h5>2.1.1. Fejlesztőkörnyezet</h5>
A programkód a NetBeans IDE 8.2-es verziójával készült teljes egészében. Az elkészült programkódok ezután lettek commit-olva jelen platformra, a GitHUB-ra.<br />
A rajzok, és grafikák a Microsoft Paint rajzolóprogrammal készültek.
<h5>2.1.2. A szoftver megvalósítása</h5>
A program teljes egészében JAVA 8 programozási nyelvben készült. Az úgynevezett "back-end" (azaz a program működését végrehajtó algoritmusok és folyamatok) színtiszta JAVA nyelvben íródott, a GUI pedig a JAVA Swing segítségével készült. Egyéb programozási eszköz nem került felhasználásra.
<h4>2.2. A program működtetése</h4>
A szoftver a fordítás nélküli forrásfájlokat tartalmazza. Fordításuk és futtatásuk mindenképpen fejlesztőkörnyezettel ajánlott, mivel az egyes írott szövegek formázása miatt a parancssoros fordítás és futtatás csak meghiúsulna.
<h4>2.3. A program működése</h4>
A program induláskor a főmenübe lép, ahol van lehetőség a beállításokon belül felbontást váltani, illetve új, vagy meglévő játékmentést indítani. Eddig a pontig a GUI működik teljes egészében, a játék elindításakor indul el a "back-end" működése is.<br />
A GUI megvalósítása olyan módon történt meg, hogy egy nagy osztály felel a megjelenés törzséért, ez hozza létre, és inicializálja is az ablakot. Később a különböző cselekvési területek (például a főmenü) szabják testre maguknak, hogy csak a megfelelő funkciók legyenek elérhetők. A szoftver az ablakot csak végleges bezáráskor terminálja, működés közben végig ugyanazt az ablakot használja, tartalomváltásnál csak törli az elemeket, és ezután veszi fel az újakat.<br />
A back-end tartalmaz minden lényeges információt a megjelenéshez. Mindezeket a memóriában tárolja. A szoftver aktuális állapotát le lehet menteni különböző fájlokba, illetve onnan betöltésre is kerülhet állapot. Új állapot létrehozása esetén a szoftver rengeteg algoritmus segítségével hozza létre például a térképet, az azon található eszközöket és egyéb, nem a felhasználó által irányított karaktereket.
<h4>2.4. A szoftver hibái, esetleges javítási lehetőségek</h4>
<h5>2.4.1. Félkész állapot</h5>
A szoftver - a fejlesztők legnagyobb sajnálatára - jelenleg félkész állapotban van. A térkép vizuális megjelenítése nincs még megvalósítva, valamint a mentett játékállás betöltése sem teljesen működőképes.<br />
Az előzetes tervekben szerettünk volna történet módot is készíteni a játékhoz, ez a jelenlegi verzióban nincs jelen.
<h5>2.4.2. Optimalizálási hibák</h5>
A szoftver készítése folyamán igyekeztünk azt a lehető legjobban optimalizálni. Azonban vannak esetek, amikor sok időt vesz igénybe egy-egy művelet. Ilyen például a játékállás elmentése, amely a térkép méretei miatt akár 2 percet is igénybe vehet. Új állapot indítása esetén a térkép generálása akár 1 percet is igénybe vehet. Minden más funkció a felhasználói kényelmeknek megfelelő sebességgel történik.
<h4>2.5. Tesztesetek</h4>
A szoftverhez készült egy tesztelő osztály is, amely leginkább a "backend"-et hivatott tesztelni. Ez ellenőrzi, hogy a különböző osztályok, melyek az adatokat tárolják, mindezt helyesen teszik-e meg. A tesztelés folyamán minden jól zajlott. A tesztelő osztály a projekthez mellékelve lett.
