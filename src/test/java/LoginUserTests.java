import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
public class LoginUserTests extends TemplateTests{
    private final UserSteps userSteps = new UserSteps();
    private final PrepareData data = new PrepareData();
    User user;

    @Before
    public void setUp(){
        user = new User();
        user.setUser();
        userSteps.
                createUser(user);
        data.preparLogin(user);
    }
    @Test
    @DisplayName("Возможность авторизоваться созданным пользователем")
    public void loginUserTest(){
        userSteps
                .loginUser(user)
                .statusCode(200)
                .body("success",is(true))
                .body("user.email",is(user.getEmail()))
                .body("user.name",is(data.getTempName()))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }
    @After
    public void tearDown(){
        user.setAccessToken(userSteps
                .loginUser(user)
                .extract().body().path("accessToken"));
        userSteps
                .deleteUser(user);
    }
}
