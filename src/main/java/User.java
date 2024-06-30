import org.apache.commons.lang3.RandomStringUtils;

public class User {
    private String name;
    private String password;
    private String email;
    private String accessToken;
    public void setUser(){
        this.name = RandomStringUtils.randomAlphabetic(10);
        this.password = RandomStringUtils.randomAlphabetic(10).toLowerCase();
        this.email = RandomStringUtils.randomAlphabetic(10).toLowerCase()+ "@yandex.ru";
        //судя по скудной аналитике, email и password должны быть в нижнем регистре. надо уточнить у аналитика.
        // В целом, можно поэксперементировать с валидацией
    }

    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public User() {
    }
}
