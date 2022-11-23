package hello.tis.specificextensionblocking.api.dto;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 확장자를 추가하기 위한 요청 값입니다. 확장자 이름 정보가 포함합니다.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class ExtensionRequest {

  private String name;

  public ExtensionRequest(String name) {
    this.name = name;
  }
}
