package cucumber.steps;

import cucumber.api.java.ru.И;
import cucumber.api.java.ru.То;
import org.testng.Assert;
import redmine.api.interfaces.Response;
import redmine.managers.Context;
import redmine.managers.Manager;
import redmine.model.dto.UserCreationError;
import redmine.model.dto.UserDto;
import redmine.model.user.User;
import redmine.utils.Asserts;
import java.util.List;
import java.util.Map;
import static redmine.utils.gson.GsonHelper.getGson;

public class RequestAssertionSteps {

    @И("Получен статус код ответа {int}")
    public void assertAnswerCode(int expectedCode) {
        Response response = Context.get("response", Response.class);
        Asserts.assertEquals(response.getStatusCode(), expectedCode);
    }

    @И("В базе данных появилась запись с данными {string}")
    public void assertUserInformationAfterCreationInDb(String userDataStashId) {
        User userContext = Context.get(userDataStashId, User.class);
        String query = String.format("select * from users where login='%s'", userContext.getLogin());
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        Assert.assertEquals(result.size(), 1, "Проверка размера результата");
        Map<String, Object> dbUser = result.get(0);
        Asserts.assertEquals(dbUser.get("login"), userContext.getLogin());
        Asserts.assertEquals(dbUser.get("firstname"), userContext.getFirstName());
        Asserts.assertEquals(dbUser.get("lastname"), userContext.getLastName());
    }

    @То("Тело содержит данные созданного пользователя {string}")
    public void assertUserInformationExistInBody(String userStashDto) {
        UserDto userContext = Context.get(userStashDto, UserDto.class);
        Response response = Context.get("response", Response.class);
        UserDto createdUser = response.getBody(UserDto.class);
        Assert.assertNotNull(createdUser.getUser().getId());
        Asserts.assertEquals(createdUser.getUser().getLogin(), userContext.getUser().getLogin());
        Asserts.assertEquals(createdUser.getUser().getFirstname(), userContext.getUser().getFirstname());
        Asserts.assertEquals(createdUser.getUser().getLastname(), userContext.getUser().getLastname());
        Assert.assertNull(createdUser.getUser().getPassword());
        Asserts.assertEquals(createdUser.getUser().getMail(), userContext.getUser().getMail());
        Assert.assertNull(createdUser.getUser().getLast_login_on());
        Asserts.assertEquals(createdUser.getUser().getStatus(), userContext.getUser().getStatus());
        Asserts.assertEquals(createdUser.getUser().getAdmin(), userContext.getUser().getAdmin());
    }

