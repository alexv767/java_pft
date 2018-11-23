package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

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

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
//        wd.findElement(By.name("selected[]")).click();
    }

    public void initModifyContact(int index) {
//        wd.findElement(By.xpath("//img[@alt='Edit']")).click();
        wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
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
        return isElementPresent(By.name("selected[]"));
    }

    public void createContact(ContactData contact) {
        initCreateContact();
        fillContactForm(contact, true);
        submitCreateContact();
    }

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<ContactData>();
//        System.out.println("*******************************************************");

        WebElement tabl = wd.findElement(By.id("maintable"));
        if (isElementPresentE(tabl, By.name("entry"))) {
            List<WebElement> elements = tabl.findElements(By.name("entry"));
            if(elements.size() > 0){
                for (WebElement element : elements) {
                    if (isElementPresentE(element, By.cssSelector("td"))) {
                        List<WebElement> cont = element.findElements(By.cssSelector("td"));
//            if (cont.size() > 0) {
                        String lastName = cont.get(1).getText();
                        String firstName = cont.get(2).getText();
                        int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
                        // compare - first and last names only:
                        ContactData contact = new ContactData(id, lastName, firstName, null, null, null, null);
                        contacts.add(contact);
//            }

                    }
                }

            }
        }
//        System.out.println("************0000000000000000000000********************");
        return contacts;
    }


    public int getModifiedContactIndex(int id, List<ContactData> before) {

        for (int i=0; i < before.size(); i++) {
            if (before.get(i).getId() == id)
                return i;
        }
        return -1;
    }
}
