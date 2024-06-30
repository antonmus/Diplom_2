import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class NegativLoginUserTests extends TemplateTests{
    private final UserSteps userSteps = new UserSteps();
    private final PrepareData data = new PrepareData();
    User user;
    String testData;
    Integer codError;
    String message;

    public NegativLoginUserTests(String testData, Integer codError, String message ) {
        this.testData = testData;
        this.codError = codError;
        this.message = message;
    }
    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {"null", 401, "email or password are incorrect"},
                {null, 401, "email or password are incorrect"}
        };
    }
    @Before
    public void setUp () {
                user = new User();
                user.setUser();
                userSteps.createUser(user);
                data.saveDataUser(user);
                user.setName(null);
            }
   //судя по требованиям, Name здесь не участвует, потому ломаем только email и password
    @Test
    @DisplayName("Авторизация с кривым паролем")
    public void badPassUserTest()  {
        user.setPassword(testData);
        userSteps.loginUser(user)
                .statusCode(codError)
                .body("message", is(message));
    }
    @Test
    @DisplayName("Авторизация с кривой почтой")
    public void badEmailUserTest()  {
        user.setEmail(testData);
        userSteps.loginUser(user)
                .statusCode(codError)
                .body("message", is(message));
    }
    @After
    public void tearDown  (){
        user.setPassword(data.getTempPass());
        user.setEmail(data.getTempEmail());
        user.setAccessToken(userSteps
               .loginUser(user)
               .extract().body().path("accessToken"));
        userSteps
               .deleteUser(user);
   }
}
