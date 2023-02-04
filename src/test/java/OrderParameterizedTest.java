import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;

import static ru.yandex.praktikum.ColourConst.*;
import static ru.yandex.praktikum.StepsOrder.*;

@RunWith(Parameterized.class)
public class OrderParameterizedTest extends SetUpOrder {
    private final File colour;

    public OrderParameterizedTest(File colour) {
        this.colour = colour;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {BLACK},
                {GREY},
                {GREY_AND_BLACK},
                {NO_COLOR},
        };
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Тело ответа содержит track не пустой")
    @Description("Проверяем цвета - черный - серый - черный/серый - без указания цвета")
    public void checkOrderTrackNotNull() {
        orderTrackNotNull(colour);
        //orderCancel(getOrderTrack(colour));
        // пока невозможно отменить заказ - баг в API - не работает ручка Отменить заказ
        // put/api/v1/orders/cancel
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Можно указать в заказе любой цвет самоката")
    @Description("Проверяем цвета - черный - серый - черный/серый - без указания цвета")
    public void checkOrderCreationAnyColours() {
        orderInfo(getOrderTrack(colour));
        // orderCancel(getOrderTrack(colour));
    }
}