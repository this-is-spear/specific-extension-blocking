package hello.tis.specificextensionblocking.api.domain;

import java.time.LocalDateTime;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 커스텀 확장자입니다. 확장자를 상속받고 있습니다.
 */
@Entity
@Getter
@DiscriminatorValue("CUSTOM")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomExtension extends Extension {

  @CreatedDate
  private LocalDateTime createdAt;

  public CustomExtension(String name) {
    super(name);
  }
}
