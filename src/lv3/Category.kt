package lv3

class Category(
    private val name: String,
    private val description: String,
) {
    private val menuList: MutableList<Food> = mutableListOf()

    fun getInfo(): String
            = "$name        | $description"
    fun addMenu(menu: Food) {
        menuList.add(menu)
    }
    fun getMenuList() = menuList.toList()
}