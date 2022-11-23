package hello.tis.specificextensionblocking.documentation;

import hello.tis.specificextensionblocking.api.service.ExtensionService;
import hello.tis.specificextensionblocking.api.ui.ExtensionController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * 확장자 리스트 조회, 추가, 삭제를 문서화합니다.*
 */
@ExtendWith(MockitoExtension.class)
class ExtensionDocTest {

  @InjectMocks
  ExtensionController extensionController;

  @MockBean
  ExtensionService extensionService;

  @Test
  void findExtension() {

  }

  @Test
  void addExtension() {

  }

  @Test
  void deleteExtension() {

  }
}
