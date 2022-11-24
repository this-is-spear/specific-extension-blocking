package hello.tis.specificextensionblocking.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 해제하지 못하는 상황에서 해제하려는 경우 발생하는 문제입니다.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClearedExtensionException extends RuntimeException {

  public ClearedExtensionException() {
    super();
  }
}
