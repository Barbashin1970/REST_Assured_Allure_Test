import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

import static org.hamcrest.CoreMatchers.equalTo;

    public class CreateCourierTest {
        @Before
        public void setUp() {    RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";  }

        @Test
        public void createNewCourier(){
            File json = new File("src/test/resources/newCourier.json");
            Response responseCourier =
                    given()
                            .header("Content-type", "application/json")
                            .and()
                            .body(json)
                            .when()
                            .post("/api/v1/courier");
            responseCourier.then().assertThat()
                    .body("ok", equalTo(true))
                    .and()
                    .statusCode(201);}

        @Test
        public void deleteNewCourier(){
            // получить номер курьера
            String jsonLogin = "{\"login\": \"SaMuRaI4\", \"password\": \"1234\"}";
            Response responseCourierLogin =
                    given()
                            .header("Content-type", "application/json")
                            .and()
                            .body(jsonLogin)
                            .when()
                            .post("/api/v1/courier/login");
            JsonPath jsnPath = responseCourierLogin.jsonPath();
             int courierId = jsnPath.get("id");
             Response responseDelete = given()
                     .header("Content-type", "application/json")
                     .when()
                     .delete(String.format("/api/v1/courier/%s", courierId));

             responseDelete.then().statusCode(200);
        }
}
