package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class DeleteGroupTests extends TestBase{

  @Test
  public void testDeleteGroup() throws Exception {
    app.getNavigationHelper().goToGroupPage();
    if(! app.getGroupHelper().isThereAnyGroup()){
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
    app.getGroupHelper().selectGroup();
    app.getGroupHelper().deleteSelectedGroups();
    app.getGroupHelper().returnToGroupPage();
  }
}
