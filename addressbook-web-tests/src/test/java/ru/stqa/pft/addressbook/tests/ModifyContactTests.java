package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ModifyContactTests extends TestBase{

    @Test
    public void testModifyContact (){
        app.getNavigationHelper().goToContactsPage();
        if(! app.getContactHelper().isThereAnyContact()){
            app.getContactHelper().createContact(new ContactData("test1", null, null, null, null, null));
        }

        List<ContactData> before = app.getContactHelper().getContactList();

        app.getContactHelper().initModifyContact();
        ContactData contact = new ContactData("Test1m", "Test2m", "Test3m", "8(777)7654321", "emailM@mail.zz", null);
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().submitModifyContact();
        app.getNavigationHelper().goToContactsPage();

        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(),before.size());
        before.remove(before.size() - 1);
        before.add(contact);

        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(before, after);

    }
}
