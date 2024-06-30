import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
public class NegativCreateUserTests extends TemplateTests{
    private final UserSteps userSteps = new UserSteps();
    User user;
    @Before
    public void SetUp(){
        user = new User();
        user.setUser();
    }
    @Test
    @DisplayName("Создание существующего пользователя")
    public void duplicateUser(){
        userSteps
                .createUser(user);
        userSteps
                .createUser(user)
                .statusCode(403)
                .body("message",is("User already exists"))
                .body("success",is (false));
    }
    @Test
    @DisplayName("Создание нового пользователя без имени")
    public void noNameUserTest() {
        user.setName(null);
        userSteps
                .createUser(user)
                .statusCode(403)
                .body("message",is("Email, password and name are required fields"))
                .body("success",is (false));
    }
    @Test
    @DisplayName("Создание нового пользователя без пароля")
    public void noPasswordUserTest() {
        user.setPassword(null);
        userSteps
                .createUser(user)
                .statusCode(403)
                .body("message",is("Email, password and name are required fields"))
                .body("success",is (false));
    }
    @Test
    @DisplayName("Создание нового пользователя без почты")
    public void noEmailUserTest() {
        user.setEmail(null);
        userSteps
                .createUser(user)
                .statusCode(403)
                .body("message",is("Email, password and name are required fields"))
                .body("success",is (false));
    }
}
