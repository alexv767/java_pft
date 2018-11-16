package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;

public class ContactHelper extends HelperBase {
    ApplicationManager app;
    private By locator;

    public ContactHelper(WebDriver wd, ApplicationManager app) {
        super(wd);
        this.app = app;
    }

    public void fillContactForm(ContactData contactData, boolean newGroup) {

        if (contactData.getGroup() != null) {
            if (newGroup){
                try {
                    new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
                } catch (NoSuchElementException ex) {
                    ;
//                    app.getNavigationHelper().goToGroupPage();
//                    app.getGroupHelper().createGroup(new GroupData(contactData.getGroup(), null, null));
//                    app.getNavigationHelper().goToContactsPage();
//                    initModifyContact();
//                    new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
                }
            } else {
                Assert.assertFalse(isElementPresent(By.name("new_group")));
            }
        }
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("email"), contactData.getEmail());
    }

    public void initCreateContact() {
        click(By.linkText("add new"));
    }

    public void submitCreateContact() {
        click(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void selectContact() {
        wd.findElement(By.name("selected[]")).click();
    }

    public void initModifyContact() {
        wd.findElement(By.xpath("//img[@alt='Edit']")).click();
    }

    public void submitModifyContact() {
        wd.findElement(By.name("update")).click();
    }

    public void submitDeleteContact() {
        wd.switchTo().alert().accept();
    }

    public void deleteSelectedContacts() {
        wd.findElement(By.xpath("//input[@value='Delete']")).click();
    }

    public boolean isThereAnyContact() {
//        return isElementPresent(By.name("selected[]"));
        return isElementPresent(By.name("selected[]"));
    }

    public void createContact(ContactData contact) {
        initCreateContact();
        fillContactForm(contact, true);
        submitCreateContact();
    }
}
