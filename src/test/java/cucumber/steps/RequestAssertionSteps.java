package cucumber.steps;

import cucumber.api.java.ru.И;
import cucumber.api.java.ru.То;
import org.testng.Assert;
import redmine.api.interfaces.Response;
import redmine.db.requests.UserRequests;
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
        User dbUser = UserRequests.getUserByLogin(userContext.getLogin()).get(0);
        Asserts.assertEquals(dbUser.getLogin(), userContext.getLogin());
        Asserts.assertEquals(dbUser.getFirstName(), userContext.getFirstName());
        Asserts.assertEquals(dbUser.getLastName(), userContext.getLastName());
    }

    @То("Тело содержит данные созданного пользователя {string}")
    public void assertUserInformationExistInBody(String userStashId) {
        UserDto userContext = Context.get(userStashId, UserDto.class);
        Response response = Context.get("response", Response.class);
        UserDto createdUser = response.getBody(UserDto.class);
        Asserts.assertNotNull(createdUser.getUser().getId());
        Asserts.assertEquals(createdUser.getUser().getLogin(), userContext.getUser().getLogin());
        Asserts.assertEquals(createdUser.getUser().getFirstname(), userContext.getUser().getFirstname());
        Asserts.assertEquals(createdUser.getUser().getLastname(), userContext.getUser().getLastname());
        Asserts.assertNull(createdUser.getUser().getPassword());
        Asserts.assertEquals(createdUser.getUser().getMail(), userContext.getUser().getMail());
        Asserts.assertNull(createdUser.getUser().getLast_login_on());
        Asserts.assertEquals(createdUser.getUser().getStatus(), userContext.getUser().getStatus());
        Asserts.assertEquals(createdUser.getUser().getAdmin(), userContext.getUser().getAdmin());
    }

    @И("В базе данных появилась запись с данными пользователя {string}")
    public void assertUserInformationExistAfterCreationInDb(String userDataStashId) {
        UserDto userContext = Context.get(userDataStashId, UserDto.class);
        User dbUser = UserRequests.getUserByLogin(userContext.getUser().getLogin()).get(0);
        Asserts.assertEquals(dbUser.getLogin(), userContext.getUser().getLogin());
        Asserts.assertEquals(dbUser.getFirstName(), userContext.getUser().getFirstname());
        Asserts.assertEquals(dbUser.getLastName(), userContext.getUser().getLastname());
        Asserts.assertEquals(dbUser.getStatus(), userContext.getUser().getStatus());
    }

    @То("Тело ответа содержит ошибки,с текстами:")
    public void errorsCheck(List<String> errorMessages) {
        int receivedErrorsCount = errorMessages.size();
        Response response = Context.get("response", Response.class);
        UserCreationError errors = getGson().fromJson(response.getBody().toString(), UserCreationError.class);
        Asserts.assertEquals(errors.getErrors().size(), receivedErrorsCount);
        errorMessages.forEach(message -> Assert.assertTrue(errors.getErrors().contains(message)));
    }

    @То("В базе данных изменилась запись с данными пользователя {string}")
    public void assertUserInformationChangedAfterPutRequest(String userStashId) {
        UserDto userContext = Context.get(userStashId, UserDto.class);
        User dbUser = UserRequests.getUserByLogin(userContext.getUser().getLogin()).get(0);
        Asserts.assertEquals(dbUser.getStatus(), 1);
    }

    @То("В базе данных отсутствует информация о пользователе {string}, созданном администратором")
    public void assertUserInformationAbsentInDbAfterDeleteRequest(String userStashId) {
        UserDto userContext = Context.get(userStashId, UserDto.class);
        String query = "select * from users where login=?";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query, userContext.getUser().getLogin());
        Assert.assertEquals(result.size(), 0, "Проверка отсутствия");
    }

    @То("В теле содержится информация пользователя {string} о самом себе, присутcтвуют поля admin и apikey")
    public void assertUserInformationExistWithApiAndAdmin(String stashId) {
        User user = Context.get(stashId, User.class);
        Response response = Context.get("response", Response.class);
        UserDto createdUser = response.getBody(UserDto.class);
        Asserts.assertNotNull(createdUser.getUser().getId());
        Asserts.assertEquals(createdUser.getUser().getLogin(), user.getLogin());
        Asserts.assertEquals(createdUser.getUser().getFirstname(), user.getFirstName());
        Asserts.assertEquals(createdUser.getUser().getLastname(), user.getLastName());
        Asserts.assertNull(createdUser.getUser().getPassword());
        Asserts.assertEquals(createdUser.getUser().getAdmin(), user.getAdmin());
        Asserts.assertEquals(createdUser.getUser().getApi_key(), user.getApiKey());
    }

    @И("В теле содержится информация о пользователе {string}")
    public void assertUserInformationExistInDb(String stashId) {
        User userContext = Context.get(stashId, User.class);
        User dbUser = UserRequests.getUserByLogin(userContext.getLogin()).get(0);
        Asserts.assertEquals(dbUser.getLogin(), userContext.getLogin());
        Asserts.assertEquals(dbUser.getFirstName(), userContext.getFirstName());
        Asserts.assertEquals(dbUser.getLastName(), userContext.getLastName());
        Asserts.assertEquals(dbUser.getStatus(), userContext.getStatus());
    }

    @То("В теле содержится информация пользователя {string}, отсутствуют поля admin и apikey")
    public void assertUserInformationExistWithoutApiAndAdmin(String stashId) {
        User user = Context.get(stashId, User.class);
        Response response = Context.get("response", Response.class);
        UserDto createdUser = response.getBody(UserDto.class);
        Asserts.assertNotNull(createdUser.getUser().getId());
        Asserts.assertEquals(createdUser.getUser().getLogin(), user.getLogin());
        Asserts.assertEquals(createdUser.getUser().getFirstname(), user.getFirstName());
        Asserts.assertEquals(createdUser.getUser().getLastname(), user.getLastName());
        Asserts.assertNull(createdUser.getUser().getPassword());
        Asserts.assertNull(createdUser.getUser().getAdmin());
        Asserts.assertNull(createdUser.getUser().getApi_key());
    }
}
