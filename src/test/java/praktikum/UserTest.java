package praktikum;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class UserTest {
    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    private String accessToken;

    // 1. Создать уникального пользователя
    @Test
    public void testSuccessfulUserRegistration() {

        // Создание пользователя
        var user = User.accountForCreateUser();
        ValidatableResponse createResponse = client.createUser(user);
        check.isCreated(createResponse);

        // Логин пользователя
        var creds = Credentials.loginUser(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.loginSuccess(loginResponse);
    }

//     2. Создать пользователя, который уже зарегистрирован
    @Test
    public void testDuplicateUserRegistration() {
        // Создание пользователя
        var user = User.accountForCreateUser();
        ValidatableResponse createResponse = client.createUser(user);
        check.isCreated(createResponse);

        // Пытаемся зарегистрировать повторно
        ValidatableResponse duplicateResponse = client.createUser(user);
        // Проверяем, что нельзя создать двух одинаковых пользователей
        int duplicateStatusCode = duplicateResponse.extract().statusCode();
        assertEquals(403, duplicateStatusCode);

        // Логин пользователя
        var creds = Credentials.loginUser(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.loginSuccess(loginResponse);
    }

    // 3. Создать пользователя и не заполнить одно из обязательных полей.
    @Test
    public void testRegistrationWithoutRequiredField() {
        // Попытка создать пользователя без email
        User noEmail = new User(null, User.accountForCreateUser().getPassword(), User.accountForCreateUser().getName());
        ValidatableResponse responseNoEmail = client.createUser(noEmail);
        int statusCodeNoEmail = responseNoEmail.extract().statusCode();
        assertEquals(403, statusCodeNoEmail);

        // Попытка создать пользователя без password
        User noPassword = new User(User.accountForCreateUser().getEmail(), null, User.accountForCreateUser().getName());
        ValidatableResponse responseNoPassword = client.createUser(noPassword);
        int statusCodeNoPassword = responseNoPassword.extract().statusCode();
        assertEquals(403, statusCodeNoPassword);

        // Попытка создать пользователя без name
        User noName = new User(User.accountForCreateUser().getEmail(), User.accountForCreateUser().getPassword(), null);
        ValidatableResponse responseNoName = client.createUser(noName);
        int statusCodeNoName = responseNoPassword.extract().statusCode();
        assertEquals(403, statusCodeNoName);

    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            client.deleteUser(accessToken);
        }
    }
}

