package redmine.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderPage extends AbstractPage {

    @FindBy(xpath = "//a[@class='home']")
    public WebElement home;

    @FindBy(xpath = "//a[@class='projects']")
    public WebElement projects;

    @FindBy(xpath = "//div[@id='loggedas']")
    private WebElement loggedAs;

    @FindBy(xpath = "//a[@class='home']")
    public WebElement adminHomePage;

    @FindBy(xpath = "//a[@class='my-page']")
    public WebElement myPage;

    @FindBy(xpath = "//a[@class='administration']")
    public WebElement administration;

    @FindBy(xpath = "//a[@class='help']")
    public WebElement help;

    @FindBy(xpath = "//a[@class='my-account']")
    public WebElement myAccount;

    @FindBy(xpath = "//a[@class='logout']")
    public WebElement logout;

    @FindBy(xpath = "//form/label/a")
    private WebElement searchLabel;

    @FindBy(xpath = "//form/input[@id='q']")
    public WebElement searchField;

    @FindBy(xpath = "//a[@class='login']")
    public WebElement signIn;

    @FindBy(xpath = "//a[@class='register']")
    public WebElement register;

    @Step("Присутствует текст 'Вошли как <Логин>'")
    public String loggedAs() {
        return loggedAs.getText();
    }

    @Step("Присутствует элемент 'Домашняя страница'")
    public String adminHomePage() {
        return adminHomePage.getText();
    }

    @Step("Присутствует элемент 'Моя страница'")
    public String myPage() {
        return myPage.getText();
    }

    @Step("Присутствует элемент 'Проекты'")
    public String projects() {
        return projects.getText();
    }

    @Step("Присутствует элемент 'Администрирование'")
    public String administration() {
        return administration.getText();
    }

    @Step("Присутствует элемент 'Помощь'")
    public String help() {
        return help.getText();
    }

    @Step("Присутствует элемент 'Моя учётная запись'")
    public String myAccount() {
        return myAccount.getText();
    }

    @Step("Присутствует элемент 'Выйти'")
    public String logout() {
        return logout.getText();
    }

    @Step("Присутствует элемент 'Поиск'")
    public String searchLabel() {
        return searchLabel.getText();
    }

}
