package lv2

class Drink(
    val name: String,
    val description: String,
    val price: Double
) {
    fun getInfo(): String
            = "$name        | W $price  | $description"
}