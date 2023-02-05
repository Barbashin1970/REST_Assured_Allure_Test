import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static ru.yandex.praktikum.LoginDataConst.*;
import static ru.yandex.praktikum.StepsRegAndLogin.*;

public class LoginCourierTest extends SetUpLogin {

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Курьер может авторизоваться")
    @Description("При вводе валидного пароля и логина успешный запрос возвращает id курьера.")
    public void loginAndPassCourierReturnId() {
        registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        checkLoginAndPassReturnId(RANDOM_LOGIN, RANDOM_PASS);
        deleteCourier(getValidCourierId(RANDOM_LOGIN, RANDOM_PASS));

    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Курьер не может авторизоваться без пароля")
    @Description("При вводе валидного логина без пароля - ошибка 400.")
    public void loginAndNoPassCourierReturnError() {
        registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        checkNoLoginOrPassReturnError(RANDOM_LOGIN, "");
        deleteCourier(getValidCourierId(RANDOM_LOGIN, RANDOM_PASS));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Курьер не может авторизоваться с пустым полем логин")
    @Description("При вводе валидного пароля с пустым полем логин - ошибка 400.")
    public void passAndNoLoginCourierReturnError() {
        registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        checkNoLoginOrPassReturnError("", RANDOM_PASS);
        deleteCourier(getValidCourierId(RANDOM_LOGIN, RANDOM_PASS));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Курьер не может авторизоваться с неправильным паролем")
    @Description("При вводе неправильного пароля - ошибка 404.")
    public void wrongPassCourierReturnError() {
        registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        checkWrongLoginOrPassReturnError(RANDOM_LOGIN, "1111");
        deleteCourier(getValidCourierId(RANDOM_LOGIN, RANDOM_PASS));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Курьер не может авторизоваться с неправильным логином")
    @Description("Если авторизоваться под несуществующим пользователем - ошибка 404.")
    public void wrongLoginCourierReturnError() {
        registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        checkWrongLoginOrPassReturnError("WrongLogin", RANDOM_PASS);
        deleteCourier(getValidCourierId(RANDOM_LOGIN, RANDOM_PASS));
    }
}