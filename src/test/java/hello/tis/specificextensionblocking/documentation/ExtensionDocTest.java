package hello.tis.specificextensionblocking.documentation;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import hello.tis.specificextensionblocking.api.service.ExtensionService;
import hello.tis.specificextensionblocking.api.ui.CustomExtensionResponse;
import hello.tis.specificextensionblocking.api.ui.ExtensionController;
import hello.tis.specificextensionblocking.api.ui.ExtensionResponses;
import hello.tis.specificextensionblocking.api.ui.FixedExtensionResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

/**
 * 확장자 리스트 조회, 추가, 삭제를 문서화합니다.*
 */
@WebMvcTest(ExtensionController.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class ExtensionDocTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  ExtensionService extensionService;

  @Test
  void findExtension() throws Exception {
    // given
    List<CustomExtensionResponse> customExtensionResponses = List.of(
        new CustomExtensionResponse("md"),
        new CustomExtensionResponse("git"));
    List<FixedExtensionResponse> fixedExtensionResponses = List.of(
        new FixedExtensionResponse("sh", true),
        new FixedExtensionResponse("bash", true),
        new FixedExtensionResponse("exe", false)
    );

    ExtensionResponses value = new ExtensionResponses(
        fixedExtensionResponses,
        customExtensionResponses
    );

    // when
    when(extensionService.findAll()).thenReturn(value);

    // then
    MockHttpServletRequestBuilder builder = RestDocumentationRequestBuilders
        .get("/extensions")
        .accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(builder).andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andDo(
            document(
                "create",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters()
            )
        );
  }

  @Test
  void addExtension() {

  }

  @Test
  void deleteExtension() {

  }

  private OperationResponsePreprocessor getDocumentResponse() {
    return Preprocessors.preprocessResponse(Preprocessors.prettyPrint());
  }

  private OperationRequestPreprocessor getDocumentRequest() {
    return Preprocessors.preprocessRequest(
        Preprocessors.modifyUris()
            .scheme("http")
            .host("localhost")
            .removePort(),
        Preprocessors.prettyPrint()
    );
  }
}
