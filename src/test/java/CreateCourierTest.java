import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.CourierId;
import ru.yandex.praktikum.CourierLogin;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.yandex.praktikum.LoginDataConst.*;


public class CreateCourierTest {


    int statusCode;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        RANDOM_LOGIN = RandomStringUtils.randomAlphabetic(8);
        RANDOM_PASS = RandomStringUtils.randomNumeric(4);
        RANDOM_NAME = RandomStringUtils.randomAlphabetic(8);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Параметр Имя Курьера - необязательный")
    @Description("Курьера можно создать без имени - создается аккаунт")
    public void createCourierNoNameReturn_201() {
        Response responseCourier = registrationValidData(RANDOM_LOGIN, RANDOM_PASS, "");
        checkAnswerThenValidRegistration(responseCourier);
        if (statusCode == 201) {
            int courierId = getValidCourier_id(RANDOM_LOGIN, RANDOM_PASS);
            deleteCourier(courierId);
        }
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание курьера")
    @Description("Курьера можно создать - по валидному паролю и логину создается аккаунт курьера")
    public void createCourierReturn_201() {
        Response responseCourier = registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        checkAnswerThenValidRegistration(responseCourier);
        if (statusCode == 201) {
            int courierId = getValidCourier_id(RANDOM_LOGIN, RANDOM_PASS);
            deleteCourier(courierId);
        }
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("Курьера нельзя создать повторно с теми же логином и паролем")
    public void createTheSameCourierAgainReturn_409() {
        registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        Response responseCourierAgain = registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        checkAnswerThenAgainRegistration(responseCourierAgain);
        if (statusCode == 201) {
            int courierId = getValidCourier_id(RANDOM_LOGIN, RANDOM_PASS);
            deleteCourier(courierId);
        }
    }


    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание курьера без логина невозможно")
    @Description("Курьера нельзя создать по валидному паролю и без логина")
    public void createCourierWithoutLoginReturn_400() {
        Response responseCourier = registrationValidData("", RANDOM_PASS, RANDOM_NAME);
        checkAnswerThenRegistrationDataNotEnough(responseCourier);
        if (statusCode == 201) {
            int courierId = getValidCourier_id("", RANDOM_PASS);
            deleteCourier(courierId);
        }
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание курьера без пароля невозможно")
    @Description("Курьера нельзя создать по валидному логину и без пароля")
    public void createCourierWithoutPasswordReturn_400() {
        Response responseCourier = registrationValidData(RANDOM_LOGIN, "", RANDOM_NAME);
        checkAnswerThenRegistrationDataNotEnough(responseCourier);
        if (statusCode == 201) {
            int courierId = getValidCourier_id(RANDOM_LOGIN, "");
            deleteCourier(courierId);
        }
    }

    @Step("Регистрация нового курьера с валидными данными")
    public Response registrationValidData(String login, String pass, String name) {
        CourierLogin courierLogin = new CourierLogin(login, pass, name);
        Response responseCourier =
                given()
                        .header("Content-type", "application/json")
                        .filters(new AllureRestAssured(), new ResponseLoggingFilter())
                        .and()
                        .body(courierLogin)
                        .when()
                        .post("/api/v1/courier");
        System.out.println(responseCourier.body().asString());
        return responseCourier;
    }

    @Step("Проверка тела - (ok: true) и статуса ответа сервера при первой валидной регистрации - 201")
    public void checkAnswerThenValidRegistration(Response responseCourier) {
        responseCourier.then()
                .body("ok", equalTo(true))
                .and()
                .statusCode(201);
        System.out.println(responseCourier.body().asString());
    }

    @Step("Проверка тела и статуса ответа сервера при повторной регистрации - 409")
    public void checkAnswerThenAgainRegistration(Response responseCourier) {
        responseCourier.then()
                .body("code", equalTo(409))
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
        System.out.println(responseCourier.body().asString());
    }

    @Step("Проверка тела и статуса ответа сервера при неполных данных - 400")
    public void checkAnswerThenRegistrationDataNotEnough(Response responseCourier) {
        responseCourier.then()
                .body("code", equalTo(400))
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
        System.out.println(responseCourier.body().asString());
    }

    @Step("Запрос номера id курьера по его логину и паролю")
    public int getValidCourier_id(String login, String pass) {
        CourierId courierId = new CourierId(login, pass);
        Response responseCourierLogin =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierId)
                        .when()
                        .post("/api/v1/courier/login"); // запрос на получение id
        JsonPath jsnPath = responseCourierLogin.jsonPath();
        System.out.println(responseCourierLogin.body().asString());
        statusCode = responseCourierLogin.getStatusCode();// запоминаем статус-код чтобы почистить базу
        return jsnPath.get("id");
    }

    @Step("Удаление курьера по его номеру id")
    public void deleteCourier(int courierId) {
        Response responseDelete = given()
                .header("Content-type", "application/json")
                .when()
                .delete(String.format("/api/v1/courier/%s", courierId)); // отправляем запрос с id на удаление курьера
        System.out.println("Courier id = " + courierId);
        responseDelete.then()
                .statusCode(200);
        System.out.println(responseDelete.body().asString());
    }

}
