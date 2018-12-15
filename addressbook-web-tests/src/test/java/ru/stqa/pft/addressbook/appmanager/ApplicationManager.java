package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
    WebDriver wd;
    public WebDriverWait wait;

    private SessionHelper sessionHelper;
    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    private ContactHelper contactHelper;
    private DbHelper dbHelper;
    private String browser;

    public ApplicationManager(String browser) {
        properties = new Properties();
        this.browser = browser;
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

        dbHelper = new DbHelper();

		if("".equals(properties.getProperty("selenium.server"))) {
			if(browser.equals(BrowserType.FIREFOX)){
				wd = new FirefoxDriver();
			} else if(browser.equals(BrowserType.CHROME)){
				wd = new ChromeDriver();
			}
		} else {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setBrowserName(browser);
			wd = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), capabilities);
		}
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(wd, 10);
        wd.get(properties.getProperty("web.baseURL"));  // "http://localhost/addressbook/"
        groupHelper = new GroupHelper(wd);
        contactHelper = new ContactHelper(wd, this);
        navigationHelper = new NavigationHelper(wd);
        sessionHelper = new SessionHelper(wd);
//        sessionHelper.login("admin", "secret");
        sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));

    }

    public void stop() {
        sessionHelper.logout();
        wd.quit();
    }

    private boolean isElementPresent(By by) {
        try {
            wd.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public GroupHelper group() {
        return groupHelper;
    }

    public ContactHelper contact() {
        return contactHelper;
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }

    public DbHelper db() {
        return dbHelper;
    }


    public void pause(Integer milliseconds){
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
