import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;


public class ListOfOrdersTest extends SetUpLogin {

    @Step("Получение списка заказов без отправки параметров.")
    public static void orderListInfoNotNull() {
        Response responseOrderInfo = given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders");
        responseOrderInfo.then()
                .body("orders", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("В тело ответа возвращается список заказов")
    @Description("Список всех заказов системы как файл json")
    public void createCourierAndOrderAndGetList() {
        orderListInfoNotNull();
    }
}
