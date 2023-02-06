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
    @Description("Курьера можно создать без имени - создается аккаунт - 201")
    public void createCourierNoNameReturnOk() {
        Response responseCourier = registrationValidData(RANDOM_LOGIN, RANDOM_PASS, "");
        checkAnswerThenValidRegistration(responseCourier);
        deleteCourier(getValidCourierId(RANDOM_LOGIN, RANDOM_PASS));
    }


    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание курьера")
    @Description("По валидному паролю и логину создается аккаунт курьера - 201")
    public void createCourierReturnOk() {
        Response responseCourier = registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        checkAnswerThenValidRegistration(responseCourier);
        deleteCourier(getValidCourierId(RANDOM_LOGIN, RANDOM_PASS));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("Курьера нельзя создать повторно с теми же логином и паролем - 409")
    public void createTheSameCourierAgainReturnError() {
        registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        Response responseCourierAgain = registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        checkAnswerThenAgainRegistration(responseCourierAgain);
        deleteCourier(getValidCourierId(RANDOM_LOGIN, RANDOM_PASS));
    }


    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание курьера без логина невозможно")
    @Description("Курьера нельзя создать по валидному паролю и без логина - 400")
    public void createCourierWithoutLoginReturnError() {
        Response responseCourier = registrationValidData("", RANDOM_PASS, RANDOM_NAME);
        checkAnswerThenRegistrationDataNotEnough(responseCourier);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание курьера без пароля невозможно")
    @Description("Курьера нельзя создать по валидному логину и без пароля - 400")
    public void createCourierWithoutPasswordReturnError() {
        Response responseCourier = registrationValidData(RANDOM_LOGIN, "", RANDOM_NAME);
        checkAnswerThenRegistrationDataNotEnough(responseCourier);
    }

}