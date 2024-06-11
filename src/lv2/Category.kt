package lv2

class Category(
    val name: String,
    val description: String
) {
    fun getInfo(): String
            = "\"$name        | $description"
}