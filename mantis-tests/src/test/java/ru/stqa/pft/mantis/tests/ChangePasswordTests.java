package ru.stqa.pft.mantis.tests;

import biz.futureware.mantis.rpc.soap.client.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.User;

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
    long now = System.currentTimeMillis();
    String user =  String.format("user%s", now);
    String email = String.format("user%s@localhost.localdomain", now);
    String password1 = "password1";
    String password2 = "password2";
    User userForTest;

    // preconditions - create a user to be used for test
    @BeforeTest
    public void createUserForTest() throws IOException, MessagingException, ServiceException {
        app.mail().start();

        app.registration().start(user, email);
        List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
        String confirmationLink = findConfirmationMail(mailMessages, email);
        app.registration().finish(confirmationLink, password1);
        assertTrue(app.newSession().login(user, password1));

        userForTest = getUserForTest();
    }

    private User getUserForTest() throws MalformedURLException, ServiceException, RemoteException {
        User userSelected = null;
        MantisConnectPortType mc = new MantisConnectLocator()
                .getMantisConnectPort(new URL("http://localhost/mantisbt-2.18.0/api/soap/mantisconnect.php"));
        ProjectData[] projects = mc.mc_projects_get_user_accessible("administrator", "root");
        System.out.println("status= ");
//        UserData userData =
//        mc.
//          .mc_project_get_users("administrator", "root", projects[0].getId(), BigInteger.valueOf(1));
//        IssueData issueData = mc.mc_issue_get("administrator", "root", BigInteger.valueOf(issueId));
//        ObjectRef status = issueData.getStatus(); // should not be 'resolved' or 'closed' to run test
//        String name = status.getName();
//
//        System.out.println("status= " + name);
        return userSelected;
    }

    @Test
    public void testChangePassword () throws IOException, MessagingException {
        app.registration().initChangePassword(user, email);     //   UI - "Reset password"

        MailMessage mailForPsw = app.mail().waitForMailForPassword(1, 10000, user); // get mail for changing psw
        String confirmationLink = getConfirmationLinkForPassword(mailForPsw);  // get link
        app.registration().submitChangePassword(confirmationLink, user, password1, password2);    //   UI
        assertTrue(app.newSession().login(user, password2));

    }

    private String findConfirmationMail(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    private String getConfirmationLinkForPassword(MailMessage mailMessage) {
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer(){
        app.mail().stop();
    }


}
