package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class DeleteContactTests extends TestBase {

    @Test
    public void testDeleteContact() throws Exception {
        app.getNavigationHelper().goToContactsPage();
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContacts();
    }
}
