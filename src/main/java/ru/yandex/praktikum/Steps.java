package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class Steps {

        // ----------------- ручка -------- POST --------- COURIER --------------
    @Step("Регистрация нового курьера с валидными данными")
    public static Response registrationValidData(String login, String pass, String name) {
        CourierLogin courierLogin = new CourierLogin(login, pass, name);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .when()
                .post("/api/v1/courier");
    }


    @Step("Проверка тела - (ok: true) и статуса ответа сервера при первой валидной регистрации - 201")
    public static void checkAnswerThenValidRegistration(Response responseCourier) {
        responseCourier.then()
                .body("ok", equalTo(true))
                .and()
                .statusCode(201);
        //  System.out.println(responseCourier.body().asString());
    }

    @Step("Проверка тела и статуса ответа сервера при повторной регистрации - 409")
    public static void checkAnswerThenAgainRegistration(Response responseCourier) {
        responseCourier.then()
                .body("code", equalTo(409))
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
        // System.out.println(responseCourier.body().asString());
    }

    @Step("Проверка тела и статуса ответа сервера при неполных данных - 400")
    public static void checkAnswerThenRegistrationDataNotEnough(Response responseCourier) {
        responseCourier.then()
                .body("code", equalTo(400))
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
        // System.out.println(responseCourier.body().asString());
    }
    // ------------------ ручка ------POST ---- LOGIN ---------------
    @Step("Запрос номера id курьера по его логину и паролю")
    public static int getValidCourier_id(String login, String pass) {
        CourierId courierId = new CourierId(login, pass);
        Response responseCourierLogin =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierId)
                        .when()
                        .post("/api/v1/courier/login"); // запрос на получение id
        JsonPath jsnPath = responseCourierLogin.jsonPath();
        return jsnPath.get("id");
    }
    @Step("Проверка наличия номера id курьера по его логину и паролю")
    public static void checkLoginAndPassReturnId(String login, String pass) {
        CourierId courierId = new CourierId(login, pass);
          Response responseLogin =  given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courierId)
                        .when()
                        .post("/api/v1/courier/login");
          responseLogin.then().body("id", notNullValue()); // запрос на получение id не пустой
    }

    @Step("Запрос номера id курьера по его логину и паролю")
    public static void checkNoLoginOrPassReturn_400(String login, String pass) {
        CourierId courierId = new CourierId(login, pass);
        Response responseLogin =  given()
                .header("Content-type", "application/json")
                .and()
                .body(courierId)
                .when()
                .post("/api/v1/courier/login");
        responseLogin.then()
                .body("code", equalTo(400))
                .and()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400); // запрос на получение id не пустой
    }

   @Step("Cистема вернёт ошибку, если неправильно указать логин или пароль")
   public static void checkWrongLoginOrPassReturn_404(String login, String pass) {
       CourierId courierId = new CourierId(login, pass);
       Response responseLogin =  given()
               .header("Content-type", "application/json")
               .and()
               .body(courierId)
               .when()
               .post("/api/v1/courier/login");
       responseLogin.then()
               .body("code", equalTo(404))
               .and()
               .body("message", equalTo("Учетная запись не найдена"))
               .and()
               .statusCode(404); // запрос на получение id не пустой
   }



    // ------------------- ручка -------- DELETE ------------
    @Step("Удаление курьера по его номеру - статус 200 и ответ ok: true.")
    public static void deleteCourier(int courierId) {
        Response responseDelete = given()
                .header("Content-type", "application/json")
                .when()
                .delete(String.format("/api/v1/courier/%s", courierId)); // отправляем запрос с id на удаление курьера
        //  System.out.println("Courier id = " + courierId);
        responseDelete.then()
                .body("ok", equalTo(true))
                .and()
                .statusCode(200);
        //System.out.println(responseDelete.body().asString());
    }


}
