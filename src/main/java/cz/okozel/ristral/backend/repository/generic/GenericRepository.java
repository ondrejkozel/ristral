package cz.okozel.ristral.backend.repository.generic;

import cz.okozel.ristral.backend.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<T extends AbstractEntity> extends JpaRepository<T, Long> {
}
