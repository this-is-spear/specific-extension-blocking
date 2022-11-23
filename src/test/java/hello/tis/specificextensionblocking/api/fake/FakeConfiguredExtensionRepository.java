package hello.tis.specificextensionblocking.api.fake;

import hello.tis.specificextensionblocking.api.domain.ConfiguredExtension;
import hello.tis.specificextensionblocking.api.domain.ConfiguredExtensionRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeConfiguredExtensionRepository implements ConfiguredExtensionRepository {

  private final Map<Long, ConfiguredExtension> configuredExtensionMap = new HashMap<>();

  private static Long sequence = 0L;

  public void save(ConfiguredExtension configuredExtension) {
    Long id = ++sequence;
    configuredExtensionMap.put(id, new ConfiguredExtension(id, configuredExtension.getExtension()));
  }

  @Override
  public List<ConfiguredExtension> findAll() {
    return new ArrayList<>(configuredExtensionMap.values());
  }
}
