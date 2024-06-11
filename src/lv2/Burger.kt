package lv2

class Burger(
    val name: String,
    val description: String,
    val price: Double
) {
    fun getInfo(): String
            = "$name        | W $price  | $description"
}