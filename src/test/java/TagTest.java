import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class TagTest {
  private Tag testTag;
  private Tag nextTag;

  @Rule
   public DBRule database = new DBRule();

 @Before
 public void initialize() {
   testTag = new Tag ("tag text");
   nextTag = new Tag ("another tag text");
 }

 @Test
 public void tag_instantiatesCorrectly_true() {
   assertTrue(testTag instanceof Tag);
 }

 @Test
 public void getText_instantiatesWithText_String() {
   assertEquals("tag text", testTag.getText());
 }

 @Test
 public void setText_updatesText_String() {
   testTag.setText("hello world");
   assertEquals("hello world", testTag.getText());
 }

 @Test
 public void save_assignsIdToObject() {
   Tag savedTag = Tag.all().get(0);
   assertEquals(testTag.getId(), savedTag.getId());
 }

 @Test
 public void all_checksIfTagsAreContainedInInstance_true() {
   assertTrue(Tag.all().size()>1);
 }

 @Test
 public void find_returnTagWithSameId_nextTag() {
   assertEquals(Tag.find(nextTag.getId()), nextTag);
 }

 @Test
 public void equals_returnsTrueIfTagsAreTheSame() {
   Tag newTag = testTag;
   assertEquals(newTag, testTag);
 }

 @Test
 public void delete_deletesCorrectTag_true() {
   int tempId = nextTag.getId();
   nextTag.delete();
   assertEquals(Tag.find(tempId), null);
 }

}
