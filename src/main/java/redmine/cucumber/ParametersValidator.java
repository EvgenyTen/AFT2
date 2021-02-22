package redmine.cucumber;

import org.testng.Assert;

import java.util.Map;

public class ParametersValidator {
    public static void validateRoleParameters(Map<String, String> parameters) {
        parameters.forEach((key, value) -> Assert.assertTrue(AllowedParameters.ROLE_PARAMETERS.contains(key),
                "Список допустимых параметров по работе с ролями не содержит параметр" + key));
    }

    public static void validateUserParameters(Map<String, String> parameters) {
        parameters.forEach((key, value) -> Assert.assertTrue(AllowedParameters.USER_PARAMETERS.contains(key),
                "Список допустимых параметров по работе с пользователями не содержит параметр" + key));
    }

    public static void validateProjectParameters(Map<String, String> parameters) {
        parameters.forEach((key, value) -> Assert.assertTrue(AllowedParameters.PROJECT_PARAMETERS.contains(key),
                "Список допустимых параметров по работе с проектами не содержит параметр" + key));
    }

    public static void validateErrorParameters(Map<String, String> parameters) {
        parameters.forEach((key, value) -> Assert.assertTrue(AllowedParameters.ERROR_TEXTS.contains(key),
                "Список допустимых ошибок не содержит параметр" + key));
    }

}
