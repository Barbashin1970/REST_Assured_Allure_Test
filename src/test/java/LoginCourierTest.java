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
        deleteCourier(getValidCourier_id(RANDOM_LOGIN, RANDOM_PASS));

    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Курьер не может авторизоваться без пароля")
    @Description("При вводе валидного логина без пароля - ошибка 400.")
    public void loginAndNoPassCourierReturn_400() {
        registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        checkNoLoginOrPassReturn_400(RANDOM_LOGIN, "");
        deleteCourier(getValidCourier_id(RANDOM_LOGIN, RANDOM_PASS));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Курьер не может авторизоваться с пустым полем логин")
    @Description("При вводе валидного пароля с пустым полем логин - ошибка 400.")
    public void passAndNoLoginCourierReturn_400() {
        registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        checkNoLoginOrPassReturn_400("", RANDOM_PASS);
        deleteCourier(getValidCourier_id(RANDOM_LOGIN, RANDOM_PASS));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Курьер не может авторизоваться с неправильным паролем")
    @Description("При вводе неправильного пароля - ошибка 404.")
    public void wrongPassCourierReturn_404() {
        registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        checkWrongLoginOrPassReturn_404(RANDOM_LOGIN, "1111");
        deleteCourier(getValidCourier_id(RANDOM_LOGIN, RANDOM_PASS));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Курьер не может авторизоваться с неправильным логином")
    @Description("Если авторизоваться под несуществующим пользователем - ошибка 404.")
    public void wrongLoginCourierReturn_404() {
        registrationValidData(RANDOM_LOGIN, RANDOM_PASS, RANDOM_NAME);
        checkWrongLoginOrPassReturn_404("WrongLogin", RANDOM_PASS);
        deleteCourier(getValidCourier_id(RANDOM_LOGIN, RANDOM_PASS));
    }
}