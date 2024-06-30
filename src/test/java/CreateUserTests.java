import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
public class CreateUserTests extends TemplateTests{
    private final UserSteps userSteps = new UserSteps();
    private final PrepareData data = new PrepareData();
    User user;
    @Before
    public void SetUp(){
        user = new User();
        user.setUser();
        }
    @Test
    @DisplayName("Возможность создать пользователя")
    public void possibleCreateUserTest(){
        userSteps
                .createUser(user)
                .statusCode(200) //хотя кажется логичнее 201 (аналитик, помоги!)
                .body("success",is(true))
                .body("user.email",is(user.getEmail()))
                .body("user.name",is(user.getName()))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
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
