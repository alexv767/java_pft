package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class CreateContactTests extends TestBase{

  @Test
  public void testNewContact() throws Exception {
    app.getNavigationHelper().goToContactsPage();

    List<ContactData> before = app.getContactHelper().getContactList();

//    System.out.println("*******###########************************************************");

    ContactData contact = new ContactData("LName1", "FName1", "Address1","8(999)1234567","email1@mail.q", "test1");
    app.getContactHelper().createContact(contact);

    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(),before.size() + 1);

    before.add(contact);

    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(),c2.getId());
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after);

  }
}
