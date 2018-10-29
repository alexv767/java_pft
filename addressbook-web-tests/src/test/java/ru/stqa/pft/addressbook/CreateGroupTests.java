package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class CreateGroupTests extends TestBase {

    @Test
    public void testCreateGroup() throws Exception {
        goToGroupPage();
        initCreateGroup();
        fillGroupForm(new GroupData("Test1", "Test2", "Test3"));
        submitCreateGroup();
        returnToGroupPage();
    }

}
