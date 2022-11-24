package hello.tis.specificextensionblocking.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 고정 확장자 응답 값입니다. 이름과 포함 여부 정보를 가집니다.
 */
@Getter
@ToString
@RequiredArgsConstructor
public class FixedExtensionResponse {

  private final String name;
  private final boolean checked;
}
