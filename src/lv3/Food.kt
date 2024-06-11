package lv3

abstract class Food {
    abstract val name: String
    abstract val description: String
    abstract val price: Double

    fun getInfo(): String
        = "$name        | W $price  | $description"
}