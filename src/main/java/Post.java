import java.util.List;
import org.sql2o.*;

public class Post {
  private int id;
  private String title;
  private String text;

  public Post(String title, String text) {
    this.title = title;
    this.text = text;
    this.save();
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String str) {
    this.title = str;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE posts SET title = :title WHERE id=:id";
      con.createQuery(sql, true)
          .addParameter("title", this.title)
          .addParameter("id", this.id)
          .executeUpdate();
    }
  }

  public String getText() {
    return this.text;
  }

  public void setText(String str) {
    this.text = str;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE posts SET text = :text WHERE id=:id";
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
      String sql = "INSERT INTO posts (title, text) VALUES (:title, :text)";
      this.id = (int) con.createQuery(sql, true)
          .addParameter("title", this.title)
          .addParameter("text", this.text)
          .executeUpdate()
          .getKey();
    }
  }

  public static List<Post> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM posts";
      return con.createQuery(sql)
                .executeAndFetch(Post.class);
    }
  }

  public static Post find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM posts where id = :id";
      Post post = con.createQuery(sql)
                   .addParameter("id", id)
                   .executeAndFetchFirst(Post.class);
      return post;
    }
  }

  @Override
  public boolean equals(Object otherPost) {
    if(!(otherPost instanceof Post)) {
      return false;
    } else {
      Post newPost = (Post) otherPost;
      return this.getTitle().equals(newPost.getTitle()) &&
             this.getText().equals(newPost.getText()) &&
             this.getId() == newPost.getId();
           }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM posts WHERE id = :id";
      con.createQuery(sql)
         .addParameter("id", this.id)
         .executeUpdate();
    }
  }

  public void addTag(int tagid) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO posts_tags (postid, tagid) VALUES (:postid, :tagid)";
      con.createQuery(sql, true)
          .addParameter("postid", this.id)
          .addParameter("tagid", tagid)
          .executeUpdate()
          .getKey();
    }
  }

  public List<Tag> getAllTags() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT tags.* FROM posts JOIN posts_tags ON (posts.id=posts_tags.postid) JOIN tags ON (posts_tags.tagid=tags.id) WHERE posts.id=:id";
      return con.createQuery(sql)
                .addParameter("id", this.id)
                .executeAndFetch(Tag.class);
    }
  }

}
