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

    public void fillContactForm(ContactData contact, boolean newGroup) {

        if (contact.getGroup() != null) {
            if (newGroup){
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
    }

    public void initCreateContact() {
        click(By.linkText("add new"));
    }

    public void submitCreateContact() {
        click(By.xpath("(//input[@name='submit'])[2]"));
        wd.findElements(By.cssSelector("div.msgbox")); // wait for message
    }

    public void initModifyContactById(int id) {
//        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
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

//    public boolean isThereAnyContact() {
//        return isElementPresent(By.name("selected[]"));
//    }

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
        //    int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
    }

//    public List<ContactData> list() {
//        List<ContactData> contacts = new ArrayList<ContactData>();
////        System.out.println("*******************************************************");
//
//        WebElement tabl = wd.findElement(By.id("maintable"));
//        if (isElementPresentE(tabl, By.name("entry"))) {
//            List<WebElement> elements = tabl.findElements(By.name("entry"));
//            if(elements.size() > 0){
//                for (WebElement element : elements) {
//                    if (isElementPresentE(element, By.cssSelector("td"))) {
//                        List<WebElement> cont = element.findElements(By.cssSelector("td"));
////            if (cont.size() > 0) {
//                        String lastName = cont.get(1).getText();
//                        String firstName = cont.get(2).getText();
//                        int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
//                        // compare - first and last names only:
//                        ContactData contact = new ContactData(id, lastName, firstName, null, null, null, null);
//                        contacts.add(contact);
////            }
//
//                    }
//                }
//
//            }
//        }
////        System.out.println("************0000000000000000000000********************");
//        return contacts;
//    }

//    public Groups all() {
//        Groups groups = new Groups();
//        List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
//        for(WebElement element : elements){
//            String name = element.getText();
//            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
//            groups.add(new GroupData().withId(id).withName(name));
//        }
//        return groups;
//    }

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
                        int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
                        // compare - first and last names only:
                        ContactData contact = new ContactData().withId(id).withLastName(lastName)
                                .withFirstName(firstName);
//                        ContactData contact = new ContactData().withId(id, lastName, firstName, null, null, null, null);
                        contacts.add(contact);
                    }
                }
            }
        }
        return contacts;
    }

}
