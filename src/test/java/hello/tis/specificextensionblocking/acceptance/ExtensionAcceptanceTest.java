package hello.tis.specificextensionblocking.acceptance;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ExtensionAcceptanceTest {

  private static final String 고정_확장자 = "sh";
  private static final String 커스텀_확장자 = "yml";
  private static final String 설정된_고정_확장자 = "sh";
  private static final String 설정되지_않은_고정_확장자 = "exe";
  private static final String 설정되지_않은_커스텀_확장자 = "c";
  private static final String 설정된_커스텀_확장자 = "yml";

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  private DatabaseCleanup databaseCleanup;

  @BeforeEach
  public void setUp() {
    databaseCleanup.execute();
  }

  /**
   * @When : 확장자를 설정하면
   * @Then : 설정된 확장자에 확장자가 추가되고,
   * @When : 설정된 확장자를 해제하면
   * @Then : 설정된 확장자에서 삭제된다.
   */
  @Disabled
  @ParameterizedTest
  @ValueSource(strings = {고정_확장자, 커스텀_확장자})
  void test1(String 확장자) throws Exception {
    // when
    ResultActions 확장자_추가 = 확장자_추가(확장자);
    확장자_추가.andExpect(status().isOk());

    // then
    String 설정_후_조회_결과 = 조회_요청(확장자)
        .andReturn().getResponse().getContentAsString();

    // TODO : 결과 비교 구현
    System.out.println(설정_후_조회_결과);

    // when
    ResultActions 확장자_해제 = 확장자_해제(확장자);
    확장자_해제.andExpect(status().isOk());

    // then
    String 해제_후_조회_결과 = 조회_요청(확장자)
        .andReturn().getResponse().getContentAsString();

    // TODO : 결과 비교 구현
    System.out.println(해제_후_조회_결과);
  }

  /**
   * @When : 설정되지 않은 확장자를 해제하려하면
   * @Then : 예외가 발생한다.
   */
  @Disabled
  @ParameterizedTest
  @ValueSource(strings = {설정되지_않은_고정_확장자, 설정되지_않은_커스텀_확장자})
  void test2(String 설정되지_않은_확장자) throws Exception {

    // when
    ResultActions 확장자_해제 = 확장자_해제(설정되지_않은_확장자);

    // then
    확장자_해제.andExpect(status().is4xxClientError());
  }

  /**
   * @When : 설정된 확장자를 설정하려하면
   * @Then : 예외가 발생한다.
   */
  @Disabled
  @ParameterizedTest
  @ValueSource(strings = {설정된_고정_확장자, 설정된_커스텀_확장자})
  void test3(String 설정된_확장자) throws Exception {
    // given
    ResultActions 확장자_추가_요청 = 확장자_추가(설정된_확장자);
    확장자_추가_요청.andExpect(status().isOk());

    // when
    ResultActions 잘못된_확장자_추가_요청 = 확장자_추가(설정된_확장자);

    // then
    잘못된_확장자_추가_요청.andExpect(status().is4xxClientError());
  }


  private ResultActions 조회_요청(String extension) throws Exception {
    return mockMvc.perform(
        MockMvcRequestBuilders.get("/extensions")
            .contentType(MediaType.APPLICATION_JSON)
            .content("\"name\":\"" + extension + "\"")
    ).andExpect(status().isOk());
  }

  private ResultActions 확장자_추가(String extension) throws Exception {
    return mockMvc.perform(
        MockMvcRequestBuilders.post("/extensions")
            .contentType(MediaType.APPLICATION_JSON)
            .content("\"name\":\"" + extension + "\"")
    );
  }

  private ResultActions 확장자_해제(String extension) throws Exception {
    return mockMvc.perform(
        MockMvcRequestBuilders.delete("/extensions")
            .contentType(MediaType.APPLICATION_JSON)
            .content("\"name\":\"" + extension + "\"")
    );
  }

}
