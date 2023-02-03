import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.Steps;

import static ru.yandex.praktikum.LoginDataConst.*;


public class CreateCourierTest extends Steps {


    // int statusCode;
    int courierId;

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
        courierId = getValidCourier_id(RANDOM_LOGIN, RANDOM_PASS);
        deleteCourier(courierId);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание курьера")
    @Description("Курьера можно создать - по валидному паролю и логину создается аккаунт курьера")
    public void createCourierReturn_201() {
        Response responseCourier = registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        checkAnswerThenValidRegistration(responseCourier);
        courierId = getValidCourier_id(RANDOM_LOGIN, RANDOM_PASS);
        deleteCourier(courierId);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("Курьера нельзя создать повторно с теми же логином и паролем")
    public void createTheSameCourierAgainReturn_409() {
        registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        Response responseCourierAgain = registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        checkAnswerThenAgainRegistration(responseCourierAgain);
        courierId = getValidCourier_id(RANDOM_LOGIN, RANDOM_PASS);
        deleteCourier(courierId);
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
