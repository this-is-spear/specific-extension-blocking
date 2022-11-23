package hello.tis.specificextensionblocking.api.infra;

import hello.tis.specificextensionblocking.api.domain.Extension;
import hello.tis.specificextensionblocking.api.domain.ExtensionRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA 로 구현한 Extension 저장소입니다.
 */
public interface JpaExtensionRepository extends JpaRepository<Extension, Long>,
    ExtensionRepository {

  @Override
  List<Extension> findAll();
}
