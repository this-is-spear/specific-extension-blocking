package hello.tis.specificextensionblocking.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 잘못된 확장자를 추가하려하면 예외가 발생합니다. 이미 설정된 확장자를 추가할 수 없습니다. 커스텀 확장자의 크기는 200이 넘으면 안됩니다.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AddedExtensionException extends RuntimeException {

  public AddedExtensionException() {
    super();
  }
}
