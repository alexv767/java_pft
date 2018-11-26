package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateContactTests extends TestBase{

  @Test(enabled = true)
  public void testNewContact() throws Exception {
    app.goTo().contactsPage();
    Contacts before = app.contact().all();
    File photo = new File("src/test/resources/bm.png");

    ContactData contact = new ContactData().withLastName("LName1")
      .withFirstName("FName1").withAddress("Address1").withHomePhone("8(999)1234567")
      .withEmail("email1@mail.q").withPhoto(photo);
    app.contact().create(contact);
    Contacts after = app.contact().all();
    assertThat(after.size(), equalTo(before.size() + 1));
    assertThat(after, equalTo(
      before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  @Test(enabled = false)
  public void tesCurrentDir(){
    File currentDir = new File(".");
    System.out.println(currentDir.getAbsolutePath());
    File photo = new File("src/test/resources/bm.png");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());
  }
}
