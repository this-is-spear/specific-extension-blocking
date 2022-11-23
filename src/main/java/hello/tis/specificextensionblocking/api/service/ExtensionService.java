package hello.tis.specificextensionblocking.api.service;

import hello.tis.specificextensionblocking.api.domain.ConfiguredExtension;
import hello.tis.specificextensionblocking.api.domain.ConfiguredExtensionRepository;
import hello.tis.specificextensionblocking.api.domain.CustomExtension;
import hello.tis.specificextensionblocking.api.domain.Extension;
import hello.tis.specificextensionblocking.api.domain.ExtensionRepository;
import hello.tis.specificextensionblocking.api.domain.FixedExtension;
import hello.tis.specificextensionblocking.api.dto.CustomExtensionResponse;
import hello.tis.specificextensionblocking.api.dto.CustomExtensionResponses;
import hello.tis.specificextensionblocking.api.dto.ExtensionRequest;
import hello.tis.specificextensionblocking.api.dto.ExtensionResponses;
import hello.tis.specificextensionblocking.api.dto.FixedExtensionResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 확장자 블로킹 기능 비즈니스 로직을 구현하는 서비스입니다.
 */
@Service
@RequiredArgsConstructor
public class ExtensionService {

  private final ExtensionRepository extensionRepository;
  private final ConfiguredExtensionRepository configuredExtensionRepository;

  /**
   * 고정확장자(FixedExtension)는 확장자 이름(name)과 설정 되어있는지 여부(isChecked)를 포함하고, 커스텀 확장자(CustomExtension)는
   * 추가된 순서대로 확장자 이름(name)만 출력합니다.
   *
   * @return 고정확장자와 커스텀확장자 리스트
   */
  public ExtensionResponses findAll() {
    List<Extension> extensions = extensionRepository.findAll();
    List<ConfiguredExtension> configuredExtensions = configuredExtensionRepository.findAll();

    List<FixedExtensionResponse> fixedExtensionResponses = getFixedExtensionResponses(
        extensions, configuredExtensions);
    List<CustomExtensionResponse> customExtensionResponses = getCustomExtensionResponses(
        extensions, configuredExtensions);

    return new ExtensionResponses(fixedExtensionResponses,
        new CustomExtensionResponses(customExtensionResponses));
  }

  private List<CustomExtensionResponse> getCustomExtensionResponses(List<Extension> extensions,
      List<ConfiguredExtension> configuredExtensions) {
    List<CustomExtensionResponse> customExtensionResponses = new ArrayList<>();
    extensions.parallelStream().forEach(extension -> {
      if (extension instanceof CustomExtension) {
        CustomExtension customExtension = (CustomExtension) extension;
        if (configuredExtensions.stream()
            .anyMatch(configuredExtension -> configuredExtension
                .getExtension().getName()
                .equals(extension.getName()))
        ) {
          customExtensionResponses.add(new CustomExtensionResponse(customExtension.getName(),
              customExtension.getCreatedAt()));
        }
      }
    });
    return customExtensionResponses;
  }

  private List<FixedExtensionResponse> getFixedExtensionResponses(List<Extension> extensions,
      List<ConfiguredExtension> configuredExtensions) {
    List<FixedExtensionResponse> fixedExtensionResponses = new ArrayList<>();
    extensions.parallelStream().forEach(extension -> {
      if (extension instanceof FixedExtension) {
        FixedExtension fixedExtension = (FixedExtension) extension;
        fixedExtensionResponses.add(
            new FixedExtensionResponse(
                fixedExtension.getName(),
                configuredExtensions.stream()
                    .anyMatch(configuredExtension -> configuredExtension.getExtension().getName()
                        .equals(extension.getName()))
            )
        );
      }
    });
    return fixedExtensionResponses;
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
