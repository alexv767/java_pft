package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ModifyContactTests extends TestBase{

    @Test
    public void testModifyContact (){
        app.getNavigationHelper().goToContactsPage();
        app.getContactHelper().selectContact();
        app.getContactHelper().initModifyContact();
        app.getContactHelper().fillContactForm(new ContactData("Test1m", "Test2m", "Test3m", "8(777)7654321", "emailM@mail.zz"));
        app.getContactHelper().submitModifyContact();
//        app.getContactHelper().returnToContactPage();

    }
}
