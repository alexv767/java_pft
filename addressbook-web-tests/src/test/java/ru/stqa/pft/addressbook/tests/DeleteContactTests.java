package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class DeleteContactTests extends TestBase {

    @Test
    public void testDeleteContact() throws Exception {
        app.getNavigationHelper().goToContactsPage();
        if(! app.getContactHelper().isThereAnyContact()){
            app.getContactHelper().createContact(new ContactData("test1", null, null, null, null, null));
            app.getNavigationHelper().goToContactsPage();
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContacts();
        app.getContactHelper().submitDeleteContact();
        app.getNavigationHelper().goToContactsPage();
    }
}
