import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static config.UserEndPoint.*;
import static config.UserEndPoint.UPDATE;
import static io.restassured.RestAssured.given;

public class UserSteps {
    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user) {
        return given()
                .body(user)
                .when().post(CREATE)
                .then();
    }
    @Step("Логин пользователя")
    public ValidatableResponse loginUser (User user)  {
        return given()
                .body(user)
                .when().post(LOGIN)
                .then();
    }
    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(User user){
        return given()
                .header("Authorization", user.getAccessToken())
                .body(user)
                .when().delete(DELETE)
                .then();
    }
    @Step("Обновление пользователя с токеном")
    public ValidatableResponse updateUserAuth(User user){
        return given()
                .header("Authorization", user.getAccessToken())
                .body(user)
                .when().patch(UPDATE)
                .then();
    }
    @Step("Обновление пользователя без токена")
    public ValidatableResponse updateUserNoAuth(User user){
        return given()
                .body(user)
                .when().patch(UPDATE)
                .then();
    }
}