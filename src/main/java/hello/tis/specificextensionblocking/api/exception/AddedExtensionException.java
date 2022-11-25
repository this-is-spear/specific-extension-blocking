package hello.tis.specificextensionblocking.api.exception;

/**
 * 잘못된 확장자를 추가하려하면 예외가 발생합니다. 이미 설정된 확장자를 추가할 수 없습니다. 커스텀 확장자의 크기는 200이 넘으면 안됩니다.
 */
public class AddedExtensionException extends RuntimeException {

  public AddedExtensionException(String message) {
    super(message);
  }

  public static AddedExtensionException alreadyExtension() {
    return new AddedExtensionException("이미 설정된 확장자입니다.");
  }

  public static AddedExtensionException invalidSize() {
    return new AddedExtensionException("커스텀 사용자가 가득 찼습니다! 최대 200개 까지 저장 가능합니다.");
  }
}
