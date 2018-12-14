package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeleteGroupTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
      if(app.db().groups().size() == 0){
        app.goTo().groupPage();
        app.group().create(new GroupData().withName("test1"));
      }
  }

  @Test(enabled = false)
  public void testDeleteGroup() throws Exception {
      Groups before = app.db().groups();
      app.goTo().groupPage();
      GroupData deletedGroup = before.iterator().next();
      app.group().delete(deletedGroup);
      Groups after = app.db().groups();
      assertThat(after.size(), equalTo(before.size() - 1));
      assertThat(after, equalTo(before.without(deletedGroup)));
  }

}
