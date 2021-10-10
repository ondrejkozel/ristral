# Návrh systému

* [Use case diagram](#Use-case-diagram)
* [Use case specifikace](#Use-case-specifikace)

## Use case diagram

<div align="center"><img src="https://github.com/ondrejkozel/ristral/blob/dev/navrh/ristral-use-case-diagram.png?raw=true" alt="Use case diagram"/></div>

## Use case specifikace

### Registrovat se

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

### Obnovit heslo

#### Krátký popis
Umožní nepřihlášenému uživateli obnovit heslo, pokud ho zapomene.

#### Podmínky pro spuštění
* Uživatel není přihlášený.

#### Základní tok
1. Uživatel zadá do formuláře emailovou adresu a vyplní recaptcha.
2. Systém zvaliduje zadané údaje.
3. Na emailovou adresu zašle systém odkaz na obnovení hesla.

#### Alternativní tok
1. Pokud zadané údaje nejsou validní, tok se spouší znovu od bodu 1 základního toku.

#### Podmínky pro dokončení
* Email se podařilo odeslat na adresu uživatele.

### Poslat zpětnou vazbu

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

### Prohlížet zastávky, linky a vozidla

#### Krátký popis
Use case umožní uživateli zobrazit si přehlednou tabulku zastávek, linek a vozidel a po rozkliknutí objektu zobrazí
detailnější informace. **Pozn.:** u administrátora organizace a jeho potomků je tento UC přepsán případem užití
*[Spravovat zastávky, režimy obsluhy zastávek, linky, vozidla a typy vozidel](#spravovat-zastávky-režimy-obsluhy-zastávek-linky-vozidla-a-typy-vozidel)*.

#### Podmínky pro spuštění
* Databáze není prázdná.
* Uživatel je přihlášený řadovým účtem organizace.

#### Základní tok
1. Systém načte objekty z databáze.
2. Systém vypíše seznam objektů.

#### Alternativní tok
1. Pokud uživatel rozklikne objekt, systém zobrazí podrobnější informace.

### Prohlížet vlastní uskutečněné jízdy

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

### Kontaktovat administrátora

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

### Jet jízdu

#### Krátký popis
Umožní uživateli (řidič) uskutečnit a podporovat ho při **naplánované** jízdě v terénu (uskutečněná jízda je ta, kterou
již uživatel (řidič) podle itineráře v naplánovaném čase odjel).

#### Podmínky pro spuštění
* Uživatel je přihlášený.
* Uživatelovo zařízení má přesně nastavený čas.
* **Pokud je offline**: 
  * Naplánovaná jízda je uložena na uživatelově zařízení.
  * Systém má práva k zapisování a čtení dat na uživatelovo zařízení.

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

### Plánovat jízdy

#### Krátký popis
Umožňuje uživateli prohlížet, vytvářet, upravovat a mazat plánované jízdy. V případě účtu organizace spravuje plánované
jízdy řadovému uživateli (super)administrátor.

#### Podmínky pro spuštění – zobrazení, vytvoření, upravení i odstranění
* Je přihlášený administrátor organizace nebo jeho potomek.

***

#### Základní tok – zobrazení
1. Systém načte plánované jízdy z databáze.
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

### Spravovat zastávky, režimy obsluhy zastávek, linky, vozidla a typy vozidel

#### Krátký popis
Umožňuje uživateli prohlížet, vytvářet, upravovat a mazat zastávky, linky vozidla a typy vozidel (dále objekty).

#### Podmínky pro spuštění – zobrazení, vytvoření, upravení i odstranění
* Je přihlášený administrátor organizace nebo jeho potomek.

***

#### Základní tok – zobrazení
1. Systém načte objekty z databáze.
2. Systém zobrazí objekty v tabulce.

#### Alternativní tok – zobrazení
1. Pokud uživatel rozklikne objekt, systém zobrazí podrobnější informace.

***

#### Základní tok – vytvoření
1. Systém zobrazí formůlář pro vytvoření objektu.
2. Uživatel zadá hodnoty.
3. Systém zvaliduje data zadaná uživatelem.
4. Data jsou uložena do vzdálené databáze.

#### Alternativní tok – vytvoření
1. Pokud hodnoty zadané uživatelem nejsou validní, systém uživatele upozorní a nedovolí uložení.
2. Tok pokračuje 2. bodem základního toku.

***

#### Základní tok – upravení
1. Uživatel vybere objekt k úpravě.
2. Systém zobrazí formulář pro úpravu objektu.
3. Uživatel upraví hodnoty.
4. System zvaliduje data upravená uživatelem.
5. Data jsou uložena do vzdálené databáze.

#### Alternativní tok – upravení
1. Pokud hodnoty upravené uživatelem nejsou validní, systém uživatele upozorní a nedovolí uložení.
2. Tok pokračuje 3. bodem základního toku.

***

#### Základní tok – odstranění
1. Uživatel vybere objekt ke smazání.
2. Systém se ujistí, jestli uživatel opravdu chce objekt odstranit.
3. Objekt je smazán z databáze.

#### Alternativní tok – odstranění
1. Pokud si uživatel rozmyslí odstranění objektu v 2. bodě základního toku, 3. bod základního toku se nevykoná.

***

#### Podmínky pro dokončení – vytvoření, upravení a odstranění
* Všechny změny jsou úspěšně uložené.

### Sledovat aktivitu podřízených účtů

#### Krátký popis
Umožní (super)administrátorským účtům sledovat aktivitu účtů podřízených.

#### Podmínky pro spuštění
* Je přihlášený uživatel řadového účtu organizace nebo jeho potomek vyjma uživatele osobního účtu.

#### Základní tok
1. Systém načte aktivitu podřízených účtů z databáze.
2. Systém zobrazí aktivitu podřízených účtů v tabulce.

### Spravovat osobní informace

#### Krátký popis
Uživateli je umožněno spravovat svoje osobní údaje jako jméno, email, heslo,...

#### Podmínky pro spuštění
* Je přihlášený uživatel osobního účtu nebo superadministrátor organizace.

#### Základní tok
1. Systém načte údaje z databáze.
2. Systém pomocí formuláře umožní uživateli měnit osobní údaje.
3. Osobní údaje jsou zvalidovány.
4. Osobní údaje jsou uloženy do databáze.

#### Alternativní tok
1. Pokud údaje zadané uživatelem nejsou validní, systém ho na to upozorní.
2. Tok pokračuje druhým bodem základního toku.

#### Podmínky pro dokončení
* Změny byly úspěšně uloženy v databázi.

### Smazat účet

#### Krátký popis
Umožňuje oprávněným uživatelům nenávratně zavřít svůj účet.

#### Podmínky pro spuštění
* Je přihlášený uživatel osobního účtu nebo superadministrátor organizace.

#### Základní tok
1. Systém se ujistí, jestli uživatel opravdu chce smazat svůj účet.
2. Pokud ano, jsou smazána všechna data uživatele.

#### Alternativní tok
1. Pokud je uživatelem jediný superadministrátor v organizaci, smažou se všechna data a uživatelé organizace.

#### Podmínky pro dokončení
* Všechna související data byla odstraněna z databáze.

### Spravovat informace o organizaci

#### Krátký popis
Use case umožňuje superadministrátorovi organizace upravovat informace o společnosti.

#### Podmínky pro spuštění
* Je přihlášený superadministrátor organizace.

#### Základní tok
1. Systém zobrazí formulář pro úpravu informací o organizaci.
2. Uživatel upraví hodnoty.
3. Systém zvaliduje hodnoty zadané uživatelem.
4. Data se uloží do vzdálené databáze.

#### Alternativní tok
1. Pokud hodnoty upravené uživatelem nejsou validní, systém uživatele upozorní a nedovolí uložení.
2. Tok pokračuje 2. bodem základního toku.

#### Podmínky pro dokončení
* Změny byly úspěšně uloženy v databázi.

### Spravovat podřízené účty

#### Krátký popis
Umožňuje superadministrátorovi zobrazit, vytvořit, upravit, odstranit a změnit oprávnění podřízeného účtu.

#### Podmínky pro spuštění – zobrazení, vytvoření, upravení, odstranění i změna oprávnění
* Je přihlášený superadministrátor organizace.

***

#### Základní tok – zobrazení
1. Systém načte podřízené účty z databáze.
2. Systém zobrazí podřízené účty v tabulce.

#### Alternativní tok – zobrazení
1. Pokud uživatel rozklikne podřízené účty, systém zobrazí podrobnější informace.

***

#### Základní tok – vytvoření
1. Systém zobrazí formůlář pro vytvoření podřízeného účtu.
2. Uživatel zadá hodnoty.
3. Systém zvaliduje data zadaná uživatelem.
4. Podřízený účet je uložen do vzdálené databáze.

#### Alternativní tok – vytvoření
1. Pokud hodnoty zadané uživatelem nejsou validní, systém uživatele upozorní a nedovolí uložení.
2. Tok pokračuje 2. bodem základního toku.

***

#### Základní tok – upravení
1. Uživatel vybere podřízený účet k úpravě.
2. Systém zobrazí formulář pro úpravu podřízeného účtu.
3. Uživatel upraví hodnoty.
4. System zvaliduje data upravená uživatelem.
5. Data jsou uložena do vzdálené databáze.

#### Alternativní tok – upravení
1. Pokud hodnoty upravené uživatelem nejsou validní, systém uživatele upozorní a nedovolí uložení.
2. Tok pokračuje 3. bodem základního toku.

***

#### Základní tok – odstranění
1. Uživatel vybere podřízený účet ke smazání.
2. Systém se ujistí, jestli uživatel opravdu chce podřízený účet odstranit.
3. Podřízený účet je smazán z databáze.

#### Alternativní tok – odstranění
1. Pokud si uživatel rozmyslí odstranění podřízeného účtu v 2. bodě základního toku, 3. bod základního toku se nevykoná.

***

#### Základní tok – změna oprávnění
1. Uživatel vybere podřízený účet, kterému chce změnit oprávnění a vybere oprávnění.
2. Systém účtu změní oprávnění a změnu zapíše do databáze.

#### Alternativní tok – změna oprávnění
1. Pokud je uživatel jediný superadministrátor a chce si odstranit superadministrátorské oprávnění, systém změnu zamítne.

***

#### Podmínky pro dokončení – vytvoření, upravení, odstranění a změna oprávnění
* Všechny změny jsou úspěšně uložené ve vzdálené databázi.

### Poslat zprávu podřízeným účtům

#### Krátký popis
Umožní superadministrátorovi organizace posílat zprávy podřízeným účtům, které se pak zobrazí na domovské stránce všech
účtů dané organizace.

#### Podmínky pro spuštění
* Je přihlášený superadministrátor organizace.

#### Základní tok
1. Systém uživateli zobrazí formulář.
2. Uživatel vyplní formulář.
3. Systém zvaliduje formulář.
4. Zpráva je odeslána.

#### Alternativná tok
1. Pokud formulář v 3. bodě zakládního toku není validní, systém na to uživatele upozorní.
2. Tok pokračuje 2. bodem základního toku.

#### Podmínky pro dokončení
* Zpráva je úspěšně uložena v databázi.

### Smazat účet organizace

#### Krátký popis
Umožňuje superadministrátorovi smazat celou organizaci.

#### Podmínky pro spuštění
* Je přihlášený superadministrátor organizace.

#### Základní tok
1. Systém se ujistí, jestli superadministrátor opravdu chce smazat organizaci.
2. Pokud ano, jsou smazána všechna data organizace.

#### Podmínky pro dokončení
* Všechna související data byla odstraněna z databáze.
