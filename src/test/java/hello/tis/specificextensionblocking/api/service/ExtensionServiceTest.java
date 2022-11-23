package hello.tis.specificextensionblocking.api.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import hello.tis.specificextensionblocking.api.domain.ConfiguredExtension;
import hello.tis.specificextensionblocking.api.domain.CustomExtension;
import hello.tis.specificextensionblocking.api.domain.FixedExtension;
import hello.tis.specificextensionblocking.api.dto.CustomExtensionResponse;
import hello.tis.specificextensionblocking.api.dto.ExtensionRequest;
import hello.tis.specificextensionblocking.api.dto.ExtensionResponses;
import hello.tis.specificextensionblocking.api.fake.FakeConfiguredExtensionRepository;
import hello.tis.specificextensionblocking.api.fake.FakeExtensionRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ExtensionServiceTest {

  private static final String 이미_설정된_고정_확장자 = "exe";
  private static final String 이미_설정된_커스텀_확장자 = "java";
  private static final String 설정되지_않은_고정_확장자 = "sh";
  private static final String 설정되지_않은_커스텀_확장자 = "img";
  private static final FixedExtension FIXED_SH = new FixedExtension(설정되지_않은_고정_확장자);
  private static final FixedExtension FIXED_EXE = new FixedExtension("exe");
  private static final FixedExtension FIXED_BASH = new FixedExtension("bash");
  private static final CustomExtension CUSTOM_YML = new CustomExtension("yml");
  private static final CustomExtension CUSTOM_JAVA = new CustomExtension(이미_설정된_커스텀_확장자);
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
  @DisplayName("고정확장자는 확장자명과 설정 되어있는지 여부를 포함한다.")
  void findAll() {
    assertDoesNotThrow(
        () -> extensionService.findAll()
    );
  }

  @Test
  @DisplayName("커스텀 확장자를 추가할 경우 가장 마지막에 배치된다.")
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

  @ParameterizedTest
  @ValueSource(strings = {설정되지_않은_고정_확장자, 설정되지_않은_커스텀_확장자})
  @DisplayName("확장자를 추가합니다.")
  void add(String 설정되지_않은_확장자명) {
    ExtensionRequest request = new ExtensionRequest(설정되지_않은_확장자명);
    assertDoesNotThrow(
        () -> extensionService.add(request)
    );
  }

  @ParameterizedTest
  @ValueSource(strings = {이미_설정된_고정_확장자, 이미_설정된_커스텀_확장자})
  @DisplayName("설정된 확장자(`CheckedExtension`)를 설정할 경우 설정 예외(AddedExtensionException`)가 발생한다.")
  void add_alreadyConfigured(String 확장자_명) {
    ExtensionRequest 이미_설정된_확장자 = new ExtensionRequest(확장자_명);

    Assertions.assertThatThrownBy(
        () -> extensionService.add(이미_설정된_확장자)
    ).isInstanceOf(AddedExtensionException.class);
  }

  @Test
  @DisplayName("커스텀 확장자(`CustomExtension`)의 최대 개수인 200을 넘어서면 추가 예외(`addedExtensionException`)가 발생한다.")
  void add_over200() {
    int size = 200 - (int) extensionRepository.findAll().stream()
        .filter(extension -> extension instanceof CustomExtension)
        .count();

    for (int i = 0; i < size; i++) {
      CustomExtension extension = new CustomExtension(
          "yy" + getRandomString() + getRandomString() + getRandomString()
      );
      extensionRepository.save(extension);
      configuredExtensionRepository.save(new ConfiguredExtension(extension));
    }

    ExtensionRequest 추가하려는_확장자 = new ExtensionRequest("newexetinsion");
    Assertions.assertThatThrownBy(
        () -> extensionService.add(추가하려는_확장자)
    ).isInstanceOf(AddedExtensionException.class);
  }

  private char getRandomString() {
    return (char) ((Math.random() * 26) + 65);
  }
}
