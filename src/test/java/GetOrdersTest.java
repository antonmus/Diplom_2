import config.Ingredients;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class GetOrdersTest extends TemplateTests{
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
//создаем пользователя
        userSteps
                .createUser(user);
//получаем токен
        data.preparLogin(user);
        user.setAccessToken(userSteps
                .loginUser(user)
                .extract().body().path("accessToken"));
//создаем заказ
        orderSteps
                .createOrderAuth(user, ingredients)
                .statusCode(200);
    }
    @Test
    @DisplayName("Получение заказов пользователя с авторизацией")
    public void getOrderTestAuth(){
        orderSteps
                .getOrdersAuth(user)
                .statusCode(200)
                .body("success", is(true))
                .body("orders", notNullValue())
                .body("total", notNullValue())
                .body("totalToday", notNullValue());
        //можно было проверить еще разное количество заказов, но по заданию вроде не надо, поэтому не стал,
        // а так циклом заказов насоздавать и количество сверить
    }

    @Test
    @DisplayName("Получение заказов пользователя без авторизации")
    public void getOrderTestNoAuth(){
        orderSteps
                .getOrdersNoAuth()
                .statusCode(401)
                .body("success", is(false))
                .body("message", is("You should be authorised"));
    }
    @After
    public void tearDown(){
        data.preparLogin(user);
        user.setAccessToken(userSteps
                .loginUser(user)
                .extract().body().path("accessToken"));
        userSteps
                .deleteUser(user);
    }
}
