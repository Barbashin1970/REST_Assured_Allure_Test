package ru.yandex.praktikum;

public class CourierId {

    private String login;
    private String password;

    public CourierId(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierId() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}