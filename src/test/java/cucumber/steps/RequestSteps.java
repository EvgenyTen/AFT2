package cucumber.steps;

import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.То;
import org.testng.Assert;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.HttpMethods;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.managers.Context;
import redmine.managers.Manager;
import redmine.model.dto.UserDto;
import redmine.model.dto.UserInfo;
import redmine.model.user.User;
import java.util.List;
import java.util.Map;
import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;
import static redmine.utils.gson.GsonHelper.getGson;

public class RequestSteps {

    @Если("Отправить запрос на создание пользователя {string} пользователем {string} со статусом: {int}")
    public void sendRequestOnUserCreation(String userStashDto, String stashId, int status) {
        User user = Context.get(stashId, User.class);
        ApiClient apiClient = new RestApiClient(user);
        String login = randomEnglishLowerString(8);
        String name = randomEnglishLowerString(8);
        String lastName = randomEnglishLowerString(8);
        String password = randomEnglishLowerString(8);
        UserDto userDto = new UserDto().setUser(new UserInfo().setLogin(login).setFirstname(name)
                    .setLastname(lastName).setMail(randomEmail()).setStatus(status).setPassword(password).setAdmin(false));
        String body = getGson().toJson(userDto);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        Context.put(userStashDto, userDto);
        Context.put("response", response);
    }

    @Если("Отправить повторный запрос на создание пользователя {string} пользователем {string} с тем же телом запроса")
    public void sendRepeatedRequest(String userStashDto, String stashId) {
        UserDto userContext = Context.get(userStashDto, UserDto.class);
        User user = Context.get(stashId, User.class);
        ApiClient apiClient = new RestApiClient(user);
        String body = getGson().toJson(userContext);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        Context.put("response", response);
    }

    @То("Отправить НЕ корректный запрос на создание пользователя {string} пользователем {string}")
    public void sendIncorrectRequest(String userStashDto, String stashId) {
        String incorrectMail = "santa.claus.petersburg";
        String incorrectPassword = randomEnglishLowerString(4);
        UserDto userContext = Context.get(userStashDto, UserDto.class);
        User user = Context.get(stashId, User.class);
        ApiClient apiClient = new RestApiClient(user);
        UserDto incorrectUser = new UserDto().setUser(new UserInfo().setLogin(userContext.getUser().getLogin())
                .setFirstname(userContext.getUser().getFirstname())
                .setLastname(userContext.getUser().getLastname())
                .setMail(incorrectMail)
                .setPassword(incorrectPassword));
        String incorrectBody = getGson().toJson(incorrectUser);
        Request incorrectRequest = new RestRequest("users.json", HttpMethods.POST, null, null, incorrectBody);
        Response response = apiClient.executeRequest(incorrectRequest);
        Context.put("response", response);
    }

    @Если("Отправить запрос на изменение пользователя {string} пользователем {string}")
    public void sendRequestOnUserChange(String userStashDto, String stashId) {
        User user = Context.get(stashId, User.class);
        ApiClient apiClient = new RestApiClient(user);
        UserDto userContext = Context.get(userStashDto, UserDto.class);

        String query = String.format("select * from users where login='%s'", userContext.getUser().getLogin());
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        Assert.assertEquals(result.size(), 1, "Проверка размера результата");
        Map<String, Object> dbUser = result.get(0);
        Integer userId = (Integer) dbUser.get("id");
        Integer newStatus = 1;
        UserDto userDto = new UserDto().setUser(new UserInfo()
                .setLogin(userContext.getUser().getLogin())
                .setAdmin(false)
                .setFirstname(userContext.getUser().getFirstname())
                .setLastname(userContext.getUser().getLastname())
                .setMail(userContext.getUser().getMail())
                .setPassword(userContext.getUser().getPassword())
                .setStatus(newStatus));
        String statusBody = getGson().toJson(userDto);
        String uri = String.format("users/%d.json", userId);
        Request putRequest = new RestRequest(uri, HttpMethods.PUT, null, null, statusBody);
        Response response = apiClient.executeRequest(putRequest);
        Context.put(userStashDto, userDto);
        Context.put("response", response);
    }

    @Если("Отправить запрос на получении инфо о пользователе {string} пользователем {string}")
    public void sendRequestOnUserGet(String userStashDto, String stashId) {
        UserDto userContext = Context.get(userStashDto, UserDto.class);
        User user = Context.get(stashId, User.class);
        ApiClient apiClient = new RestApiClient(user);

        String query = String.format("select * from users where login='%s'", userContext.getUser().getLogin());
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        Map<String, Object> dbUser = result.get(0);
        Integer userId = (Integer) dbUser.get("id");
        String uri = String.format("users/%d.json", userId);
        Request request = new RestRequest(uri, HttpMethods.GET, null, null, null);
        Response response = apiClient.executeRequest(request);
        UserDto userDto = response.getBody(UserDto.class);
        Context.put(userStashDto, userDto);
        Context.put("response", response);
    }

    @Если("Отправить запрос на удаление пользователя {string} пользователем {string}")
    public void sendRequestOnUserDelete(String userStashDto, String stashId) {
        UserDto userContext = Context.get(userStashDto, UserDto.class);
        User user = Context.get(stashId, User.class);
        ApiClient apiClient = new RestApiClient(user);
        Integer userId = userContext.getUser().getId();
        String uri = String.format("users/%d.json", userId);
        Request request = new RestRequest(uri, HttpMethods.DELETE, null, null, null);
        Response response = apiClient.executeRequest(request);
        UserDto userDto = response.getBody(UserDto.class);
        Context.put("response", response);
    }

    @Если("Отправить запрос на удаление несуществующего пользователя {string} пользователем {string}")
    public void sendRequestOnNonExistUserDelete(String userStashDto, String stashId) {
        UserDto userContext = Context.get(userStashDto, UserDto.class);
        User user = Context.get(stashId, User.class);
        ApiClient apiClient = new RestApiClient(user);
        Integer userId = userContext.getUser().getId();
        String uri = String.format("users/%d.json", userId);
        Request request = new RestRequest(uri, HttpMethods.DELETE, null, null, null);
        Response response = apiClient.executeRequest(request);
        UserDto userDto = response.getBody(UserDto.class);
        Context.put("response", response);
    }


    @Если("Отправить запрос на {string} пользователя {string} пользователем {string}")
    public void sendRequestOnOperationRequest(String operation, String stashId,String stashId2) {
        User user2 = Context.get(stashId2, User.class);
        ApiClient apiClient = new RestApiClient(user2);
        User user=Context.get(stashId, User.class);
        String uri = String.format("users/%d.json", user.getId());

        if (operation.equals("получение")) {
            Request request = new RestRequest(uri, HttpMethods.GET, null, null, null);
            Response response = apiClient.executeRequest(request);
            Context.put(stashId, user);
            Context.put("response", response);
        }

        if (operation.equals("удаление")) {
            Request request = new RestRequest(uri, HttpMethods.DELETE, null, null, null);
            Response response = apiClient.executeRequest(request);
            Context.put(stashId, user);
            Context.put("response", response);
        }
    }
}