package praktikum;

import io.restassured.response.ValidatableResponse;

public class UserClient extends Client {
    public static final String USER = "/auth/user";
    public static final String LOGIN = "/auth/login";
    public static final String REGISTER = "/auth/register";

    private String accessToken;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public ValidatableResponse loginUser(Credentials creds) {
        return Client.spec()
                .body(creds)
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    public ValidatableResponse createUser(User user) {
        return Client.spec()
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all();
    }

    public ValidatableResponse deleteUser(String accessToken) {
        return Client.spec()
                .header("Authorization", accessToken)
                .when()
                .delete(USER)
                .then().log().all();
    }

    public ValidatableResponse editUser(User edit) {
        return Client.spec()
                .header("Authorization", this.accessToken)
                .body(edit)
                .when()
                .patch(USER)
                .then().log().all();
    }

    public ValidatableResponse editUserWithoutToken(User edit) {
        return Client.spec()
                .body(edit)
                .when()
                .patch(USER)
                .then().log().all();
    }
}
