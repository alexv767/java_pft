package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class DeleteContactTests extends TestBase {

    @Test
    public void testDeleteContact() throws Exception {
        app.getNavigationHelper().goToContactsPage();
        if(! app.getContactHelper().isThereAnyContact()){
            app.getContactHelper().createContact(new ContactData("test1", null, null, null, null, null));
            app.getNavigationHelper().goToContactsPage();
        }

        List<ContactData> before = app.getContactHelper().getContactList();

        app.getContactHelper().selectContact(before.size() - 1);
        app.getContactHelper().deleteSelectedContacts();
        app.getContactHelper().submitDeleteContact();
        app.getNavigationHelper().goToContactsPage();

//        if( isElementPresent(By.id("maintable"))) ;
//        app.wait.until(presenceOfElementLocated(By.id("maintable")));
//        app.wait.until(presenceOfElementLocated(By.name("logout")));

        app.pause(5000);
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(),before.size() - 1);

        before.remove(before.size() - 1);
        Assert.assertEquals(after, before);



    }
}
