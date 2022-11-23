package hello.tis.specificextensionblocking.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ExtensionAcceptanceTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  private DatabaseCleanup databaseCleanup;

  @BeforeEach
  public void setUp() {
    databaseCleanup.execute();
  }

  /**
   * @When : 고정 확장자를 설정하면
   * @Then : 설정된 확장자에 확장자가 추가되고,
   * @When : 설정된 고정 확장자를 해제하면
   * @Then : 설정된 확장자에서 삭제된다.
   */
  @Test
  @Disabled
  void test1() {
  }

  /**
   * @When : 설정되지 않은 고정 확장자를 해제하려하면
   * @Then : 예외가 발생한다.
   */
  @Test
  @Disabled
  void test2() {
  }

  /**
   * @When : 설정된 고정 확장자를 설정하려하면
   * @Then : 예외가 발생한다.
   */
  @Test
  @Disabled
  void test3() {
  }

  /**
   * @When : 커스텀 확장자를 추가하면
   * @Then : 설정된 확장자에 확장자가 추가되고,
   * @When : 커스텀 확장자를 삭제하면
   * @Then : 설정된 확장자에서 삭제된다.
   */
  @Test
  @Disabled
  void test4() {
  }

  /**
   * @When : 설정되지 않은 커스텀 확장자를 하제하려하면
   * @Then : 예외가 발생한다.
   */
  @Test
  @Disabled
  void test5() {
  }

  /**
   * @When : 설정된 커스텀 확장자를 설정하려하면
   * @Then : 예외가 발생한다.
   */
  @Test
  @Disabled
  void test6() {

  }

  /**
   * @When : 커스텀 확장자에 고정 확장자를 추가할 때,
   * @When : 고정 확장자가 설정되지 않았다면
   * @Then : 고정 확장자를 설정한다.
   */
  @Test
  @Disabled
  void test7() {

  }

  /**
   * @When : 커스텀 확장자에 고정 확장자를 추가할 때,
   * @When : 고정 확장자가 설정되어 있다면
   * @Then : 예외가 발생한다.
   */
  @Test
  @Disabled
  void test8() {

  }
}
