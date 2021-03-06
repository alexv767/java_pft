package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ModifyGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if(app.db().groups().size() == 0){
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test(enabled = true)
    public void testModifyGroup(){
        Groups before = app.db().groups();
        GroupData modifiedGroup = before.iterator().next();
        GroupData group = new GroupData()
            .withId(modifiedGroup.getId()).withName("Test1e").withHeader("Test2e").withFooter("Teste");
        app.goTo().groupPage();
        app.group().modify(group);
        Groups after = app.db().groups();
        assertEquals(after.size(),before.size());
        assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));

        verifyGroupListInUI();
    }

}
