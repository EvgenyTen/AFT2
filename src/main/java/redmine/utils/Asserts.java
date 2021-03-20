package redmine.utils;

import io.qameta.allure.Step;
import org.testng.Assert;

public class Asserts {
    @Step("Сравнение переменных actual: {0}, expected: {1}")
    public static void assertEquals(Object actual, Object expected) {
        Assert.assertEquals(actual, expected);
    }

    @Step("Проверка переменной actual: {0}, на присутствие")
    public static void assertNotNull(Object actual) { Assert.assertNotNull(actual); }

    @Step("Проверка переменной actual: {0}, на отсутствие")
    public static void assertNull(Object actual) { Assert.assertNull(actual); }
}
