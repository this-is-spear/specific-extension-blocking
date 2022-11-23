package hello.tis.specificextensionblocking.api.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 빌드한 파일이 잘 동작하는지 확인하기 위한 컨트롤러입니다. 해당 클래스에는 더 이상 API를 추가하지 않습니다. 헬스 체크 URI 는 `/health` 입니다.
 */
@RestController
public class HealthController {

  @GetMapping("health")
  public ResponseEntity<String> check() {
    return ResponseEntity.ok("ok");
  }
}
