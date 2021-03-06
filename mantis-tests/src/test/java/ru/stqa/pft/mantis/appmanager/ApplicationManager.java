package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
    private final Properties properties;
    private WebDriver wd;
    public WebDriverWait wait;
    private String browser;
    private RegistrationHelper registrationHelper;
    private FtpHelper ftp = null;
    private MailHelper mailHelper = null;
    private SoapHelper soapHelper;

    public ApplicationManager(String browser) {
        properties = new Properties();
        this.browser = browser;
    }

    public FtpHelper ftp() {
        if(ftp == null) {
            ftp = new FtpHelper(this);
        }
        return  ftp;
    }

    public MailHelper mail() {
        if(mailHelper == null) {
            mailHelper = new MailHelper(this);
        }
        return  mailHelper;
    }

    public SoapHelper soap() {
        if(soapHelper == null) {
            soapHelper = new SoapHelper(this);
        }
        return  soapHelper;
    }


    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    }

    public void stop() {
        if(wd != null) {
            wd.quit();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public HttpSession newSession(){
        return new HttpSession(this);
    }

    public RegistrationHelper registration () {
        if (registrationHelper == null) {
            registrationHelper = new RegistrationHelper(this);
        }
        return registrationHelper;
    }

    public WebDriver getDriver() {
        if (wd == null) {
            if(browser.equals(BrowserType.FIREFOX)){
                wd = new FirefoxDriver();
            } else if(browser.equals(BrowserType.CHROME)){
                wd = new ChromeDriver();
            }
            wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            wait = new WebDriverWait(wd, 10);
            wd.get(properties.getProperty("web.baseURL"));
        }
        return wd;
    }

}
