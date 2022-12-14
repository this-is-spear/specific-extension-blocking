package hello.tis.specificextensionblocking.acceptance;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.tis.specificextensionblocking.api.DataLoader;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExtensionAcceptanceTest {

  private static final String 고정_확장자 = "exe";
  private static final String 커스텀_확장자 = "yml";
  private static final String 설정된_고정_확장자 = "exe";
  private static final String 설정되지_않은_확장자 = "notusedextension";
  private static final String 설정된_커스텀_확장자 = "yml";

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  private DatabaseCleanup databaseCleanup;

  @Autowired
  private DataLoader dataLoader;

  @BeforeEach
  public void setUp() {
    databaseCleanup.execute();
    dataLoader.loadData();
  }

  /**
   * @When : 고정 확장자를 설정하면
   * @Then : 설정된 고정 확장자에 확장자가 추가되고,
   * @When : 설정된 고정 확장자를 해제하면
   * @Then : 설정된 고정 확장자에서 삭제된다.
   */
  @Test
  void test1() throws Exception {
    // when
    ResultActions 확장자_추가 = 확장자_추가(고정_확장자);
    확장자_추가.andExpect(status().isOk());

    //then
    ResultActions 추가_후_조회 = 조회_요청();
    추가_후_조회.andExpect(content().string(
            Matchers.containsString("{\"name\":\"" + 고정_확장자 + "\",\"checked\":true}")
        )
    );

    // when
    ResultActions 확장자_해제 = 확장자_해제(고정_확장자);
    확장자_해제.andExpect(status().isNoContent());

    // then
    ResultActions 해제_후_조회 = 조회_요청();
    해제_후_조회.andExpect(content().string(
            Matchers.containsString("{\"name\":\"" + 고정_확장자 + "\",\"checked\":false}")
        )
    );
  }

  /**
   * @When : 커스텀 확장자를 설정하면
   * @Then : 설정된 커스텀 확장자에 확장자가 추가되고,
   * @When : 설정된 커스텀 확장자를 해제하면
   * @Then : 설정된 커스텀 확장자에서 삭제된다.
   */
  @Test
  void test4() throws Exception {
    // when
    ResultActions 확장자_추가 = 확장자_추가(커스텀_확장자);
    확장자_추가.andExpect(status().isOk());

    //then
    ResultActions 추가_후_조회 = 조회_요청();
    추가_후_조회.andExpect(content().string(Matchers.containsString("\"name\":\"" + 커스텀_확장자 + "\"")));

    // when
    ResultActions 확장자_해제 = 확장자_해제(커스텀_확장자);
    확장자_해제.andExpect(status().isNoContent());

    // then
    ResultActions 해제_후_조회 = 조회_요청();
    해제_후_조회.andExpect(
        content().string(Matchers.containsString("{\"customExtensionResponses\":[]}}")));
  }

  /**
   * @When : 설정되지 않은 확장자를 해제하려하면
   * @Then : 예외가 발생한다.
   */
  @Test
  void test2() throws Exception {
    // when
    ResultActions 확장자_해제 = 확장자_해제(설정되지_않은_확장자);

    // then
    확장자_해제.andExpect(status().is4xxClientError());
  }

  /**
   * @When : 설정된 확장자를 설정하려하면
   * @Then : 예외가 발생한다.
   */
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


  private ResultActions 조회_요청() throws Exception {
    return mockMvc.perform(
        MockMvcRequestBuilders.get("/extensions")
            .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isOk());
  }

  private ResultActions 확장자_추가(String extension) throws Exception {
    return mockMvc.perform(
        MockMvcRequestBuilders.post("/extensions")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"" + extension + "\"}")
    );
  }

  private ResultActions 확장자_해제(String extension) throws Exception {
    return mockMvc.perform(
        MockMvcRequestBuilders.delete("/extensions")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"" + extension + "\"}")
    );
  }

}


