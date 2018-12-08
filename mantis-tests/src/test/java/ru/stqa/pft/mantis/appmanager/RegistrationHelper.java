package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class RegistrationHelper extends HelperBase{
//    private WebDriver wd;

    public RegistrationHelper(ApplicationManager app) {
        super(app);
//        wd = app.getDriver();
    }

    public void start (String username, String email) {
        wd.get(app.getProperty("web.baseURL") + "signup_page.php");  // open page
//        wd.findElement(By.name("username")).sendKeys(username);
        type(By.name("username"), username);
        type(By.name("email"), email);
//        click(By.cssSelector("input[value='Signup']"));
        click(By.cssSelector("input[value='Зарегистрироваться']"));
    }


    public void finish(String confirmationLink, String password) {
        wd.get(confirmationLink);
        type(By.name("realname"), password);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
//        click(By.cssSelector("input[value='UpdateUser']"));
       click(By.ByXPath.xpath("//*[.='Изменить пользователя']"));
    }
}
