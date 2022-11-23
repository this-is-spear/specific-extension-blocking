package hello.tis.specificextensionblocking.api.fake;

import hello.tis.specificextensionblocking.api.domain.CustomExtension;
import hello.tis.specificextensionblocking.api.domain.Extension;
import hello.tis.specificextensionblocking.api.domain.ExtensionRepository;
import hello.tis.specificextensionblocking.api.domain.FixedExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeExtensionRepository implements ExtensionRepository {

  private final Map<Long, Extension> extensionMap = new HashMap<>();

  private static Long sequence = 0L;

  public void save(CustomExtension extension) {
    Long id = ++sequence;
    extensionMap.put(id, new CustomExtension(id, extension.getName(), LocalDateTime.now()));
  }

  public void saveFixed(FixedExtension extension) {
    Long id = ++sequence;
    extensionMap.put(id, new FixedExtension(id, extension.getName()));
  }

  @Override
  public List<Extension> findAll() {
    return new ArrayList<>(extensionMap.values());
  }
}
