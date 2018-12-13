package ru.stqa.pft.mantis.tests;

import biz.futureware.mantis.rpc.soap.client.AccountData;
import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import biz.futureware.mantis.rpc.soap.client.ProjectData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase {
    AccountData userForTest;

    long now = System.currentTimeMillis();
    String user =  String.format("user%s", now);
    String email = String.format("user%s@localhost.localdomain", now);
    String password1 = "password1";  // original password
    String password2 = "password2";  // new password

    // preconditions - create a user to be used for test
    @BeforeTest
    public void createUserForTest() throws IOException, MessagingException, ServiceException {
        app.mail().start();

        // create a user just in case (if the only one "administrator" exists):
        app.registration().start(user, email);
        List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
        String confirmationLink = app.mail().findConfirmationMail(mailMessages, email);
        app.registration().finish(confirmationLink, password1);
        assertTrue(app.newSession().login(user, password1));

        userForTest = getUserForTest(); // get any user for test
    }

    @Test
    public void testChangePassword () throws IOException, MessagingException {
        app.registration().initChangePassword(userForTest.getName(), userForTest.getEmail());     //   UI - "Reset password"

        MailMessage mailForPsw = app.mail().waitForMailForPassword(1, 10000, userForTest.getName()); // get mail for changing psw
        String confirmationLink = app.mail().getConfirmationLinkForPassword(mailForPsw);  // get link
        app.registration().submitChangePassword(confirmationLink, password2);    //   UI
        assertTrue(app.newSession().login(userForTest.getName(), password2));
    }


    @AfterMethod(alwaysRun = true)
    public void stopMailServer(){
        app.mail().stop();
    }



    private AccountData getUserForTest() throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = new MantisConnectLocator()
                .getMantisConnectPort(new URL("http://localhost/mantisbt-2.18.0/api/soap/mantisconnect.php"));
        ProjectData[] projects = mc.mc_projects_get_user_accessible("administrator", "root"); // projects
        AccountData[] users = mc
                .mc_project_get_users("administrator", "root", projects[0].getId(), BigInteger.valueOf(0));
//        System.out.println("project = " + project);
//        System.out.println("project = " + users[1]);   // as users[0] is administrator actually !
        return users[1];
    }

}
