package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ModifyContactTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        if(app.db().contacts().size() == 0){
            app.goTo().contactsPage();
            app.contact().create(new ContactData().withLastName("test1"));
        }
    }

    @Test(enabled = true)
    public void testModifyContact (){
        Contacts before = app.db().contacts();
        ContactData modifiedContact =  before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withLastName("Test1m")
            .withFirstName("Test2m").withAddress("Test3m").withHomePhone("8(777)7654321")
            .withEmail("emailM@mail.zz");
        app.goTo().contactsPage();
        app.contact().modify(contact);
        Contacts after = app.db().contacts();
        assertThat(after.size(), equalTo(before.size()));
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}
