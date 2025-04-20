package praktikum;

import io.restassured.response.ValidatableResponse;

public class OrderClient extends Client {
    public static final String ORDER = "/orders";

    private String accessToken;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public ValidatableResponse createOrder (Order order) {
        return spec()
                .header("Authorization", this.accessToken)
                .body(order)
                .when()
                .post(ORDER)
                .then().log().all();
    }

    public ValidatableResponse createOrderWithoutToken (Order order) {
        return spec()
                .body(order)
                .when()
                .post(ORDER)
                .then().log().all();
    }

    public ValidatableResponse getOrders() {
        return spec()
                .header("Authorization", this.accessToken)
                .when()
                .get(ORDER)
                .then().log().all();
    }

    public ValidatableResponse getOrdersWithOutToken() {
        return spec()
                .get(ORDER)
                .then().log().all();
    }
}
