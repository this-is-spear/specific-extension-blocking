package hello.tis.specificextensionblocking.api.fake;

import hello.tis.specificextensionblocking.api.domain.ConfiguredExtension;
import hello.tis.specificextensionblocking.api.domain.ConfiguredExtensionRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeConfiguredExtensionRepository implements ConfiguredExtensionRepository {

  private final Map<Long, ConfiguredExtension> configuredExtensionMap = new HashMap<>();

  private static Long sequence = 0L;

  @Override
  @SuppressWarnings("unchecked")
  public <S extends ConfiguredExtension> S save(S entity) {
    Long id = ++sequence;
    ConfiguredExtension extension = new ConfiguredExtension(id, entity.getExtension());
    configuredExtensionMap.put(id, extension);
    return (S) extension;
  }

  @Override
  public void delete(ConfiguredExtension entity) {
    configuredExtensionMap.remove(entity.getId());
  }

  @Override
  public List<ConfiguredExtension> findAll() {
    return new ArrayList<>(configuredExtensionMap.values());
  }
}
