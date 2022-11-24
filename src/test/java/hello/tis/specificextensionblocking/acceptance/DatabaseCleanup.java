package hello.tis.specificextensionblocking.acceptance;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile("test")
@Service
public class DatabaseCleanup implements InitializingBean {

  @Autowired
  private DataSource dataSource;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private List<String> tableNames;

  @Override
  public void afterPropertiesSet() {
    tableNames = new ArrayList<>();
    try {
      DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
      ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});
      while (tables.next()) {
        if (!tables.getString("TABLE_SCHEM").equals("INFORMATION_SCHEMA")) {
          String tableName = tables.getString("TABLE_NAME");
          tableNames.add(tableName);
        }
      }
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }

  @Transactional
  public void execute() {
    jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
    for (String tableName : tableNames) {
      jdbcTemplate.execute("TRUNCATE TABLE " + tableName);
    }
    jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
  }
}
