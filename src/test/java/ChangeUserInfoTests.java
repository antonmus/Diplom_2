import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class ChangeUserInfoTests extends TemplateTests{
    private final UserSteps userSteps = new UserSteps();
    private final PrepareData data = new PrepareData();
    private User user;

    @Before
    public void setUp(){
        user = new User();
        user.setUser();
        userSteps.createUser(user);
        data.saveDataUser(user);

    }
    @Test
    @DisplayName("Замена имени с авторизацией")
    public void changeNameWithAuth(){
        data.preparLogin(user);
        user.setAccessToken(userSteps
                .loginUser(user)
                .extract().body().path("accessToken"));
        user.setName("New name");
        userSteps
                .updateUserAuth(user)
                .statusCode(200)
                .body("success", is(true))
                .body("user.name", is(user.getName()));
    }
    @Test
    @DisplayName("Замена почты с авторизацией")
    public void changeEmailWithAuth(){
        data.preparLogin(user);
        user.setAccessToken(userSteps
                .loginUser(user)
                .extract().body().path("accessToken"));
        data.rePreparLogin(user);
        user.setEmail("new"+user.getEmail());
        userSteps
                .updateUserAuth(user)
                .statusCode(200)
                .body("success", is(true))
                .body("user.email", is(user.getEmail()));
    }
    @Test
    @DisplayName("Замена пароля с авторизацией")
    public void changePasswordWithAuth(){
        data.preparLogin(user);
        user.setAccessToken(userSteps
                .loginUser(user)
                .extract().body().path("accessToken"));
        data.rePreparLogin(user);
        user.setPassword("newpassword");
        userSteps
                .updateUserAuth(user)
                .statusCode(200)
                .body("success", is(true));
        data.preparLogin(user);
        userSteps
                .loginUser(user)
                .statusCode(200)
                .body("success", CoreMatchers.is(true))
                .body("user.email", CoreMatchers.is(user.getEmail()))
                .body("user.name", CoreMatchers.is(data.getTempName()))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());

    }
    @Test
    @DisplayName("Замена имени без авторизации")
    public void changeNameNoAuth(){
        user.setName("New name");
        userSteps
                .updateUserNoAuth(user)
                .statusCode(401)
                .body("success", is(false))
                .body("message", is("You should be authorised"));
    }
    @Test
    @DisplayName("Замена почты без авторизации")
    public void changeEmailNoAuth(){
        user.setEmail("new"+user.getEmail());
        userSteps
                .updateUserNoAuth(user)
                .statusCode(401)
                .body("success", is(false))
                .body("message", is("You should be authorised"));
        user.setEmail(data.getTempEmail());
    }
    @Test
    @DisplayName("Замена пароля без авторизации")
    public void changePasswordNoAuth() {
        user.setPassword("newpassword");
        userSteps
                .updateUserNoAuth(user)
                .statusCode(401)
                .body("success", is(false))
                .body("message", is("You should be authorised"));
        data.preparLogin(user);
        userSteps
                .loginUser(user)
                .statusCode(401)
                .body("success", is(false))
                .body("message", is("email or password are incorrect"));
    }
    @Test
    @DisplayName("Замена почты пользователя на почту, уже используемую другим")
    public void changeUseEmail(){
        //создаем второго пользователя
        User user1 = new User();
        user1.setUser();
        userSteps.createUser(user1);
        //получаем токен по первому
        data.preparLogin(user);
        user.setAccessToken(userSteps
                .loginUser(user)
                .extract().body().path("accessToken"));
        data.rePreparLogin(user);
        //подменяем почту первого на почту второго
        user.setEmail(user1.getEmail());
        //шлем патч на изменение почты
        userSteps
                .updateUserAuth(user)
                .statusCode(403)
                .body("success", is(false))
                .body("message", is("User with such email already exists"));
        user.setEmail(data.getTempEmail());
        //удаляем второго пользователя
        data.preparLogin(user1);
        user1.setAccessToken(userSteps
                .loginUser(user1)
                .extract().body().path("accessToken"));
        userSteps
                .deleteUser(user1);
    }
    public void tearDown(){
        data.preparLogin(user);
        user.setAccessToken(userSteps
                .loginUser(user)
                .extract().body().path("accessToken"));
        userSteps
                .deleteUser(user);
    }
}
