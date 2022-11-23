package hello.tis.specificextensionblocking.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 커스텀 확장자 응답 값입니다. 이름 정보를 포함합니다.
 */
@Getter
@RequiredArgsConstructor
public class CustomExtensionResponse {
  private final String name;
}
