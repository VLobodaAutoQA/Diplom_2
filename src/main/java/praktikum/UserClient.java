package praktikum;

import io.restassured.response.ValidatableResponse;

import java.util.Map;

public class UserClient extends Client {
    public static final String USER = "/user";

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
}
