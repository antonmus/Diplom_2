import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static config.IngredientsEndPoint.GET;
import static io.restassured.RestAssured.given;

public class IngredientsSteps {
    @Step("Запрос списка ингридиентов")
    public ValidatableResponse getIngred(){
        return given()
                .when().get(GET)
                .then();
    }
}
