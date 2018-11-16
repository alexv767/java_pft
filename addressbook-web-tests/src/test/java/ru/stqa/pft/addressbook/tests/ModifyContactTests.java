package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ModifyContactTests extends TestBase{

    @Test
    public void testModifyContact (){
        app.getNavigationHelper().goToContactsPage();
        if(! app.getContactHelper().isThereAnyContact()){
            app.getContactHelper().createContact(new ContactData("test1", null, null, null, null, null));
        }
        app.getContactHelper().initModifyContact();
        app.getContactHelper().fillContactForm(new ContactData("Test1m", "Test2m", "Test3m", "8(777)7654321", "emailM@mail.zz", null), false);
        app.getContactHelper().submitModifyContact();
        app.getNavigationHelper().goToContactsPage();
    }
}
