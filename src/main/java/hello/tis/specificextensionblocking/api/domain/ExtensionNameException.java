package hello.tis.specificextensionblocking.api.domain;

/**
 * 확장자명이 20 글자가 넘거나 특수문자가 포함된 경우 예외가 발생합니다. null 이거나 비어있는 경우도 예외가 발생합니다.
 */
public class ExtensionNameException extends RuntimeException {

  public ExtensionNameException() {
    super();
  }
}
