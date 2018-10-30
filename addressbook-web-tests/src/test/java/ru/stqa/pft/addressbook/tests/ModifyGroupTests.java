package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class ModifyGroupTests extends TestBase {

    @Test
    public void testModifyGroup(){
        app.getNavigationHelper().goToGroupPage();
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().initModifyGroup();
        app.getGroupHelper().fillGroupForm(new GroupData("Test1e", "Test2e", "Test3e"));
        app.getGroupHelper().submitModifyGroup();
        app.getGroupHelper().returnToGroupPage();
    }
}
