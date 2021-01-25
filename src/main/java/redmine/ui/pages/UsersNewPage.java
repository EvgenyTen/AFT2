package redmine.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import redmine.managers.Manager;

import static redmine.ui.pages.Pages.getPage;

public class UsersNewPage extends AbstractPage{
    private WebDriver driver;

    @FindBy(xpath = "//h2[text()=' » Новый пользователь']")
    private WebElement newUserPage;
    @FindBy(xpath = "//input[@id='user_login']")
    public WebElement usernameField;
    @FindBy(xpath = "//input[@id='user_firstname']")
    public WebElement userFirstNameField;
    @FindBy(xpath = "//input[@id='user_lastname']")
    public WebElement userLastNameField;
    @FindBy(xpath = "//input[@id='user_mail']")
    public WebElement userMailField;
    @FindBy(xpath = "//input[@id='user_generate_password']")
    public WebElement passwordCreationCheckBox;
    @FindBy(xpath = "//input[@name='commit']")
    public WebElement commit;
    @FindBy(xpath = "//div[@id='flash_notice']")
    private WebElement flashNotice;

    @Step("Создание нового пользователя")
    public void userCreation(String login, String firstName,String lastName,String mail) {
        new Actions(Manager.driver())
                .moveToElement(getPage(UsersNewPage.class).usernameField)
                .click()
                .sendKeys(login)
                .moveToElement(getPage(UsersNewPage.class).userFirstNameField)
                .click()
                .sendKeys(firstName)
                .moveToElement(getPage(UsersNewPage.class).userLastNameField)
                .click()
                .sendKeys(lastName)
                .moveToElement(getPage(UsersNewPage.class).userMailField)
                .click()
                .sendKeys(mail)
                .moveToElement(getPage(UsersNewPage.class).passwordCreationCheckBox)
                .click()
                .moveToElement(getPage(UsersNewPage.class).commit)
                .click()
                .build()
                .perform();
    }
    @Step("Уведомление о добавлении нового пользователя")
    public String flashNotice() {
        return flashNotice.getText();
    }
    @Step("Страница 'Новый пользователь' отображается")
    public String newUserPage() {
        return newUserPage.getText();
    }
}