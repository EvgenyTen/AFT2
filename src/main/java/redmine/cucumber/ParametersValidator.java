package redmine.cucumber;

import org.testng.Assert;
import redmine.managers.Context;
import redmine.ui.pages.helpers.CucumberName;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

public class ParametersValidator {
    public static void validateRoleParameters(Map<String, String> parameters) {
        parameters.forEach((key, value) -> Assert.assertTrue(AllowedParameters.ROLE_PARAMETERS.contains(key),
                "Список допустимых параметров по работе с ролями не содержит параметр : " + key));
    }

    public static void validateUserParameters(Map<String, String> parameters) {
        parameters.forEach((key, value) -> Assert.assertTrue(AllowedParameters.USER_PARAMETERS.contains(key),
                "Список допустимых параметров по работе с пользователями не содержит параметр : " + key));
    }

    public static void validateProjectParameters(Map<String, String> parameters) {
        parameters.forEach((key, value) -> Assert.assertTrue(AllowedParameters.PROJECT_PARAMETERS.contains(key),
                "Список допустимых параметров по работе с проектами не содержит параметр : " + key));
    }

    public static String replaceCucumberVariables(String rawString) throws IllegalAccessException {
        while (rawString.contains("${")) {
            String replacement = rawString.substring(rawString.indexOf("${"), rawString.indexOf("}") + 1);
            String stashId = replacement.substring(2, replacement.indexOf("->"));
            String fieldDescription = replacement.substring(replacement.indexOf("->") + 2, replacement.length() - 1);
            Object stashObject = Context.get(stashId.trim());
            Field foundField = Arrays.stream(stashObject.getClass().getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(CucumberName.class))
                    .filter(field -> field.getAnnotation(CucumberName.class).value().equals(fieldDescription.trim()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Не задана анотация @CucumberName" + stashObject));
            foundField.setAccessible(true);
            String result=foundField.get(stashObject).toString();
            rawString=rawString.replace(replacement,result);
        }
        return rawString;
    }
}
