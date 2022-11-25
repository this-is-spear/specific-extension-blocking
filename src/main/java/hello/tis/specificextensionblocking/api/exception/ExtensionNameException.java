package hello.tis.specificextensionblocking.api.exception;

/**
 * 확장자명이 20 글자가 넘거나 특수문자가 포함된 경우 예외가 발생합니다. null 이거나 비어있는 경우도 예외가 발생합니다.
 */
public class ExtensionNameException extends RuntimeException {

  public ExtensionNameException(String message) {
    super(message);
  }

  public static ExtensionNameException invalidStrings() {
    return new ExtensionNameException("유효하지 않은 문자열입니다. 영문자만 사용할 수 있습니다.");
  }

  public static ExtensionNameException invalidLength() {
    return new ExtensionNameException("유효하지 않은 길이입니다. 최대 20글자까지 가능합니다.");
  }

}
