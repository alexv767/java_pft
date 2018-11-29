package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeleteContactTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if(app.db().contacts().size() == 0){
            app.goTo().contactsPage();
            app.contact().create(new ContactData().withLastName("test1"));
        }
    }

    @Test(enabled = true)
    public void testDeleteContact() throws Exception {
        Contacts before = app.db().contacts();
        app.goTo().contactsPage();
        ContactData deletedContact =  before.iterator().next();
        app.contact().delete(deletedContact);
        Contacts after = app.db().contacts();
        assertThat(after.size(), equalTo(before.size() - 1));
        assertThat(after, equalTo(before.without(deletedContact)));
    }

}
