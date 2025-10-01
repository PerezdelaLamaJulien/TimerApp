package com.jperez.timerapp.feature.model


enum class CategoryColor(val value: Long, dark: Boolean, light: Boolean) {
    //todo : select color depending dark or light
    PERSONA5_YELLOW(0xfff2e852, true, false),
    PERSONA5_BORDEAUX(0xff732424, true, true),
    PERSONA3_LIGHT_BLUE(0Xff79d7fd, true, true),
    PERSONA3_MID_BLUE(0Xff00bbfa, true, true),
    LIGHT_GREEN(0Xff59ac77, true, true),
    DARK_GREEN(0Xff3a6f43, true, true),
    LIGHT_PINK(0Xffffd5d5, true, false),
    DARK_PINK(0Xfffdaaaa, true, true),
    ELECTRIC_PINK(0Xffe45a92, true, true),
    DARK_PURPLE(0Xff5d2f77, true, true),
    DARKEST_PURPLE(0Xff3e1e68, false, true),
    PALE_YELLOW(0Xfff8f7ba, true, false),
    PALE_GREEN(0Xffbde3c3, true, false),
    PALE_BLUE(0Xffa3ccda, true, true),
    PALE_PEACH(0Xfff5d2d2, true, false),
    MITSURU_RED(0Xff942324, true, true),
    MITSURU_BROWN(0Xff5d0d10, true, true),
    FUUKA_BLUE(0Xff549ba2, true, true),
    KORO_DARK_GRAY(0Xff949390, true, true),
    KORO_LIGHT_GRAY(0Xffc6c5bf, true, true),
    KEN_LIGHT_ORANGE(0Xfff48b1c, true, true),
    KEN_DARK_ORANGE(0Xffc05914, true, true),
    YUKARI_PINK(0Xffe08396, true, true),
}