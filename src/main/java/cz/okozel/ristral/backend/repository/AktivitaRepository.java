package cz.okozel.ristral.backend.repository;

import cz.okozel.ristral.backend.aktivity.Aktivita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AktivitaRepository extends JpaRepository<Aktivita, Long> {
}
