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

### UC 4 Prohlížet vlastní proběhlé jízdy

#### Krátký popis
Uživatel si může zobrazit přehled vlastních podrobných jízd. Součástí přehledu by měl být seznam proběhlých jízd
a souhrn krátkých informací a statistik o posledních proběhlých jízdách.

#### Podmínky pro spuštění
* Uživatel je přihlášený

#### Základní tok
1. Systém načte proběhlé jízdy z databáze.
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
