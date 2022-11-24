package hello.tis.specificextensionblocking.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 확장자명이 20 글자가 넘거나 특수문자가 포함된 경우 예외가 발생합니다. null 이거나 비어있는 경우도 예외가 발생합니다.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExtensionNameException extends RuntimeException {

  public ExtensionNameException() {
    super();
  }
}
