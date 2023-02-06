package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class StepsOrder {

    @Step("Получаем track-номер заказа из тела ответа.")
    public static int getOrderTrack(File json) {
        Response responseOrder = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/orders");
        return responseOrder.jsonPath().get("track");
    }

    @Step("Тело ответа содержит не пустой track.")
    public static void orderTrackNotNull(File json) {
        Response responseOrder = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/orders");
        responseOrder.then().body("track", notNullValue());
    }

    @Step("Получение информации о заказе по номеру заказа.")
    public static void orderInfo(int orderTrack) {
        Response responseOrderInfo = given()
                .header("Content-type", "application/json")
                .when()
                .queryParam("t", orderTrack)
                .get("/api/v1/orders/track");
        responseOrderInfo.then()
                .body("order", notNullValue())
                .and()
                .statusCode(200);
    }

    //@Step("Отмена заказа по номеру track.")
    // шаг отмены заказа  пока закомментирован в тестах - не работает ручка put
    //
    // public static void orderCancel(int track) {
    //    OrderTrack orderTrack = new OrderTrack(track);
    //     Response responseOrder = given()
    //             .header("Content-type", "application/json")
    //           .and()
    //           .body(orderTrack)
    //            .when()
    //            .put("/api/v1/orders/cancel"); // не работает корректно
    //    responseOrder.then()
    //            .body("ok", equalTo(true)) // на валидный трек присылает 400
    //            .and()
    //            .statusCode(200);
    // }

}