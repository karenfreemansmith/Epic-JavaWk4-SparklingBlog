import java.sql.Timestamp;

public abstract class Comment {
  public int id;
  public int postId;
  public String text;
  public Timestamp time;
  public int parentId;
}
