import java.util.List;
import org.sql2o.*;

public class Tag {
  private int id;
  private String text;

  public Tag(String text) {
    this.text = text;
    this.save();
  }

  public String getText() {
    return this.text;
  }

  public void setText(String str) {
    this.text = str;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tags SET text = :text WHERE id=:id";
      con.createQuery(sql, true)
          .addParameter("text", this.text)
          .addParameter("id", this.id)
          .executeUpdate();
    }
  }

  public int getId() {
    return this.id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tags (text) VALUES (:text)";
      this.id = (int) con.createQuery(sql, true)
                            .addParameter("text", this.text)
                            .executeUpdate()
                            .getKey();
    }
  }

  public static List<Tag> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tags";
      return con.createQuery(sql)
                .executeAndFetch(Tag.class);
    }
  }

  public static Tag find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tags where id = :id";
      Tag tag = con.createQuery(sql)
                   .addParameter("id", id)
                   .executeAndFetchFirst(Tag.class);
      return tag;
    }
  }

  @Override
  public boolean equals(Object otherTag) {
    if(!(otherTag instanceof Tag)) {
      return false;
    } else {
      Tag newTag = (Tag) otherTag;
      return this.getText().equals(newTag.getText()) &&
             this.getId() == newTag.getId();
           }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM tags WHERE id = :id";
      con.createQuery(sql)
         .addParameter("id", this.id)
         .executeUpdate();
    }
  }



}
