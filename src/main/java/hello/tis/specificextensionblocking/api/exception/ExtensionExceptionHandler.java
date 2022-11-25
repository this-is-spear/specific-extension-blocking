package hello.tis.specificextensionblocking.api.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 모든 예외 상황을 관리합니다.
 */
@RestControllerAdvice
public class ExtensionExceptionHandler {

  /**
   * 확장자와 관련된 예외 상황을 처리합니다.
   *
   * @param e 예외 상황에서 전달되는 메시지입니다.
   * @return 메시지는 문자열로 전송됩니다.
   */
  @ExceptionHandler(value = {AddedExtensionException.class, ClearedExtensionException.class,
      ExtensionNameException.class})
  public ResponseEntity<String> exceptExtension(RuntimeException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }
}
