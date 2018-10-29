package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class CreateGroupTests extends TestBase {

    @Test
    public void testCreateGroup() throws Exception {
        app.goToGroupPage();
        app.initCreateGroup();
        app.fillGroupForm(new GroupData("Test1", "Test2", "Test3"));
        app.submitCreateGroup();
        app.returnToGroupPage();
    }

}
