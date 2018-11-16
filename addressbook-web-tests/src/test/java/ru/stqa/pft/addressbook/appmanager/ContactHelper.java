package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

    private By locator;

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void fillContactForm(ContactData contactData, boolean newGroup) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());

        if (newGroup){
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
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

    public void goToContactsPage() {
        wd.switchTo().alert().accept();
    }

}
