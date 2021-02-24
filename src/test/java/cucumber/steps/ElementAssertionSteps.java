package cucumber.steps;

import cucumber.api.java.ru.И;
import cucumber.api.java.ru.То;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import redmine.cucumber.ParametersValidator;
import redmine.managers.Context;
import redmine.model.project.Project;
import redmine.ui.pages.ProjectsPage;
import redmine.ui.pages.UsersPage;
import redmine.ui.pages.helpers.CucumberPageObjectHelper;
import redmine.utils.Asserts;
import redmine.utils.BrowserUtils;

import java.util.List;
import java.util.stream.Collectors;

import static redmine.ui.pages.helpers.Pages.getPage;

public class ElementAssertionSteps {
    @То("На главной странице отображается поле {string}")
    public void assertProjectElementIsDisplayed(String fieldName) {
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(CucumberPageObjectHelper.getElementBy("Заголовок", fieldName)));
    }

    @То("На главной странице не отображается поле {string}")
    public void assertProjectElementIsNotDisplayed(String fieldName) {
        Assert.assertFalse(BrowserUtils.isElementCurrentlyPresent(CucumberPageObjectHelper.getElementBy("Заголовок", fieldName)));
    }

    @И("На странице {string} отображается элемент {string}")
    public void assertFieldIsDisplayed(String pageName, String fieldName) {
        WebElement element = CucumberPageObjectHelper.getElementBy(pageName, fieldName);
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(element));
    }

    @И("На странице {string} не отображается элемент {string}")
    public void assertFieldIsNotDisplayed(String pageName, String fieldName) {
        WebElement element = CucumberPageObjectHelper.getElementBy(pageName, fieldName);
        Assert.assertFalse(BrowserUtils.isElementCurrentlyPresent(element));
    }

    @То("Отображается сообщение {string}")
    public void assertCreationMessage(String rawString) throws IllegalAccessException {
        String result= ParametersValidator.replaceCucumberVariables(rawString);
        WebElement element = CucumberPageObjectHelper.getElementBy("Страница создания нового пользователя", "Уведомление о создании нового пользователя");
        Asserts.assertEquals(element.getText(), result);
    }

    @И("Отображается проект {string}")
    public void assertProjectNameAndDescriptionDisplayed(String projectStashId) {
        Project project = Context.get(projectStashId, Project.class);
        String projectExpectedName = project.getName();
        String projectExpectedDescription = project.getDescription();
        String actualName = ProjectsPage.projectName(projectExpectedName);
        String actualDescription = ProjectsPage.projectNameDescription(projectExpectedName);
        Asserts.assertEquals(actualName, projectExpectedName);
        Asserts.assertEquals(actualDescription, projectExpectedDescription);
    }

    @И("Не Отображается проект {string}")
    public void assertProjectNameAndDescriptionNotDisplayed(String projectStashId) {
        Project project = Context.get(projectStashId, Project.class);
        String projectExpectedName = project.getName();
        String projectExpectedDescription = project.getDescription();
        Assert.assertFalse(getPage(ProjectsPage.class).isProjectNameIsSituatingInListOfProjects(projectExpectedName));
        Assert.assertFalse(getPage(ProjectsPage.class).isProjectDescriptionIsSituatingInListOfProjects(projectExpectedDescription));
    }

    @И("Таблица пользователей не отсортирована по столбцу {string}")
    public void assertUnSorting(String fieldElement) {
        if (fieldElement.equals("Фамилия")) {
            List<String> actualList = getPage(UsersPage.class).listOfLastNamesInTable
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
            List<String> notExpectedOrderedByAscList = actualList
                    .stream()
                    .sorted()
                    .collect(Collectors.toList());
            Assert.assertNotEquals(actualList, notExpectedOrderedByAscList);
        } else if (fieldElement.equals("Имя")) {
            List<String> actualList = getPage(UsersPage.class).listOfNamesInTable
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
            List<String> notExpectedOrderedByAscList = actualList
                    .stream()
                    .sorted()
                    .collect(Collectors.toList());

            Assert.assertNotEquals(actualList, notExpectedOrderedByAscList);
        } else {
            throw new IllegalArgumentException("Не корректный параметр " + fieldElement);
        }

    }

    @И("Таблица пользователей отсортирована по столбцу {string}, по убыванию")
    public void assertSortingByDesc(String fieldElement) {
        if (fieldElement.equals("Фамилия")) {
            List<String> actualList = getPage(UsersPage.class).listOfLastNamesInTable
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
            List<String> expectedOrderedByDescList = actualList
                    .stream()
                    .sorted(String.CASE_INSENSITIVE_ORDER.reversed())
                    .collect(Collectors.toList());
            Asserts.assertEquals(actualList, expectedOrderedByDescList);
        } else if (fieldElement.equals("Имя")) {
            List<String> actualList = getPage(UsersPage.class).listOfNamesInTable
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
            List<String> expectedOrderedByDescList = actualList
                    .stream()
                    .sorted(String.CASE_INSENSITIVE_ORDER.reversed())
                    .collect(Collectors.toList());
            Asserts.assertEquals(actualList, expectedOrderedByDescList);
        } else if (fieldElement.equals("Логин")) {
            List<String> actualList = getPage(UsersPage.class).listOfUsernamesInTable
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
            List<String> expectedOrderedByDescList = actualList
                    .stream()
                    .sorted(String.CASE_INSENSITIVE_ORDER.reversed())
                    .collect(Collectors.toList());
            Asserts.assertEquals(actualList, expectedOrderedByDescList);
        } else {
            throw new IllegalArgumentException("Не корректный параметр " + fieldElement);
        }
    }

    @И("Таблица пользователей отсортирована по столбцу {string}, по возрастанию")
    public void assertSortingByAsc(String fieldElement) {
        if (fieldElement.equals("Фамилия")) {
            List<String> actualList = getPage(UsersPage.class).listOfLastNamesInTable
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
            List<String> expectedOrderedByAscList = actualList
                    .stream()
                    .sorted(String::compareToIgnoreCase)
                    .collect(Collectors.toList());

            Asserts.assertEquals(actualList, expectedOrderedByAscList);
        } else if (fieldElement.equals("Имя")) {
            List<String> actualList = getPage(UsersPage.class).listOfNamesInTable
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
            List<String> expectedOrderedByAscList = actualList
                    .stream()
                    .sorted(String::compareToIgnoreCase)
                    .collect(Collectors.toList());

            Asserts.assertEquals(actualList, expectedOrderedByAscList);
        } else if (fieldElement.equals("Логин")) {
            List<String> actualList = getPage(UsersPage.class).listOfUsernamesInTable
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
            List<String> expectedOrderedByAscList = actualList
                    .stream()
                    .sorted(String::compareToIgnoreCase)
                    .collect(Collectors.toList());

            Asserts.assertEquals(actualList, expectedOrderedByAscList);
        } else {
            throw new IllegalArgumentException("Не корректный параметр " + fieldElement);
        }
    }

    @И("На странице {string} отображается таблица {string}")
    public void assertTableIsDisplayed(String pageName, String fieldName) {
        WebElement element = CucumberPageObjectHelper.getElementBy(pageName, fieldName);
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(element));
    }

}
