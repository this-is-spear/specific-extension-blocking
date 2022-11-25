package hello.tis.specificextensionblocking.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Jpa 의 Auditing 관리해주는 구성 파일입니다. SpringBootApplication 에 설정하면 JPA를 활용한 테스트가 진행되지 않습니다.
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfigurer {

}
