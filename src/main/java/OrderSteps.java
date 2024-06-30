import config.Ingredients;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static config.OrderEndPoint.CREATE_ORDER;
import static config.OrderEndPoint.GET_ORDER;
import static io.restassured.RestAssured.given;

public class OrderSteps {
    @Step("Создание заказа с авторизацией")
    public ValidatableResponse createOrderAuth(User user, Ingredients ingredients) {
        return given()
                .header("Authorization", user.getAccessToken())
                .body(ingredients.getPrepareIngredients())
                .when().post(CREATE_ORDER)
                .then();
    }
    @Step("Создание заказа без авторизации")
    public ValidatableResponse createOrderNoAuth(Ingredients ingredients) {
        return given()
                .body(ingredients.getPrepareIngredients())
                .when().post(CREATE_ORDER)
                .then();
    }
    @Step("Создание заказа с авторизацией без ингредиентов")
    public ValidatableResponse createOrderAuthNoIngredients(User user, Ingredients ingredients) {
        return given()
                .header("Authorization", user.getAccessToken())
                .body(ingredients.getIngredients())
                .when().post(CREATE_ORDER)
                .then();
    }
    @Step("Получение заказов с авторизацией")
    public ValidatableResponse getOrdersAuth(User user) {
        return given()
                .header("Authorization", user.getAccessToken())
                .when().get(GET_ORDER)
                .then();
    }
    @Step("Получение заказов без авторизации")
    public ValidatableResponse getOrdersNoAuth() {
        return given()
                .when().get(GET_ORDER)
                .then();
    }
}
