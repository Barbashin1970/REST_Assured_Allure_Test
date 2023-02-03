package ru.yandex.praktikum;

import org.apache.commons.lang3.RandomStringUtils;

public class LoginDataConst {
    public static String RANDOM_LOGIN = RandomStringUtils.randomAlphabetic(8);
    public static String RANDOM_PASS = RandomStringUtils.randomNumeric(4);
    public static String RANDOM_NAME = RandomStringUtils.randomAlphabetic(8);
}