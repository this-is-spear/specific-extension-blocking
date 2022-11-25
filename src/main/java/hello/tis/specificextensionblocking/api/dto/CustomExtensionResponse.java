package hello.tis.specificextensionblocking.api.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

/**
 * 커스텀 확장자 응답 값입니다. 이름 정보를 포함합니다.
 */
@Getter
@ToString(exclude = "createdAt")
public class CustomExtensionResponse implements Comparable<CustomExtensionResponse> {

  private final String name;
  private final LocalDateTime createdAt;

  public CustomExtensionResponse(String name, LocalDateTime createdAt) {
    this.name = name;
    this.createdAt = createdAt;
  }

  @Override
  public int compareTo(CustomExtensionResponse o) {
    return this.createdAt.compareTo(o.createdAt);
  }
}
