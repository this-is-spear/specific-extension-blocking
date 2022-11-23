package hello.tis.specificextensionblocking.api.domain;

import java.util.List;

/**
 * ConfiguredExtension 저장소 인터페이스입니다.
 */
public interface ConfiguredExtensionRepository {

  List<ConfiguredExtension> findAll();
}
