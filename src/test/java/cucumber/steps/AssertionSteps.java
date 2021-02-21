package cucumber.steps;

import cucumber.api.java.ru.Тогда;
import lombok.SneakyThrows;
import redmine.cucumber.ParametersValidator;
import redmine.managers.Context;
import redmine.model.role.*;
import redmine.utils.Asserts;

import java.util.Map;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class AssertionSteps {
    @SneakyThrows
    @Тогда("Роль {string} имеет параметры:")
    public void assertRoleParameters(String roleStashId, Map<String, String> parameters) {
        Role role = Context.get(roleStashId, Role.class);
        ParametersValidator.validateRoleParameters(parameters);
        if (parameters.containsKey("Позиция")) {
            Asserts.assertEquals(role.getPosition(), valueOf(parseInt(parameters.get("Позиция"))));
        }
        if (parameters.containsKey("Встроенная")) {
            Asserts.assertEquals(role.getBuiltin(), valueOf(parseInt(parameters.get("Встроенная"))));
        }
        if (parameters.containsKey("Задача может быть назначена этой роли")) {
            Asserts.assertEquals(role.getAssignable(), Boolean.valueOf(parseBoolean(parameters.get("Задача может быть назначена этой роли"))));
        }
        if (parameters.containsKey("Видимость задач")) {
            Asserts.assertEquals(role.getIssuesVisibility(), IssuesVisibility.of(parameters.get("Видимость задач")));
        }
        if (parameters.containsKey("Видимость пользователей")) {
            Asserts.assertEquals(role.getUsersVisibility(), UsersVisibility.of(parameters.get("Видимость пользователей")));
        }
        if (parameters.containsKey("Видимость трудозатрат")) {
            Asserts.assertEquals(role.getTimeEntriesVisibility(), TimeEntriesVisibility.of(parameters.get("Видимость трудозатрат")));
        }
        if (parameters.containsKey("Права")) {
            RolePermissions expectedPermissions = Context.get(parameters.get("Права"), RolePermissions.class);
            RolePermissions actualPermissions = role.getPermissions();
            Asserts.assertEquals(actualPermissions, expectedPermissions);
        }
    }

}
