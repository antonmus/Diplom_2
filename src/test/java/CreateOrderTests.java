import config.Ingredients;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateOrderTests extends TemplateTests {
    private final UserSteps userSteps = new UserSteps();
    private final OrderSteps orderSteps = new OrderSteps();
    private final IngredientsSteps ingredientsSteps = new IngredientsSteps();
    private final PrepareData data = new PrepareData();
    User user;
    Ingredients ingredients;
    @Before
    public void setUp(){
        user = new User();
        user.setUser();

//получаем список ингредиентов и кладем его в конструктор, попутно отлавливаем, если вдруг прилетит ошибка от гета
        ingredients = new Ingredients(ingredientsSteps
                .getIngred()
                .statusCode(200)
                .extract().path("data._id"));
    }
    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void createOrderAuthTest(){
        userSteps
                .createUser(user);

        data.preparLogin(user);
        user.setAccessToken(userSteps
                .loginUser(user)
                .extract().body().path("accessToken"));

        orderSteps
                .createOrderAuth(user, ingredients)
                .statusCode(200)
                .body("success", is(true))
                .body("name", notNullValue())
                .body("order.number", notNullValue());
    }
    @After
    public void tearDown(){
        userSteps
                .deleteUser(user);
    }
}
