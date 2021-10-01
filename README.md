# Ristral

Ristral bude jednoduchý informační systém dopravního podniku. Hlavním cílem je, aby systém splňoval požadavky pro 
zajištění plynulého chodu dopravní společnosti (databáze zastávek, linek, vozidel, řidičů; plánování jízd a přidělování
řidičům; statistiky provozu a možné optimalizace). Ristral bude přístupný jako webová aplikace jednotlivcům stejně jako
organizacím a to i pro komerční použití. **Návrh systému** naleznete na [této stránce](https://github.com/ondrejkozel/ristral/blob/dev/navrh/navrh.md).

## Použité technologie 

Ristral je psaný v **Javě**. Bude využívat frameworky **[Spring Boot](https://spring.io/projects/spring-boot)**
a **[Vaadin](https://vaadin.com/)** pro rychlý vývoj webových aplikací. Ristral bude **PWA**. Aplikace bude připojená
k relační databázi a bude používat **objektově relační mapování** pomocí
**[Java Persistence API](https://cs.wikipedia.org/wiki/Java_Persistence_API)** a jeho implementace
**[Hibernate](https://cs.wikipedia.org/wiki/Hibernate)**.

## Aktuální fáze vývoje

Poslední přidané funkcionality můžete sledovat ve větvi **[dev](https://github.com/ondrejkozel/ristral/tree/dev)**.
Stav práce na projektu můžete sledovat na [nástěnce](https://github.com/ondrejkozel/ristral/projects/1).
