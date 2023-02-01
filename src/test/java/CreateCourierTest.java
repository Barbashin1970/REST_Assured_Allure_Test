import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

import static org.hamcrest.CoreMatchers.equalTo;

    public class CreateCourierTest {
        int statusCode;
        @Before
        public void setUp() {    RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";  }

        @Test
        @Severity(SeverityLevel.NORMAL)
        @DisplayName("Проверка создания курьера")
        @Description("Проверяем что по валидному паролю и логину создается аккаунт курьера")
        public void createNewCourier(){
            String json = "{\"login\": \"SaMuRaI4\", \"password\": \"1234\"}";
            Response responseCourier =
                    given()
                            .header("Content-type", "application/json")
                            .and()
                            .body(json)
                            .when()
                            .post("/api/v1/courier");
            responseCourier.then()
                    .body("ok", equalTo(true))
                    .and()
                    .statusCode(201);

            statusCode = responseCourier.getStatusCode(); // запоминаем статус-код чтобы почистить базу

        }

        @After
        public void TearDown() {        // Чистим базу - удаляем аккаунт курьера
            if (statusCode == 201) {    // Надо получить id курьера и удалить аккаунт с таким id
                String jsonLogin = "{\"login\": \"SaMuRaI4\", \"password\": \"1234\"}";
                Response responseCourierLogin =
                        given()
                                .header("Content-type", "application/json")
                                .and()
                                .body(jsonLogin)
                                .when()
                                .post("/api/v1/courier/login"); // запрос на получение id
                JsonPath jsnPath = responseCourierLogin.jsonPath();
                int courierId = jsnPath.get("id"); // извлекаем из ответа id
                Response responseDelete = given()
                        .header("Content-type", "application/json")
                        .when()
                        .delete(String.format("/api/v1/courier/%s", courierId)); // отправляем запрос с id на удаление курьера

                responseDelete.then()
                        .statusCode(200); // проверяем что курьер удален
            }
        }
}
