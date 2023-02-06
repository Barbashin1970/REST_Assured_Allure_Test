package ru.yandex.praktikum;

import java.io.File;
import java.util.Collections;

public class ColourConst {
    public static File BLACK = new File("src/test/resources/orderBlack.json");
    public static File GREY = new File("src/test/resources/orderGrey.json");
    public static File GREY_AND_BLACK = new File("src/test/resources/orderGreyAndBlack.json");
    public static File NO_COLOR = new File("src/test/resources/orderNoColor.json");

    public static Order ORDER_BLACK = new Order("Олег",
            "Testman",
            "Novosibirsk",
            4,
            "+79139000000",
            7,
            "2023-02-10",
            "Be careful with my scooter",
            Collections.singletonList(("BLACK")));
    public static Order ORDER_GREY = new Order("Олег",
            "Testman",
            "Novosibirsk",
            4,
            "+79139000000",
            7,
            "2023-02-10",
            "Be careful with my scooter",
            Collections.singletonList(("GREY")));
    public static Order ORDER_GREY_BLACK = new Order("Олег",
            "Testman",
            "Novosibirsk",
            4,
            "+79139000000",
            7,
            "2023-02-10",
            "Be careful with my scooter",
            Collections.singletonList(("GREY, BLACK")));
    public static Order ORDER_NO_COLOR = new Order("Олег",
            "Testman",
            "Novosibirsk",
            4,
            "+79139000000",
            7,
            "2023-02-10",
            "Be careful with my scooter",
            Collections.singletonList(("")));
}