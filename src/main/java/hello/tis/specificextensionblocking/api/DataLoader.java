package hello.tis.specificextensionblocking.api;

import hello.tis.specificextensionblocking.api.domain.ExtensionRepository;
import hello.tis.specificextensionblocking.api.domain.FixedExtension;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 초기 데이터를 저장하기 위한 Loader입니다. 스프링이 시작하고 바로 데이터가 추가됩니다.
 */
@Component
@RequiredArgsConstructor
public class DataLoader {

  private final ExtensionRepository extensionRepository;

  public boolean isValid() {
    return extensionRepository.findAll().isEmpty();
  }

  /**
   * 데이터가 로드되는 영역입니다. 이곳에서 고정 확장자를 추가합니다.
   */
  public void loadData() {
    extensionRepository.save(new FixedExtension("bat"));
    extensionRepository.save(new FixedExtension("cmd"));
    extensionRepository.save(new FixedExtension("com"));
    extensionRepository.save(new FixedExtension("cpi"));
    extensionRepository.save(new FixedExtension("exe"));
    extensionRepository.save(new FixedExtension("scr"));
    extensionRepository.save(new FixedExtension("js"));
  }
}
