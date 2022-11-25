package hello.tis.specificextensionblocking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 특정 확장자를 제한하기 위해 만들어진 서비스입니다.
 */
@EnableJpaAuditing
@SpringBootApplication
public class SpecificExtensionBlockingApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpecificExtensionBlockingApplication.class, args);
  }

}
