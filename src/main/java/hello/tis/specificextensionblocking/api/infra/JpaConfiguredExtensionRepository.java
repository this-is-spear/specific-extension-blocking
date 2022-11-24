package hello.tis.specificextensionblocking.api.infra;

import hello.tis.specificextensionblocking.api.domain.ConfiguredExtension;
import hello.tis.specificextensionblocking.api.domain.ConfiguredExtensionRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA 로 구현한 ConfiguredExtension 저장소입니다.
 */
public interface JpaConfiguredExtensionRepository extends JpaRepository<ConfiguredExtension, Long>,
    ConfiguredExtensionRepository {

  @Override
  List<ConfiguredExtension> findAll();

  @Override
  <S extends ConfiguredExtension> S save(S entity);

  @Override
  void delete(ConfiguredExtension entity);
}
