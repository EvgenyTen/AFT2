package bddTests.steps;

import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.То;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.HttpMethods;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.managers.Context;
import redmine.model.dto.UserDto;
import redmine.model.dto.UserInfo;
import redmine.model.user.User;

import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;
import static redmine.utils.gson.GsonHelper.getGson;

public class RequestSteps {

    @Если("Отправить запрос на создание пользователя {string} {string} {string} со статусом:{int}")
    public void answerOnUserCreationRequest(String userStashDto, String userType,String stashId,int status) {
        User user = Context.get(stashId, User.class);
        ApiClient apiClient = new RestApiClient(user);
        if (userType.equals("пользователем")){
        String login = randomEnglishLowerString(8);
        String name = randomEnglishLowerString(8);
        String lastName = randomEnglishLowerString(8);
        String password = randomEnglishLowerString(8);
        UserDto userDto = new UserDto().setUser(new UserInfo().setLogin(login).setFirstname(name)
                .setLastname(lastName).setMail(randomEmail()).setStatus(status).setPassword(password).setAdmin(false));
        String body = getGson().toJson(userDto);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        Context.put(userStashDto,userDto);
        Context.put("response",response);}
        else{
            String login = randomEnglishLowerString(8);
            String name = randomEnglishLowerString(8);
            String lastName = randomEnglishLowerString(8);
            String password = randomEnglishLowerString(8);
            UserDto userDto = new UserDto().setUser(new UserInfo().setLogin(login).setFirstname(name)
                    .setLastname(lastName).setMail(randomEmail()).setStatus(status).setPassword(password).setAdmin(true));
            String body = getGson().toJson(userDto);
            Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
            Response response = apiClient.executeRequest(request);
            Context.put(userStashDto,userDto);
            Context.put("response",response);}
    }

    @Если("Отправить повторный запрос на создание пользователя {string} пользователем {string} с тем же телом запроса")
    public void repeatedRequestDto(String userStashDto,String stashId){
        UserDto userContext = Context.get(userStashDto, UserDto.class);
        User user = Context.get(stashId, User.class);
        ApiClient apiClient = new RestApiClient(user);
        String body = getGson().toJson(userContext);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        Context.put("response",response);
    }

    @То("Отправить НЕ корректный запрос на создание пользователя {string} пользователем {string}")
    public void incorrectRequestDto(String userStashDto,String stashId){
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
        Context.put("response",response);
            }
    @Если ("Отправить запрос на изменение пользователя {string} пользователем {string}")
    public void changeRequestDto(String userStashDto,String stashId){
        UserDto userContext = Context.get(userStashDto, UserDto.class);
        User user = Context.get(stashId, User.class);
        ApiClient apiClient = new RestApiClient(user);
        String uri = String.format("users/%d.json", userContext.getUser().getId());

        UserDto putUser = new UserDto().setUser(new UserInfo().setLogin(userContext.getUser()
                .getLogin())
                .setStatus(1));
        String putBody = getGson().toJson(putUser);
        Request putRequest = new RestRequest(uri, HttpMethods.PUT, null, null, putBody);
        Response response = apiClient.executeRequest(putRequest);
        Context.put("response",response);
    }



}
