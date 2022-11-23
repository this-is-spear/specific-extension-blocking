package hello.tis.specificextensionblocking.api.service;

import static org.junit.jupiter.api.Assertions.*;

import hello.tis.specificextensionblocking.api.domain.ConfiguredExtension;
import hello.tis.specificextensionblocking.api.domain.CustomExtension;
import hello.tis.specificextensionblocking.api.domain.FixedExtension;
import hello.tis.specificextensionblocking.api.dto.CustomExtensionResponse;
import hello.tis.specificextensionblocking.api.dto.ExtensionResponses;
import hello.tis.specificextensionblocking.api.fake.FakeConfiguredExtensionRepository;
import hello.tis.specificextensionblocking.api.fake.FakeExtensionRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExtensionServiceTest {

  private static final FixedExtension FIXED_SH = new FixedExtension("sh");
  private static final FixedExtension FIXED_EXE = new FixedExtension("exe");
  private static final FixedExtension FIXED_BASH = new FixedExtension("bash");
  private static final CustomExtension CUSTOM_YML = new CustomExtension("yml");
  private static final CustomExtension CUSTOM_JAVA = new CustomExtension("java");
  private final FakeExtensionRepository extensionRepository = new FakeExtensionRepository();
  private final FakeConfiguredExtensionRepository configuredExtensionRepository = new FakeConfiguredExtensionRepository();
  private ExtensionService extensionService;

  @BeforeEach
  void setUp() {
    extensionService = new ExtensionService(extensionRepository,
        configuredExtensionRepository);

    initExtensionRepository();
    initConfiguredExtensionRepository();
  }

  private void initExtensionRepository() {
    extensionRepository.saveFixed(FIXED_SH);
    extensionRepository.saveFixed(FIXED_EXE);
    extensionRepository.saveFixed(FIXED_BASH);
    extensionRepository.save(CUSTOM_YML);
    extensionRepository.save(CUSTOM_JAVA);
  }

  private void initConfiguredExtensionRepository() {
    configuredExtensionRepository.save(new ConfiguredExtension(FIXED_EXE));
    configuredExtensionRepository.save(new ConfiguredExtension(FIXED_BASH));
    configuredExtensionRepository.save(new ConfiguredExtension(CUSTOM_YML));
    configuredExtensionRepository.save(new ConfiguredExtension(CUSTOM_JAVA));
  }


  @Test
  @DisplayName("고정확장자는 확장자명과 설정 되어있는지 여부를 포함하고, 커스텀 확장자는 추가된 순서대로 확장자명만 출력한다.")
  void findAll() {
    assertDoesNotThrow(
        () -> extensionService.findAll()
    );
  }

  @Test
  @DisplayName("커스텀 확장자를 추가할 경우 가장 마지막에 배치됩니다.")
  void findAll_addCustomExtension() {
    CustomExtension 추가하는_확장자 = new CustomExtension("aab");
    extensionRepository.save(추가하는_확장자);
    configuredExtensionRepository.save(new ConfiguredExtension(추가하는_확장자));

    ExtensionResponses responses = extensionService.findAll();

    List<CustomExtensionResponse> customExtensionResponses = responses.getCustomExtensions()
        .getCustomExtensionResponses();

    Assertions.assertThat(
            customExtensionResponses.get(customExtensionResponses.size() - 1).getName())
        .isEqualTo(추가하는_확장자.getName());
  }
}
