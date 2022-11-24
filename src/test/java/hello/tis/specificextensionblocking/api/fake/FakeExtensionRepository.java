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

  public void saveFixed(FixedExtension extension) {
    Long id = ++sequence;
    extensionMap.put(id, new FixedExtension(id, extension.getName()));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <S extends Extension> S save(S entity) {
    Long id = ++sequence;
    Extension extension = new CustomExtension(id, entity.getName(),
        LocalDateTime.now());
    extensionMap.put(id, extension);
    return (S) extension;
  }

  @Override
  public List<Extension> findAll() {
    return new ArrayList<>(extensionMap.values());
  }

  @Override
  public void delete(Extension entity) {
    extensionMap.remove(entity.getId());
  }
}
