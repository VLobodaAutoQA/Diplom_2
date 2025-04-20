package praktikum;

import io.restassured.response.ValidatableResponse;

public class OrderClient extends Client {
    public static final String ORDER = "/orders";

    private String accessToken;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public ValidatableResponse createOrder (Order order) {
        return Client.spec()
                .header("Authorization", this.accessToken)
                .body(order)
                .when()
                .post(ORDER)
                .then().log().all();
    }

    public ValidatableResponse createOrderWithoutToken (Order order) {
        return Client.spec()
                .body(order)
                .when()
                .post(ORDER)
                .then().log().all();
    }
}