    @И("В базе данных появилась запись с данными пользователя {string}")
    public void assertUserInformationExistAfterCreationInDb(String userDataStashId) {
        UserDto userContext = Context.get(userDataStashId, UserDto.class);
        String query = String.format("select * from users where login=?");
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query, userContext.getUser().getLogin());
        Assert.assertEquals(result.size(), 1, "Проверка размера результата");
        Map<String, Object> dbUser = result.get(0);
        Asserts.assertEquals(dbUser.get("login"), userContext.getUser().getLogin());
        Asserts.assertEquals(dbUser.get("firstname"), userContext.getUser().getFirstname());
        Asserts.assertEquals(dbUser.get("lastname"), userContext.getUser().getLastname());
        Asserts.assertEquals(dbUser.get("status"), userContext.getUser().getStatus());
    }

    @То("Тело ответа содержит {int} ошибки,с текстом:{string},{string},{string}")
    public void errorsCheck(Integer errorNumber, String errorEmail, String errorLogin, String errorChars) {
        if (errorNumber == 2) {
            Response response = Context.get("response", Response.class);
            UserCreationError errors = getGson().fromJson(response.getBody().toString(), UserCreationError.class);
            Asserts.assertEquals(errors.getErrors().size(), 2);
            Asserts.assertEquals(errors.getErrors().get(0), "Email уже существует");
            Asserts.assertEquals(errors.getErrors().get(1), "Пользователь уже существует");
        }
        if (errorNumber == 3) {
            Response response = Context.get("response", Response.class);
            UserCreationError errors = getGson().fromJson(response.getBody().toString(), UserCreationError.class);
            Asserts.assertEquals(errors.getErrors().size(), 3);
            Asserts.assertEquals(errors.getErrors().get(0), "Email имеет неверное значение");
            Asserts.assertEquals(errors.getErrors().get(1), "Пользователь уже существует");
            Asserts.assertEquals(errors.getErrors().get(2), "Пароль недостаточной длины (не может быть меньше 8 символа)");
        }
    }

    @То("В базе данных изменилась запись с данными пользователя {string}")
    public void assertUserInformationChangedAfterPutRequest(String userStashDto) {
        UserDto userContext = Context.get(userStashDto, UserDto.class);
        String query = String.format("select * from users where login='%s'", userContext.getUser().getLogin());
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        Map<String, Object> dbUser = result.get(0);
        Asserts.assertEquals(dbUser.get("status"), 1);
    }

    @То("В базе данных отсутствует информация о пользователе {string}, созданном {string}")
    public void assertUserInformationAbsentInDbAfterDeleteRequest(String userStashDto, String stashId) {
        UserDto userContext = Context.get(userStashDto, UserDto.class);
        String query = String.format("select * from users where login='%s'", userContext.getUser().getLogin());
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        Assert.assertEquals(result.size(), 0, "Проверка отсутствия");
    }

    @То("В теле содержится информация пользователя {string} о самом себе, присутcтвуют поля admin и apikey")
    public void assertUserInformationExistWithApiAndAdmin(String stashId) {
        User user = Context.get(stashId, User.class);
        Response response = Context.get("response", Response.class);
        UserDto createdUser = response.getBody(UserDto.class);
        Assert.assertNotNull(createdUser.getUser().getId());
        Asserts.assertEquals(createdUser.getUser().getLogin(), user.getLogin());
        Asserts.assertEquals(createdUser.getUser().getFirstname(), user.getFirstName());
        Asserts.assertEquals(createdUser.getUser().getLastname(), user.getLastName());
        Assert.assertNull(createdUser.getUser().getPassword());
        Assert.assertEquals(createdUser.getUser().getAdmin(), user.getAdmin());
        Assert.assertEquals(createdUser.getUser().getApi_key(), user.getApiKey());
    }

    @И("В теле содержится информация о пользователе {string}")
    public void assertUserInformationExistInDb(String stashId) {
        User userContext = Context.get(stashId, User.class);
        String query = String.format("select * from users where login='%s'", userContext.getLogin());
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        Assert.assertEquals(result.size(), 1, "Проверка размера результата");
        Map<String, Object> dbUser = result.get(0);
        Asserts.assertEquals(dbUser.get("login"), userContext.getLogin());
        Asserts.assertEquals(dbUser.get("firstname"), userContext.getFirstName());
        Asserts.assertEquals(dbUser.get("lastname"), userContext.getLastName());
        Asserts.assertEquals(dbUser.get("status"), userContext.getStatus());
    }

    @То("В теле содержится информация пользователя {string}, отсутствуют поля admin и apikey")
    public void assertUserInformationExistWithoutApiAndAdmin(String stashId) {
        User user = Context.get(stashId, User.class);
        Response response = Context.get("response", Response.class);
        UserDto createdUser = response.getBody(UserDto.class);
        Assert.assertNotNull(createdUser.getUser().getId());
        Asserts.assertEquals(createdUser.getUser().getLogin(), user.getLogin());
        Asserts.assertEquals(createdUser.getUser().getFirstname(), user.getFirstName());
        Asserts.assertEquals(createdUser.getUser().getLastname(), user.getLastName());
        Assert.assertNull(createdUser.getUser().getPassword());
        Assert.assertNull(createdUser.getUser().getAdmin());
        Assert.assertNull(createdUser.getUser().getApi_key());
    }
}
