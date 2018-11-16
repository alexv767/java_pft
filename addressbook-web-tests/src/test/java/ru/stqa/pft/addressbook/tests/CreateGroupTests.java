package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class CreateGroupTests extends TestBase {

    @Test
    public void testCreateGroup() throws Exception {
        app.getNavigationHelper().goToGroupPage();
        app.getGroupHelper().createGroup(new GroupData("test1", "Test2", "Test3"));
    }
}
