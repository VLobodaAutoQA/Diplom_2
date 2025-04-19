package praktikum;

import io.restassured.response.ValidatableResponse;

import java.util.Map;

public class UserClient extends Client {
    public static final String USER = "/user";
    User editName = new User("vladtest" + + System.currentTimeMillis() + "@yandex.rus", "11111111", "VLoboda" + System.currentTimeMillis());

    private String accessToken;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public ValidatableResponse loginUser(Credentials creds) {
        return Client.spec()
                .body(creds)
                .when()
                .post("/login")
                .then().log().all();
    }

    public ValidatableResponse createUser(User user) {
        return Client.spec()
                .body(user)
                .when()
                .post("/register")
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

//    public ValidatableResponse editUser(User edit) {
//    }
}
