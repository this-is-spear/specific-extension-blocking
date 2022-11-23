package hello.tis.specificextensionblocking.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 웹 브라우저를 통해 문서에 접근할 수 있도록 설정한 파일입니다.
 */
@Configuration
public class DocumentationConfigurer implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/docs")
        .addResourceLocations("classpath:/static/docs/");
  }
}
