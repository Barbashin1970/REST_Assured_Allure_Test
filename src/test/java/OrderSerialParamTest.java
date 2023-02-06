import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.Order;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.yandex.praktikum.ColourConst.*;

@RunWith(Parameterized.class)
public class OrderSerialParamTest {

    private final Order colour;

    public OrderSerialParamTest(Order colour) {
        this.colour = colour;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {ORDER_BLACK},
                {ORDER_GREY},
                {ORDER_GREY_BLACK},
                {ORDER_NO_COLOR},
        };
    }

    @Step("Создание заказа на самокат - возвращает трек-номер заказа")
    public static int getOrderTrackNumber(Order colour) {
        Response responseOrder = given()
                .header("Content-type", "application/json")
                .and()
                .body(colour)
                .when()
                .post("/api/v1/orders");
        return responseOrder.jsonPath().get("track");
    }

    @Step("Тело ответа содержит не пустой track.")
    public static void orderTrackNotNullNew(Order colour) {
        Response responseOrder = given()
                .header("Content-type", "application/json")
                .and()
                .body(colour)
                .when()
                .post("/api/v1/orders");
        responseOrder.then().body("track", notNullValue());
    }

    @Step("Получение информации о заказе по номеру заказа.")
    public static void checkOrderInfo(int track) {
        Response responseOrderInfo = given()
                .header("Content-type", "application/json")
                .when()
                .queryParam("t", track)
                .get("/api/v1/orders/track");
        responseOrderInfo.then()
                .body("order", notNullValue())
                .and()
                .statusCode(200);
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";

    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Тело ответа содержит track не пустой")
    @Description("Проверяем цвета - черный - серый - черный/серый - без указания цвета")
    public void checkNotNull() {
        orderTrackNotNullNew(colour);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Можно указать в заказе любой цвет самоката")
    @Description("Проверяем цвета - черный - серый - черный/серый - без указания цвета")
    public void checkAnyColour() {
        checkOrderInfo(getOrderTrackNumber(colour));
    }

    // @Step
    // шаг отмены заказа  пока закомментирован в тестах - не работает ручка put
    // "Отмена заказа по номеру track."
    //  public static void orderCancelNew(int track) {
    //    OrderTrack orderTrack = new OrderTrack(track);
    //    Response responseOrder = given()
    //            .header("Content-type", "application/json")
    //            .and()
    //            .body(orderTrack)
    //            .when()
    //            .put("/api/v1/orders/cancel");
    //    System.out.println(responseOrder.body().asString());
    //    responseOrder.then()
    //            .body("ok", equalTo(true))
    //            .and()
    //            .statusCode(200);
    //  }
    //  int track;
    //  @After
    //  public void tearDown() {
    //  orderCancelNew(track);
    //
    //    }
}