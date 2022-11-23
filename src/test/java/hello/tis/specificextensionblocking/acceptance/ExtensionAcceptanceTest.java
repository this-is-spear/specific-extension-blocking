package hello.tis.specificextensionblocking.acceptance;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
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
   * @When : 고정 확장자를 설정하면
   * @Then : 설정된 확장자에 확장자가 추가되고,
   * @When : 설정된 고정 확장자를 해제하면
   * @Then : 설정된 확장자에서 삭제된다.
   */
  @Test
  @Disabled
  void test1() throws Exception {
    final String 고정_확장자 = "sh";
    // when
    ResultActions 확장자_추가 = 확장자_추가(고정_확장자);
    확장자_추가.andExpect(status().isOk());

    // then
    String 설정_후_조회_결과 = 조회_요청(고정_확장자)
        .andReturn().getResponse().getContentAsString();

    // TODO : 결과 비교 구현
    System.out.println(설정_후_조회_결과);

    // when
    ResultActions 확장자_해제 = 확장자_해제(고정_확장자);
    확장자_해제.andExpect(status().isOk());

    // then
    String 해제_후_조회_결과 = 조회_요청(고정_확장자)
        .andReturn().getResponse().getContentAsString();

    // TODO : 결과 비교 구현
    System.out.println(해제_후_조회_결과);
  }

  /**
   * @When : 설정되지 않은 고정 확장자를 해제하려하면
   * @Then : 예외가 발생한다.
   */
  @Test
  @Disabled
  void test2() throws Exception {
    String 설정되지_않은_고정_확장자 = "exe";

    // when
    ResultActions 확장자_해제 = 확장자_해제(설정되지_않은_고정_확장자);

    // then
    확장자_해제.andExpect(status().is4xxClientError());
  }

  /**
   * @When : 설정된 고정 확장자를 설정하려하면
   * @Then : 예외가 발생한다.
   */
  @Test
  @Disabled
  void test3() throws Exception {
    // given
    String 설정된_고정_확장자 = "sh";
    ResultActions 확장자_추가_요청 = 확장자_추가(설정된_고정_확장자);
    확장자_추가_요청.andExpect(status().isOk());

    // when
    ResultActions 잘못된_확장자_추가_요청 = 확장자_추가(설정된_고정_확장자);

    // then
    잘못된_확장자_추가_요청.andExpect(status().is4xxClientError());
  }

  /**
   * @When : 커스텀 확장자를 추가하면
   * @Then : 설정된 확장자에 확장자가 추가되고,
   * @When : 커스텀 확장자를 삭제하면
   * @Then : 설정된 확장자에서 삭제된다.
   */
  @Test
  @Disabled
  void test4() throws Exception {
    String 커스텀_확장자 = "yml";
    // when
    ResultActions 확장자_추가 = 확장자_추가(커스텀_확장자);
    확장자_추가.andExpect(status().isOk());

    // then
    String 설정_후_조회_결과 = 조회_요청(커스텀_확장자)
        .andReturn().getResponse().getContentAsString();

    // TODO : 결과 비교 구현
    System.out.println(설정_후_조회_결과);

    // when
    ResultActions 확장자_해제 = 확장자_해제(커스텀_확장자);
    확장자_해제.andExpect(status().isOk());

    // then
    String 해제_후_조회_결과 = 조회_요청(커스텀_확장자)
        .andReturn().getResponse().getContentAsString();

    // TODO : 결과 비교 구현
    System.out.println(해제_후_조회_결과);

  }

  /**
   * @When : 설정되지 않은 커스텀 확장자를 해제하려하면
   * @Then : 예외가 발생한다.
   */
  @Test
  @Disabled
  void test5() throws Exception {
    String 설정되지_않은_커스텀_확장자 = "c";

    // when
    ResultActions 확장자_해제 = 확장자_해제(설정되지_않은_커스텀_확장자);

    // then
    확장자_해제.andExpect(status().is4xxClientError());
  }

  /**
   * @When : 설정된 커스텀 확장자를 설정하려하면
   * @Then : 예외가 발생한다.
   */
  @Test
  @Disabled
  void test6() throws Exception {
    // given
    String 설정된_커스텀_확장자 = "yml";

    ResultActions 확장자_추가_요청 = 확장자_추가(설정된_커스텀_확장자);
    확장자_추가_요청.andExpect(status().isOk());

    // when
    ResultActions 잘못된_확장자_추가_요청 = 확장자_추가(설정된_커스텀_확장자);

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
