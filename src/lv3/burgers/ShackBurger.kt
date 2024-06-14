package lv3.burgers

import lv3.Burger

class ShackBurger: Burger(
    name = "ShackBurger",
    description = "토마토, 양상추, 쉑소스가 토핑된 치즈버거",
    price = 6.9) {

    val burgerList = listOf(
        Burger("빅맥", "맥", 8.0),
        Burger("상하이", "", 8.5)
    )
}