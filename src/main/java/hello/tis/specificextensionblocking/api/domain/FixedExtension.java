package hello.tis.specificextensionblocking.api.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 고정 확장자입니다. 확장자를 상속받고 있습니다.
 */
@Entity
@Getter
@DiscriminatorValue("FIXED")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FixedExtension extends Extension {

  public FixedExtension(String name) {
    super(name);
  }
}
