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
//        type(By.name("realname"), password);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
//        click(By.cssSelector("input[value='UpdateUser']"));
       click(By.ByXPath.xpath("//*[.='Изменить пользователя']"));
    }

    public void initChangePassword (String username, String email) {
        wd.get(app.getProperty("web.baseURL") + "/login_page.php");  // open login page
        type(By.name("username"), "administrator");
//        click(By.cssSelector("input[value='Login']"));
//        click(By.ByXPath.xpath("//input[@value='Login']"));
        click(By.ByXPath.xpath("//input[@value='Войти']"));

        wd.findElement(By.name("password")).sendKeys("root");
        click(By.ByXPath.xpath("//input[@value='Войти']"));

        click(By.ByXPath.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Управление'])[1]/preceding::i[1]"));
        wd.findElement(By.linkText("Управление пользователями")).click();
        wd.findElement(By.linkText(username)).click();

        click(By.cssSelector("input[value='Сбросить пароль']")); // //input[@value='Сбросить пароль']
        // here is possible to wait for a MsgBox ...
        // and now - wait for email ...
    }

    public void submitChangePassword(String confirmationLink, String password2) {
        wd.get(confirmationLink);
//        type(By.name("realname"), user);
        type(By.name("password"), password2);
        type(By.name("password_confirm"), password2);
//        click(By.cssSelector("input[value='UpdateUser']"));
        click(By.ByXPath.xpath("//*[.='Изменить пользователя']"));  //*[.='Нужный мне текст']
    }

}
