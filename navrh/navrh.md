# Návrh systému

* [Use case diagram](#Use-case-diagram)
* [Use case specifikace](#Use-case-specifikace)

## Use case diagram

<div align="center"><img src="https://github.com/ondrejkozel/ristral/blob/navrh/navrh/ristral-use-case-diagram.png?raw=true" alt="Use case diagram"/></div>

## Use case specifikace

### UC 1 Registrovat se

#### Krátký popis
Use case umožní uživateli se registrovat do systému.

#### Podmínky pro spuštění
* Uživatel není přihlášený.

#### Základní tok
1. Uživatel vyplní svoje uživatelské jméno, email a heslo.
2. Systém zvaliduje data od uživatele.
3. Systém uloží nového uživatele.

#### Alternativní tok 1
1. Pokud uživatel zvolil registraci organizace, vyplňuje i název organizace.

#### Alternativní tok 2
1. Pokud v 2. bodě základního toku systém zjistí neplatné vstupní údaje, systém ho na to upozorní a nedovolí vytvořit nového uživatele.
2. Tok pokračuje 1. bodem základního toku.

#### Podmínky pro dokončení
* Nový uživatel je úspěšně uložen v databázi.

### UC 2 Poslat zpětnou vazbu

#### Krátký popis
Uživatel má pomocí tohoto UC možnost kontaktovat vývojáře jednoduchým formulářem.

#### Základní tok
1. Uživatel vyplní svůj email a zprávu.
2. Systém odešle zprávu na email vývojáře.
3. Systém odešle kopii zprávy na email uživatele.

#### Alternativní tok
1. Pokud je uživatel přihlášený, mailovou adresu za něj vyplní systém.

#### Podmínky pro dokončení
* E-mailová zpráva vývojáři byla úspěšně odeslána.

### UC 3 Prohlížet zastávky, linky a vozidla

#### Krátký popis
Use case umožní uživateli zobrazit si přehlednou tabulku zastávek, linek a vozidel a po rozkliknutí objektu zobrazí
detailnější informace. **Pozn.:** u administrátora organizace a jeho potomků je tento UC přepsán případem užití
*[UC 8 Spravovat zastávky, linky, vozidla a typy vozidel](#UC-8-Spravovat-zastávky,-linky,-vozidla-a-typy-vozidel)*.

#### Podmínky pro spuštění
* Databáze není prázdná.
* Uživatel je přihlášený řadovým účtem organizace.

#### Základní tok
1. Systém načte objekty z databáze.
2. Systém vypíše seznam objektů.

#### Alternativní tok
1. Pokud uživatel rozklikne objekt, systém zobrazí podrobnější informace.

### UC 4 Prohlížet vlastní uskutečněné jízdy

#### Krátký popis
Uživatel si může zobrazit přehled vlastních uskutečněných jízd. Součástí přehledu by měl být seznam proběhlých jízd
a souhrn krátkých informací a statistik o posledních proběhlých jízdách.

#### Podmínky pro spuštění
* Uživatel je přihlášený.

#### Základní tok
1. Systém načte uskutečněné jízdy z databáze.
2. Systém vypočítá statistiky posledních jízd.
3. Systém zobrazí statistiky a informace o posledních jízdách a seznam proběhlých jízd.

#### Alternativní tok
1. Pokud uživatel neuskutečnil ani jednu jízdu, systém ho na to upozorní a statistiky ani seznam nezobrazuje.

### UC 5 Kontaktovat administrátora

#### Krátký popis
Uživateli řadového účtu organizace a jeho potomkům mimo uživatele osobního účtu umožňuje pomocí jednoduchého formuláře
kontaktovat administrátora/superadministrátora organizace.

#### Podmínky pro spuštění
* Je přihlášený uživatel řadového účtu organizace nebo jeho potomek vyjma uživatele osobního účtu.

#### Základní tok
1. Systém načte seznam administrátorů a superadministrátorů z databáze.
2. Systém zobrazí formulář s combo boxem adresátů a polem pro zadání textu.
3. Uživatel vyplní zprávu a potvrdí.
4. Systém odešle zprávu emailem adresátovi.

#### Podmínky pro dokončení
* Zpráva byla úspěšně odeslána na email adresovaného (super)administrátora.

### UC 6 Jet jízdu

#### Krátký popis
Umožní uživateli (řidič) uskutečnit a podporovat ho při **naplánované** jízdě v terénu (uskutečněná jízda je ta, kterou
již uživatel (řidič) podle itineráře v naplánovaném čase odjel).

#### Podmínky pro spuštění
* Uživatel je přihlášený.
* Uživatelovo zařízení má přesně nastavený čas.
* **Pokud je offline**: naplánovaná jízda je uložena na uživatelově zařízení.

#### Základní tok
1. Systém podle času vybere jízdu, kterou má řidič v plánu uskutečnit.
2. Řidič zahájí jízdu.
3. Řidič postupně obslouží všechny zastávky, systém ho informuje o čase k dosažení příští zastávky a dalších skutečnostech.
4. Řidič dojede na konečnou zastávku a tím ukončí jízdu. Jízda je označena jako uskutečněná.
5. Systém uloží jízdu do vzdálené databáze.

#### Alternativní tok 1
1. Pokud je řidičovo zařízení offline, zůstane uskutečněná jízda uložena pouze na jeho zařízení. 
2. Až bude řidičovo zařízení online, systém uloží jízdu do vzdálené databáze.

#### Alternativní tok 2
1. Pokud je to vůlí řidiče a jsou dostupné dostatečně přesné polohovací služby, systém zobrazuje i mapu s aktuální polohou, případně polohou zastávek.

#### Podmínky pro dokončení
* Naplánovaná jízda je ve vzdálené databázi úspěšně přepsaná uskutečněnou jízdou.

### UC 7 Plánovat jízdy

#### Krátký popis
Umožňuje uživateli prohlížet, vytvářet, upravovat a mazat plánované jízdy. V případě účtu organizace spravuje plánované
jízdy řadovému uživateli (super)administrátor.

#### Podmínky pro spuštění – zobrazení, vytvoření, upravení i odstranění
* Je přihlášený administrátor organizace nebo jeho potomek.

***

#### Základní tok – zobrazení
1. Systém načte všchny plánované jízdy z databáze.
2. Systém zobrazí plánované jízdy.

#### Alternativní tok – zobrazení
1. Pokud uživatel rozklikne plánovanou jízdu, systém zobrazí podrobnější informace.

***

#### Základní tok – vytvoření
1. Systém zobrazí formůlář pro vytvoření plánování jízdy.
2. Uživatel zadá hodnoty.
3. Systém zvaliduje data zadaná uživatelem.
4. Data jsou uložena do vzdálené databáze.

#### Alternativní tok – vytvoření
1. Pokud hodnoty zadané uživatelem nejsou validní, systém uživatele upozorní a nedovolí uložení.
2. Tok pokračuje 2. bodem základního toku.

***

#### Základní tok – upravení
1. Uživatel vybere naplánovanou jízdu k úpravě.
2. Systém zobrazí formulář pro úpravu plánované jízdy.
3. Uživatel upraví hodnoty.
4. System zvaliduje data upravená uživatelem.
5. Data jsou uložena do vzdálené databáze.

#### Alternativní tok – upravení
1. Pokud hodnoty upravené uživatelem nejsou validní, systém uživatele upozorní a nedovolí uložení.
2. Tok pokračuje 3. bodem základního toku.

***

#### Základní tok – odstranění
1. Uživatel vybere naplánovanou jízdu ke smazání.
2. Systém se ujistí, jestli uživatel opravdu chce odstranit naplánovanou jízdu.
3. Naplánovaná jízda je smazána z databáze.

#### Alternativní tok – odstranění
1. Pokud si uživatel rozmyslí odstranění naplánované jízdy v 2. bodě základního toku, 3. bod základního toku se nevykoná. 

***

#### Podmínky pro dokončení – vytvoření, upravení a odstranění
* Všechny změny jsou úspěšně uložené.
