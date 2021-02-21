package cucumber.steps;

import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.И;
import org.openqa.selenium.WebElement;
import redmine.managers.Context;
import redmine.managers.Manager;
import redmine.model.user.User;
import redmine.ui.pages.LoginPage;
import redmine.ui.pages.helpers.CucumberPageObjectHelper;
import redmine.utils.Asserts;

import static redmine.ui.pages.helpers.Pages.getPage;

public class LoginSteps {
    @Если("Авторизоваться пользователем {string}")
    public void authorizeBy(String userStashId) {
        User user = Context.get(userStashId, User.class);
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
    }

    @И("Открыт браузер на главной странице")
    public void openBrowserOnMainPage() {
        Manager.openPage("login");
    }

    @И("Для пользователя отображается {string} с текстом {string}")
    public void flashNoticeAboutAccountConfirmationByAdmin(String fieldName, String text) {
        WebElement element = CucumberPageObjectHelper.getElementBy("Вход в систему", fieldName);
        String actualElementName = element.getText();
        Asserts.assertEquals(actualElementName, text);
    }

    @И("На странице {string} присутствует элемент {string}{string}")
    public void assertLoggedAsElement(String pageName, String fieldName, String stashId) {
        User user = Context.get(stashId, User.class);
        WebElement element = CucumberPageObjectHelper.getElementBy(pageName, fieldName);
        String actualElementName = element.getText();
        Asserts.assertEquals(actualElementName, "Вошли как " + user.getLogin());
    }
}
