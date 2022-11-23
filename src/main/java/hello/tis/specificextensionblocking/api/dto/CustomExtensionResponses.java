package hello.tis.specificextensionblocking.api.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

/**
 * 커스텀 확장자 응답 값입니다. 이름 정보를 포함합니다.
 */
public class CustomExtensionResponses {

  private final List<CustomExtensionResponse> customExtensionResponses;

  public CustomExtensionResponses(List<CustomExtensionResponse> customExtensionResponses) {
    this.customExtensionResponses = customExtensionResponses.stream().sorted().collect(Collectors.toList());
  }

  public List<CustomExtensionResponse> getCustomExtensionResponses() {
    return new ArrayList<>(customExtensionResponses);
  }
}
