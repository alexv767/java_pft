package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase {
    long now = System.currentTimeMillis();
    String user =  String.format("user%s", now);
    String email = String.format("user%s@localhost.localdomain", now);
    String password1 = "password1";
    String password2 = "password2";

    // preconditions - create a user to be used for test
    @BeforeTest
    public void createUserForTest() throws IOException, MessagingException{
        app.mail().start();

        app.registration().start(user, email);
        List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
        String confirmationLink = findConfirmationMail(mailMessages, email);
        app.registration().finish(confirmationLink, password1);
        assertTrue(app.newSession().login(user, password1));
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
