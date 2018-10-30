package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class CreateContactTests extends TestBase{

  @Test
  public void testNewContact() throws Exception {
    app.getNavigationHelper().goToContactsPage();
    app.getContactHelper().initCreateContact();
    app.getContactHelper().fillContactForm(new ContactData("FName1", "LName1", "Address1","8(999)1234567","email1@mail.q"));
    app.getContactHelper().submitCreateContact();
  }
}
