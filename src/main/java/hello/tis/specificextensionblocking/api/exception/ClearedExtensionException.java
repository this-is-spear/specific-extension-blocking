package hello.tis.specificextensionblocking.api.exception;

/**
 * 해제하지 못하는 상황에서 해제하려는 경우 발생하는 문제입니다.
 */
public class ClearedExtensionException extends RuntimeException {

  public ClearedExtensionException(String message) {
    super(message);
  }

  public static ClearedExtensionException noDataExtension() {
    throw new ClearedExtensionException("존재하지 않는 확장자를 해제할 수 없습니다.");
  }

  public static ClearedExtensionException invalidExtension() {
    throw new ClearedExtensionException("이미 해제된 확장자입니다.");
  }
}
