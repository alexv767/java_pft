package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

public class ContactHelper extends HelperBase {
    ApplicationManager app;
    private By locator;

    public ContactHelper(WebDriver wd, ApplicationManager app) {
        super(wd);
        this.app = app;
    }

    public void fillContactForm(ContactData contact, boolean creation) {

        if (contact.getGroup() != null) {
            if (creation){
                try {
                    new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contact.getGroup());
                } catch (NoSuchElementException ex) {
                    ;
//                    app.goTo().groupPage();
//                    app.group().create(new GroupData(contactData.getGroup(), null, null));
//                    app.goTo().contactsPage();
//                    initModifyContact();
//                    new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
                }
            } else {
                Assert.assertFalse(isElementPresent(By.name("new_group")));
            }
        }
        type(By.name("firstname"), contact.getFirstName());
        type(By.name("lastname"), contact.getLastName());
        type(By.name("address"), contact.getAddress());
        type(By.name("home"), contact.getHomePhone());
        type(By.name("email"), contact.getEmail());
        attach(By.name("photo"), contact.getPhoto());
    }

    public void initCreateContact() {
        click(By.linkText("add new"));
    }

    public void submitCreateContact() {
        click(By.xpath("(//input[@name='submit'])[2]"));
        wd.findElements(By.cssSelector("div.msgbox")); // wait for message
    }

    public void initModifyContactById(int id) {
        wd.findElement(By.cssSelector("a[href='edit.php?id=" + id + "']")).click();
    }

    public void submitModifyContact() {
        wd.findElement(By.name("update")).click();
    }

    public void submitDeleteContact() {
        wd.switchTo().alert().accept();
        wd.findElements(By.cssSelector("div.msgbox")); // wait for message
    }

    public void deleteSelectedContacts() {
        wd.findElement(By.xpath("//input[@value='Delete']")).click();
    }

    public void create(ContactData contact) {
        initCreateContact();
        fillContactForm(contact, true);
        submitCreateContact();
    }

    public void modify(ContactData contact) {
        initModifyContactById(contact.getId());
        fillContactForm(contact, false);
        submitModifyContact();
        app.goTo().contactsPage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContacts();
        submitDeleteContact();
        app.goTo().contactsPage();
    }

    public void selectContactById(int id) {
            wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public Contacts all() {
        Contacts contacts = new Contacts();
        WebElement tabl = wd.findElement(By.id("maintable"));
        if (isElementPresentE(tabl, By.name("entry"))) {
            List<WebElement> elements = tabl.findElements(By.name("entry"));
            if(elements.size() > 0){
                for (WebElement element : elements) {
                    if (isElementPresentE(element, By.cssSelector("td"))) {
                        List<WebElement> cont = element.findElements(By.cssSelector("td"));
                        String lastName = cont.get(1).getText();
                        String firstName = cont.get(2).getText();
                        String allPhones = cont.get(5).getText();
                        String address = cont.get(3).getText();
                        String allEmails = cont.get(4).getText();
                        int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
                        // compare - first and last names only:
                        ContactData contact = new ContactData().withId(id)
                                .withLastName(lastName).withFirstName(firstName)
                                .withAllPhones(allPhones)
                                .withAddress(address)
                                .withAllEmails(allEmails);
                        contacts.add(contact);
                    }
                }
            }
        }
        return contacts;
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initModifyContactById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withLastName(lastname).withFirstName(firstname)
                .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work)
                .withAddress(address).withEmail(email).withEmail2(email2).withEmail3(email3);
    }
}
