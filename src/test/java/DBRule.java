import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DBRule extends ExternalResource {

@Override
protected void before() {
DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/blog_test", null, null);
}

@Override
protected void after() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM tags *;";
    con.createQuery(sql).executeUpdate();
    }
  }
}
