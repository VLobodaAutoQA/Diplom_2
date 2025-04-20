package praktikum;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class IngredientsClient extends Client {

    public static final String INGREDIENTS = "/ingredients";

    public Response getIngredients() {
        return spec()
                .accept(ContentType.JSON)
                .get(INGREDIENTS);
    }
}