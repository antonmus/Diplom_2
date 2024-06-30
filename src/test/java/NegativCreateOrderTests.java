import config.Ingredients;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.hamcrest.core.Is.is;

public class NegativCreateOrderTests extends TemplateTests{
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
        userSteps
                .createUser(user);

//получаем список ингредиентов и кладем его в конструктор, попутно отлавливаем, если вдруг прилетит ошибка от гета
        ingredients = new Ingredients(ingredientsSteps
                .getIngred()
                .statusCode(200)
                .extract().path("data._id"));
    }
    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderNoAuthTest(){
        orderSteps
                .createOrderNoAuth(ingredients)
                .statusCode(401)
                .body("success", is(false));
        //в требованиях не описано, но кажется здесь должно быть так.
        // Тест падает так как заказ без авторизации создается. Тут надо обсуждать...
    }
    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderNoIngredients(){
        data.preparLogin(user);
        user.setAccessToken(userSteps
                .loginUser(user)
                .extract().body().path("accessToken"));

        orderSteps
                .createOrderAuthNoIngredients(user, ingredients)
                .statusCode(400)
                .body("success", is(false))
                .body("message", is("Ingredient ids must be provided"));
    }
    @Test
    @DisplayName("Создание заказа с кривыми ингредиентами")
    public void createOrderAuthTest(){
        data.preparLogin(user);
        user.setAccessToken(userSteps
                .loginUser(user)
                .extract().body().path("accessToken"));


        ingredients.setIngredients(Stream.of("криво", "косо")
                .collect(Collectors.toList()));


        orderSteps
                .createOrderAuth(user, ingredients)
                .statusCode(500);

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
