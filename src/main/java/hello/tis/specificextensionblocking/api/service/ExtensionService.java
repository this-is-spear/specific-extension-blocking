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
import hello.tis.specificextensionblocking.api.exception.AddedExtensionException;
import hello.tis.specificextensionblocking.api.exception.ClearedExtensionException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  @Transactional(readOnly = true)
  public ExtensionResponses findAll() {
    List<Extension> extensions = extensionRepository.findAll();
    List<ConfiguredExtension> configuredExtensions = configuredExtensionRepository.findAll();

    List<FixedExtensionResponse> fixedExtensionResponses = getFixedExtensionResponses(
        extensions, configuredExtensions);
    List<CustomExtensionResponse> customExtensionResponses = getCustomExtensionResponses(
        extensions);

    return new ExtensionResponses(fixedExtensionResponses,
        new CustomExtensionResponses(customExtensionResponses));
  }

  /**
   * 설정된 확장자(`CheckedExtension`)를 설정할 경우 설정 예외(`addedExtensionException`)가 발생하고, 커스텀
   * 확장자(`CustomExtension`)의 최대 개수인 200을 넘어서면 추가 예외(`addedExtensionException`)가 발생합니다.
   *
   * @param extensionRequest 확장명이 포함된 요청 값
   */
  @Transactional
  public void add(ExtensionRequest extensionRequest) {
    List<ConfiguredExtension> configuredExtensions = configuredExtensionRepository.findAll();
    List<Extension> extensions = extensionRepository.findAll();
    alreadyConfigured(extensionRequest, configuredExtensions);
    isOver200(configuredExtensions);
    saveRepository(extensionRequest, extensions);
  }


  /**
   * 설정되지 않은 확장자(UncheckedExtension)를 해제할 경우 해제 예외(clearedExtensionException)가 발생하고 커스텀
   * 확장자(CustomExtension)인 경우 삭제합니다. 그리고 사용자가 설정한 확장자(ConfiguredExtension)를 삭제합니다.
   *
   * @param extensionRequest 확장명이 포함된 요청 값
   */
  @Transactional
  public void clear(ExtensionRequest extensionRequest) {
    Extension ex = extensionRepository.findByName(extensionRequest.getName())
        .orElseThrow(ClearedExtensionException::noDataExtension);

    if (ex instanceof CustomExtension) {
      extensionRepository.delete(ex);
    }

    ConfiguredExtension extension = configuredExtensionRepository.findAll().stream()
        .filter(
            configuredExtension -> configuredExtension.getExtension().getName()
                .equals(extensionRequest.getName())
        ).findFirst().orElseThrow(ClearedExtensionException::invalidExtension);

    configuredExtensionRepository.delete(extension);
  }

  private List<CustomExtensionResponse> getCustomExtensionResponses(List<Extension> extensions) {
    return extensions.parallelStream().filter(extension -> extension instanceof CustomExtension)
        .map(extension -> {
          CustomExtension customExtension = (CustomExtension) extension;
          return getCustomExtensionResponse(customExtension);
        }).collect(Collectors.toList());
  }

  private List<FixedExtensionResponse> getFixedExtensionResponses(List<Extension> extensions,
      List<ConfiguredExtension> configuredExtensions) {
    return extensions.parallelStream().filter(extension -> extension instanceof FixedExtension)
        .map(extension -> getFixedExtensionResponse(configuredExtensions, extension))
        .collect(Collectors.toList());
  }

  private CustomExtensionResponse getCustomExtensionResponse(CustomExtension customExtension) {
    return new CustomExtensionResponse(customExtension.getName(),
        customExtension.getCreatedAt());
  }

  private FixedExtensionResponse getFixedExtensionResponse(
      List<ConfiguredExtension> configuredExtensions,
      Extension extension) {
    return new FixedExtensionResponse(
        extension.getName(),
        containsConfiguredExtension(configuredExtensions, extension)
    );
  }

  private boolean containsConfiguredExtension(List<ConfiguredExtension> configuredExtensions,
      Extension extension) {
    return configuredExtensions.stream()
        .anyMatch(configuredExtension -> configuredExtension.getExtension().getName()
            .equals(extension.getName()));
  }


  private void alreadyConfigured(ExtensionRequest extensionRequest,
      List<ConfiguredExtension> configuredExtensions) {
    if (configuredExtensions.stream().anyMatch(
        configuredExtension -> configuredExtension.getExtension().getName()
            .equals(extensionRequest.getName()))) {
      throw AddedExtensionException.alreadyExtension();
    }
  }

  private void isOver200(List<ConfiguredExtension> configuredExtensions) {
    if (configuredExtensions.stream().filter(
            configuredExtension -> configuredExtension.getExtension() instanceof CustomExtension)
        .count() >= 200) {
      throw AddedExtensionException.invalidSize();
    }
  }

  private void saveRepository(ExtensionRequest extensionRequest, List<Extension> extensions) {
    Optional<Extension> extension = extensions.stream()
        .filter(ex -> ex instanceof FixedExtension)
        .filter(ex -> ex.getName().equals(extensionRequest.getName()))
        .findFirst();

    if (extension.isPresent()) {
      configuredExtensionRepository.save(new ConfiguredExtension(extension.get()));
      return;
    }

    CustomExtension customExtension = new CustomExtension(extensionRequest.getName());
    extensionRepository.save(customExtension);
    configuredExtensionRepository.save(new ConfiguredExtension(customExtension));
  }
}
