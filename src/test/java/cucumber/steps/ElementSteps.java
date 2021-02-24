package cucumber.steps;

import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.То;
import org.openqa.selenium.WebElement;
import redmine.managers.Context;
import redmine.model.user.User;
import redmine.ui.pages.helpers.CucumberPageObjectHelper;

import java.util.Objects;

import static redmine.utils.StringGenerators.randomEmail;

public class ElementSteps {
    @И("На странице {string} нажать на элемент {string}")
    public void clickOnElement(String pageName, String fieldName) {
        WebElement element = CucumberPageObjectHelper.getElementBy(pageName, fieldName);
        element.click();
    }

    @И("На странице {string} в поле {string} ввести текст {string}")
    public void textInputToField(String pageName, String fieldName, String text) {
        WebElement element = CucumberPageObjectHelper.getElementBy(pageName, fieldName);
        element.sendKeys(text);
    }

    @Если("Заполнить данные пользователя корректными случайносгенерированными значениями пользователя {string}")
    public void generateAndFillNewUserForm(String userDataStashId) {
        User createdUser = new User();
        String mail = randomEmail();
        WebElement elementLogin = CucumberPageObjectHelper.getElementBy("Страница создания нового пользователя", "Пользователь");
        elementLogin.click();
        elementLogin.sendKeys(createdUser.getLogin());
        WebElement elementName = CucumberPageObjectHelper.getElementBy("Страница создания нового пользователя", "Имя");
        elementName.click();
        elementName.sendKeys(createdUser.getFirstName());
        WebElement elementLastName = CucumberPageObjectHelper.getElementBy("Страница создания нового пользователя", "Фамилия");
        elementLastName.click();
        elementLastName.sendKeys(createdUser.getLastName());
        WebElement elementMail = CucumberPageObjectHelper.getElementBy("Страница создания нового пользователя", "Электронная почта");
        elementMail.click();
        elementMail.sendKeys(mail);
        Context.put(userDataStashId, createdUser);
    }

    @И("Установить чекбокс {string}")
    public void setCheckBoxCreatePasswordOn(String fieldName) {
        WebElement elementCreatePasswordCheckBox = CucumberPageObjectHelper.getElementBy("Страница создания нового пользователя", fieldName);
        if (!elementCreatePasswordCheckBox.isSelected()) {
            elementCreatePasswordCheckBox.click();
        }
    }

    @Если("В шапке таблицы пользователей нажать на {string}")
    public void pushTableHeader(String fieldElement) {
        if (Objects.equals(fieldElement, "Фамилия")) {
            WebElement pushFamily = CucumberPageObjectHelper.getElementBy("Страница Пользователи", "Фамилия");
            pushFamily.click();
        } else if (Objects.equals(fieldElement, "Имя")) {
            WebElement pushName = CucumberPageObjectHelper.getElementBy("Страница Пользователи", "Имя");
            pushName.click();
        } else if (Objects.equals(fieldElement, "Пользователь")) {
            WebElement pushUser = CucumberPageObjectHelper.getElementBy("Страница Пользователи", "Пользователь");
            pushUser.click();
        } else {
            throw new IllegalArgumentException("Не корректный параметр " + fieldElement);
        }
    }

    @То("На странице {string} элемент {String} имеет текст {string}")
    public void checkLoggedAstext(String pageName, String fieldName,String rawString) {


    }


}
