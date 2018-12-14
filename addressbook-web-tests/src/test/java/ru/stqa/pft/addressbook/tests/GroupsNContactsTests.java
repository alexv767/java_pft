package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupsNContactsTests extends TestBase {

//    @BeforeMethod
    @BeforeTest
    public void ensurePreconditions() {
        Contacts contacts = app.db().contacts();    // all contacts
        Groups groups = app.db().groups();      // all groups in database

        if(contacts.size() == 0){        // create contact if required
            app.goTo().contactsPage();
            app.contact().create(new ContactData().withLastName("test1").withPhoto(new File("src/test/resources/bm.png")));
            contacts = app.db().contacts();
        }

        if(groups.size() == 0){      // create group if required
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("group111"));
            groups = app.db().groups();
        }

        ContactData linkedContact =  contacts.iterator().next();   // get contact for test and call it "linkedContact"
        Groups linkedGroupsBefore = linkedContact.getGroups(); // groups already linked to "linkedContact"
        if (linkedGroupsBefore.size() == groups.size()){   // all groups in db are linked already to "linkedContact",  so ...
            app.contact().newGroupToLink();
        }

    }

    @Test(enabled = false)
    public void testContactAddToGroup (){
        Contacts contacts = app.db().contacts();        // all contacts
        ContactData linkedContact =  contacts.iterator().next();   // get contact for test and call it "linkedContact"
        Groups linkedGroupsBefore = linkedContact.getGroups(); // groups already linked to "linkedContact"
        Groups groups = app.db().groups();      // all groups in database

        app.goTo().contactsPage();
        GroupData groupToLink = app.contact().getGroupToLink(groups, linkedGroupsBefore);

        app.contact().linkToGroup(linkedContact, groupToLink);            // link by name

        contacts = app.db().contacts();        // refresh all contacts
        linkedContact =  contacts.iterator().next();   // get contact again
        Groups linkedGroupsAfter = linkedContact.getGroups();
//        System.out.println("AFTER ::::::::::");
//        System.out.println(linkedContact.getGroups());
        assertThat(linkedGroupsAfter.size(), equalTo(linkedGroupsBefore.size() + 1));
        assertThat(linkedGroupsAfter, equalTo(linkedGroupsBefore.withAdded(groupToLink)));
    }

    @Test(enabled = true)      // run after "testContactAddToGroup" as preconditions guarantee
    public void testContactRemoveFromGroup (){
        Contacts contacts = app.db().contacts();        // get all contacts from db

        ContactData unlinkedContact = app.contact().getContactToUnlink(contacts);     // get contact to unlink
        Groups linkedGroupsBefore = unlinkedContact.getGroups();
        GroupData unlinkedGroup = app.contact().unlinkContact(unlinkedContact); // unlink contact from group

        unlinkedContact = app.contact().getContactById(app.db().contacts(), unlinkedContact.getId());  // refresh data for contacts
        Groups linkedGroupsAfter = unlinkedContact.getGroups();
        assertThat(linkedGroupsAfter.size(), equalTo(linkedGroupsBefore.size() - 1));
        assertThat(linkedGroupsAfter, equalTo(linkedGroupsBefore.without(unlinkedGroup)));
    }

}
