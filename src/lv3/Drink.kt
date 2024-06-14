package lv3

open class Drink(
    override val name: String,
    override val description: String,
    override val price: Double
): Food() {

}