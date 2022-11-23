package hello.tis.specificextensionblocking.api.service;

import hello.tis.specificextensionblocking.api.dto.ExtensionRequest;
import hello.tis.specificextensionblocking.api.dto.ExtensionResponses;
import org.springframework.stereotype.Service;

/**
 * 확장자 블로킹 기능 비즈니스 로직을 구현하는 서비스입니다.
 */
@Service
public class ExtensionService {

  /**
   * 고정확장자(FixedExtension)는 확장자 이름(name)과 설정 되어있는지 여부(isChecked)를 포함하고, 커스텀 확장자(CustomExtension)는
   * 추가된 순서대로 확장자 이름(name)만 출력합니다.
   *
   * @return 고정확장자와 커스텀확장자 리스트
   */
  public ExtensionResponses findAll() {
    return null;
  }

  /**
   * 설정된 확장자(`CheckedExtension`)를 설정할 경우 설정 예외(`addedExtensionException`)가 발생하고, 커스텀
   * 확장자(`CustomExtension`)의 최대 개수인 200을 넘어서면 추가 예외(`addedExtensionException`)가 발생합니다.
   *
   * @param extensionRequest 확장명이 포함된 요청 값
   */
  public void add(ExtensionRequest extensionRequest) {

  }

  /**
   * 설정되지 않은 확장자(UncheckedExtension)를 해제할 경우 해제 예외(clearedExtensionException)가 발생하고 커스텀
   * 확장자(CustomExtension)인 경우 삭제합니다. 그리고 사용자가 설정한 확장자(ConfiguredExtension)를 삭제합니다.
   *
   * @param extensionRequest 확장명이 포함된 요청 값
   */
  public void clear(ExtensionRequest extensionRequest) {

  }
}
