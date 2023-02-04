import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;

import org.junit.Test;

import static ru.yandex.praktikum.LoginDataConst.*;
import static ru.yandex.praktikum.StepsRegAndLogin.*;

public class CreateCourierTest extends SetUpLogin {

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Параметр Имя Курьера - необязательный")
    @Description("Курьера можно создать без имени - создается аккаунт")
    public void createCourierNoNameReturn_201() {
        Response responseCourier = registrationValidData(RANDOM_LOGIN, RANDOM_PASS, "");
        checkAnswerThenValidRegistration(responseCourier);
        deleteCourier(getValidCourier_id(RANDOM_LOGIN, RANDOM_PASS));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание курьера")
    @Description("Курьера можно создать - по валидному паролю и логину создается аккаунт курьера")
    public void createCourierReturn_201() {
        Response responseCourier = registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        checkAnswerThenValidRegistration(responseCourier);
        deleteCourier(getValidCourier_id(RANDOM_LOGIN, RANDOM_PASS));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("Курьера нельзя создать повторно с теми же логином и паролем")
    public void createTheSameCourierAgainReturn_409() {
        registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        Response responseCourierAgain = registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        checkAnswerThenAgainRegistration(responseCourierAgain);
        deleteCourier(getValidCourier_id(RANDOM_LOGIN, RANDOM_PASS));
    }


    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание курьера без логина невозможно")
    @Description("Курьера нельзя создать по валидному паролю и без логина")
    public void createCourierWithoutLoginReturn_400() {
        Response responseCourier = registrationValidData("", RANDOM_PASS, RANDOM_NAME);
        checkAnswerThenRegistrationDataNotEnough(responseCourier);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание курьера без пароля невозможно")
    @Description("Курьера нельзя создать по валидному логину и без пароля")
    public void createCourierWithoutPasswordReturn_400() {
        Response responseCourier = registrationValidData(RANDOM_LOGIN, "", RANDOM_NAME);
        checkAnswerThenRegistrationDataNotEnough(responseCourier);
    }

}