package hello.tis.specificextensionblocking.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 데이터 로더가 정확하게 동작하는 지 확인합니다.
 */
@Component
@RequiredArgsConstructor
public class DataLoaderBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private final DataLoader dataLoader;

  /**
   * DataLoader 안에 데이터가 존재하는지 확인합니다.
   *
   * @param event the event to respond to START
   */
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (dataLoader.isValid()) {
      dataLoader.loadData();
    }
  }
}
