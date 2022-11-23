package hello.tis.specificextensionblocking.api.domain;

import java.util.List;

/**
 * Extension 저장소 인터페이스입니다. Extension 은 크게 고정 Extension 과 커스텀 Extension 으로 나뉩니다.
 */
public interface ExtensionRepository {

  List<Extension> findAll();
}
