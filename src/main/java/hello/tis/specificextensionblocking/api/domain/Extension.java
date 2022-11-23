package hello.tis.specificextensionblocking.api.domain;

import java.util.Objects;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

/**
 * 확장자 엔티티입니다. 확장자명과 확장자 타입을 가집니다.
 */
@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn
public abstract class Extension {

  private static final Pattern PATTERN = Pattern.compile("[a-zA-Z]*");

  @Id
  @Column(name = "extension_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;


  /**
   * 확장자는 이름이 비어있을 수 없습니다. 이름의 길이는 최대 20글자이며, 영문자 외에는 불가능합니다.
   *
   * @param id 식별자
   * @param name 이름
   */
  public Extension(Long id, String name) {
    validateNullAndEmpty(name);
    validateSpecialCharacters(name);
    validateOver20(name);
    name = name.toLowerCase();

    this.id = id;
    this.name = name;
  }

  /**
   * 확장자는 이름이 비어있을 수 없습니다. 이름의 길이는 최대 20글자이며, 영문자 외에는 불가능합니다.
   *
   * @param name 확장자명
   */
  public Extension(String name) {
    this(null, name);
  }

  private void validateOver20(String name) {
    if (name.length() > 20) {
      throw new ExtensionNameException();
    }
  }

  private void validateSpecialCharacters(String name) {
    if (!PATTERN.matcher(name).matches()) {
      throw new ExtensionNameException();
    }
  }

  private void validateNullAndEmpty(String name) {
    if (name == null || name.isBlank()) {
      throw new ExtensionNameException();
    }
  }
}
