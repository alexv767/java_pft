package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressNEmailTests extends TestBase{
    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().contactsPage();
        if(app.contact().all().size() == 0){
            app.contact().create(new ContactData().withLastName("test1")
                    .withHomePhone("123").withMobilePhone("456").withWorkPhone("789")
                    .withAddress("Fleet Street")
                    .withEmail("email1@ww.ru").withEmail2("email2@ww.ru").withEmail3("email3@ww.ru"));
        }
    }

    @Test(enabled = false)
    public void testContactAddressNEmails (){
        app.goTo().contactsPage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllEmails(), CoreMatchers.equalTo(mergeEmails(contactInfoFromEditForm)));
        assertThat(contact.getAddress(), CoreMatchers.equalTo(contactInfoFromEditForm.getAddress()));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s) -> ! s.equals(""))
                .collect(Collectors.joining("\n"));
    }

}
