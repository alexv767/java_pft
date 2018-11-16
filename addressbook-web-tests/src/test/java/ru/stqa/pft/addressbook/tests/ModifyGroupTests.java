package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class ModifyGroupTests extends TestBase {

    @Test
    public void testModifyGroup(){
        app.getNavigationHelper().goToGroupPage();
        if(! app.getGroupHelper().isThereAnyGroup()){
            app.getGroupHelper().createGroup(new GroupData("test1", null, null));
        }
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().initModifyGroup();
        app.getGroupHelper().fillGroupForm(new GroupData("Test1e", null, null));
        app.getGroupHelper().submitModifyGroup();
        app.getGroupHelper().returnToGroupPage();
    }
}
