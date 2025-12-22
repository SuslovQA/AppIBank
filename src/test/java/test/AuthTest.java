package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static data.DataGenerator.Registration.getRegisteredUser;
import static data.DataGenerator.Registration.getUser;
import static data.DataGenerator.getRandomLogin;
import static data.DataGenerator.getRandomPassword;


class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }


    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
        //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
        //  пользователя registeredUser
        $x("//input[@name='login']").setValue(registeredUser.getLogin());
        $x("//input[@name='password']").setValue(registeredUser.getPassword());
        $x("//button[@data-test-id='action-login']").click();
        $x("//h2").shouldHave(text("  Личный кабинет"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
        $x("//input[@name='login']").setValue(notRegisteredUser.getLogin());
        $x("//input[@name='password']").setValue(notRegisteredUser.getPassword());
        $x("//button[@data-test-id='action-login']").click();
        $x("//div[@data-test-id='error-notification']").shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
        $x("//input[@name='login']").setValue(blockedUser.getLogin());
        $x("//input[@name='password']").setValue(blockedUser.getPassword());
        $x("//button[@data-test-id='action-login']").click();
        $x("//div[@data-test-id='error-notification']").shouldBe(visible).shouldHave(text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        //  "Пароль" - пользователя registeredUser
        $x("//input[@name='login']").setValue(wrongLogin);
        $x("//input[@name='password']").setValue(registeredUser.getPassword());
        $x("//button[@data-test-id='action-login']").click();
        $x("//div[@data-test-id='error-notification']").shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
        //  "Пароль" - переменную wrongPassword
        $x("//input[@name='login']").setValue(registeredUser.getLogin());
        $x("//input[@name='password']").setValue(wrongPassword);
        $x("//button[@data-test-id='action-login']").click();
        $x("//div[@data-test-id='error-notification']").shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }
}