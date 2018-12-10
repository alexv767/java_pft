package ru.stqa.pft.mantis.tests;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import biz.futureware.mantis.rpc.soap.client.ObjectRef;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

public class TestBase {

    public static final ApplicationManager app =
//            new ApplicationManager(BrowserType.FIREFOX);
            new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));
//            new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));


    private boolean isIssueOpen(int issueId) throws RemoteException, MalformedURLException, ServiceException {
        MantisConnectPortType mc = new MantisConnectLocator()
                .getMantisConnectPort(new URL("http://localhost/mantisbt-2.18.0/api/soap/mantisconnect.php"));
        IssueData issueData = mc.mc_issue_get("administrator", "root", BigInteger.valueOf(issueId));
        ObjectRef status = issueData.getStatus(); // should not be 'resolved' or 'closed' to run test
        String name = status.getName();

        System.out.println("status= " + name);
        if ( ! (name.equals("resolved") || name.equals("closed") ) )
            return true;
        else
            return false;
    }

    public void skipIfNotFixed(int issueId) throws RemoteException, ServiceException, MalformedURLException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
        app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.bak");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.ftp().restore("config_inc.php.bak", "config_inc.php");
        app.stop();
    }

}
