package hello.tis.specificextensionblocking.api.dto;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 확장자 리스트를 반환하기 위한 DTO 입니다.
 */
@Getter
@RequiredArgsConstructor
public class ExtensionResponses {

  private final List<FixedExtensionResponse> fixedExtensions;
  private final List<CustomExtensionResponse> customExtensions;
}
