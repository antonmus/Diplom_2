import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.Before;
import static config.TotalConfig.HOST;
public class TemplateTests {
    @Before
    public void setUpTests(){
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(HOST)
                .setContentType(ContentType.JSON)
                .build();
    }
}
