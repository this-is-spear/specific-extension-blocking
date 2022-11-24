package hello.tis.specificextensionblocking.api.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import hello.tis.specificextensionblocking.api.exception.ExtensionNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ExtensionTest {

  @Test
  @DisplayName("타입은 고정(Fixed), 커스텀(Custom) 둘 중 하나의 정보를 가진다.")
  void createExtension() {
    assertAll(
        () -> assertDoesNotThrow(() -> new FixedExtension("sh")),
        () -> assertDoesNotThrow(() -> new CustomExtension("exe"))
    );
  }

  @Test
  @DisplayName("설정된 확장자는 확장자 정보(Extension)를 가진다.")
  void createConfiguredExtension() {
    FixedExtension extension = new FixedExtension("sh");
    assertDoesNotThrow(() -> new ConfiguredExtension(extension));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @DisplayName("확장자명이 비어있거나 nulL 이면 ExtensionNameException 가 발생한다.")
  void createExtension_nullOrEmpty(String 확장자명) {
    assertAll(
        () -> assertThatThrownBy(() -> new FixedExtension(확장자명))
            .isInstanceOf(ExtensionNameException.class),
        () -> assertThatThrownBy(() -> new CustomExtension(확장자명))
            .isInstanceOf(ExtensionNameException.class)
    );
  }

  @Test
  @DisplayName("확장자명(Name)은 20글자가 넘어서면 ExtensionNameException 가 발생한다.")
  void createExtension_over20() {
    String 너무_긴_확장자명 = "dasfdsfasfsfsafhafasfasfaslkfjhashfjkgasjfgasjhfahjskgfhajsfgkhasjfgksajhfg";

    assertAll(
        () -> assertThatThrownBy(() -> new FixedExtension(너무_긴_확장자명))
            .isInstanceOf(ExtensionNameException.class),
        () -> assertThatThrownBy(() -> new CustomExtension(너무_긴_확장자명))
            .isInstanceOf(ExtensionNameException.class)
    );
  }

  @ParameterizedTest
  @ValueSource(strings = {"한글도 안됩니다", "ddsadf?", "asd3asdfg", "sd.", "\\:_+"})
  @DisplayName("확장자명(Name)가 영문자로만 구성되지 않으면 ExtensionNameException 가 발생한다.")
  void createExtension_includeSpecialCharacters(String 영문자만_포함되지_않은_문자) {
    assertAll(
        () -> assertThatThrownBy(() -> new FixedExtension(영문자만_포함되지_않은_문자))
            .isInstanceOf(ExtensionNameException.class),
        () -> assertThatThrownBy(() -> new CustomExtension(영문자만_포함되지_않은_문자))
            .isInstanceOf(ExtensionNameException.class)
    );
  }

  @ParameterizedTest
  @ValueSource(strings = {"ABC", "Avc", "aVc"})
  @DisplayName("대분자로 들어와도 모두 소문자로 변경되어 저장된다.")
  void name(String 대문자가_포함된_영문자) {
    String 소문자로_변경된_영문자 = 대문자가_포함된_영문자.toLowerCase();
    CustomExtension extension = new CustomExtension(대문자가_포함된_영문자);
    assertThat(extension.getName()).isEqualTo(소문자로_변경된_영문자);
  }
}
