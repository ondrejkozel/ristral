package cz.okozel.ristral.backend.repository;

import cz.okozel.ristral.backend.uzivatele.Uzivatel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UzivatelRepository extends JpaRepository<Uzivatel, Long> {
}
