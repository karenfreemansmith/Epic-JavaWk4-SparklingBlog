import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class PostTest {
  private Post testPost;
  private Post nextPost;

  @Rule
   public DBRule database = new DBRule();

 @Before
 public void initialize() {
   testPost = new Post ("title", "post text");
   nextPost = new Post ("another title", "another post text");
 }

 @Test
 public void post_instantiatesCorrectly_true() {
   assertTrue(testPost instanceof Post);
 }

 @Test
 public void getTitle_instantiatesWithTitle_String() {
   assertEquals("title", testPost.getTitle());
 }

 @Test
 public void setTitle_updatesTitle_String() {
   testPost.setTitle("hello world");
   assertEquals("hello world", testPost.getTitle());
 }

 @Test
 public void getText_instantiatesWithText_String() {
   assertEquals("post text", testPost.getText());
 }

 @Test
 public void setText_updatesText_String() {
   testPost.setText("hello world");
   assertEquals("hello world", testPost.getText());
 }

 @Test
 public void save_assignsIdToObject() {
   assertTrue(testPost.getId()>0);
 }

 @Test
 public void all_checksIfPostsAreContainedInInstance_true() {
   assertTrue(Post.all().size()>1);
 }

 @Test
 public void find_returnPostWithSameId_nextPost() {
   assertEquals(Post.find(nextPost.getId()), nextPost);
 }

 @Test
 public void equals_returnsTrueIfPostsAreTheSame() {
   Post newPost = testPost;
   assertEquals(newPost, testPost);
 }

 @Test
 public void delete_deletesCorrectPost_true() {
   int tempId = nextPost.getId();
   nextPost.delete();
   assertEquals(Post.find(tempId), null);
 }

 @Test
 public void addTag_addTagToPost() {
   Tag sampleTag = new Tag("sample");
   testPost.addTag(sampleTag.getId());
   assertTrue(testPost.getAllTags().contains(sampleTag));
 }

}
