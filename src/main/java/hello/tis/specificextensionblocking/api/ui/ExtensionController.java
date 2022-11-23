package hello.tis.specificextensionblocking.api.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 확장자 관리 컨트롤러 입니다. API는 확장자 리스트 조회, 추가, 삭제가 있습니다.
 */
@RestController
@RequestMapping("extensions")
public class ExtensionController {

  /**
   * 고정 확장자와 커스텀 확장자의 리스트를 반환합니다.
   *
   * @return 200 OK
   */
  @GetMapping
  public ResponseEntity<ExtensionResponses> findExtensions() {
    ExtensionResponses extensionResponses = new ExtensionResponses();
    return ResponseEntity.ok(extensionResponses);
  }

  /**
   * 확장자를 등록합니다. 고정 확장자에 등록된 이름인 경우 커스텀 확장자로 인식합니다.
   *
   * @param extensionRequest 확장자의 이름을 입력받습니다.
   * @return 200 OK
   */
  @PostMapping
  public ResponseEntity<Void> addExtension(ExtensionRequest extensionRequest) {
    return ResponseEntity.ok().build();
  }

  /**
   * 확장자를 해제합니다. 고정 확장자일 경우 해제하고, 커스텀 확장자 일 경우 삭제합니다.
   *
   * @param extensionRequest 확장자의 이름을 입력받습니다.
   * @return 204 NO_CONTENT
   */
  @DeleteMapping
  public ResponseEntity<Void> clearExtension(ExtensionRequest extensionRequest) {
    return ResponseEntity.noContent().build();
  }

}
