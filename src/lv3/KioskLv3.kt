package lv3

import lv3.burgers.CheeseBurger
import lv3.burgers.Hamburger
import lv3.burgers.ShackBurger
import lv3.burgers.ShroomBurger
import lv3.drinks.Cider
import lv3.drinks.Coke
import lv3.drinks.OrangeJuice
import lv3.drinks.Pepsi
import kotlin.system.exitProcess

class KioskLv3 {
    private var categoryList: List<Category>
    private val burgerCategory = Category(
        name = "Burger",
        description = "앵거스 비프 통살을 다져만든 버거"
    )
    private val drinkCategory = Category(
        name = "Drink",
        description = "매장에서 직접 만드는 음료"
    )

    init {
        val shackBurger = ShackBurger()
        val smokeShack = ShroomBurger()
        val shroomBurger = ShroomBurger()
        val cheeseburger = CheeseBurger()
        val hamburger = Hamburger()

        burgerCategory.addMenu(shackBurger)
        burgerCategory.addMenu(smokeShack)
        burgerCategory.addMenu(shroomBurger)
        burgerCategory.addMenu(cheeseburger)
        burgerCategory.addMenu(hamburger)

        val cock = Coke()
        val pepsi = Pepsi()
        val cider = Cider()
        val orangeJuice = OrangeJuice()

        drinkCategory.addMenu(cock)
        drinkCategory.addMenu(pepsi)
        drinkCategory.addMenu(cider)
        drinkCategory.addMenu(orangeJuice)

        categoryList =  listOf(burgerCategory, drinkCategory)
    }


    fun main() {
        while (true) {
            println("--------------------------------------------------------")
            println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.\n")

            val categoryInput = getCategory()
            if(categoryInput == 0) exitProcess(0)

            val menu = getMenu(categoryInput)

            println("${menu.name}을 장바구니에 넣었습니다.")
        }
    }

    private fun getCategory(): Int {
        println("Category")
        for (i in categoryList.indices) {
            println("${i+1}. ${categoryList[i].getInfo()}")
        }
        println("0. 종료")
        println("--------------------------------------------------------")

        var result = -1
        while (true) {
            print("입력: ")
            result = readln().toInt()
            if(result !in 0..categoryList.size) {
                println("잘못된 값을 입력하였습니다. 다시 입력해주세요.")
            } else {
                break
            }
        }

        return result
    }
    private fun getMenu(index: Int): Food {
        val menuList = categoryList[index-1].getMenuList()

        for (i in menuList.indices) {
            println("${i+1}: ${menuList[i].getInfo()}")
        }
        println("0. 뒤로가기")
        println("--------------------------------------------------------")

        val result: Food
        while (true) {
            print("입력: ")
            val input = readln().toInt()
            if(input !in 0..menuList.size) {
                println("잘못된 값을 입력하였습니다. 다시 입력해주세요.")
            } else {
                if(input == 0) exitProcess(0)
                result = menuList[input-1]

                break
            }
        }

        return result
    }
}
