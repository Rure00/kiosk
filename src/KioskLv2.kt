import jdk.jfr.Description
import kotlin.system.exitProcess

class KioskLv2 {
    private val category = arrayOf(
        Category("Burger", "앵거스 비프 통살을 다져만든 버거"),
        Category("Drinks", "매장에서 직접 만드는 음료")
    )
    private val burgerMenu = arrayOf(
        Burger("ShackBurger", "토마토, 양상추, 쉑소스가 토핑된 치즈버거", 6.9),
        Burger("SmokeShack", "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거", 8.9),
        Burger("ShroomBurger", "몬스터 치즈와 체다 치즈로 속을 채운 베지테리안 버거", 9.4),
        Burger("Cheeseburger", "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거", 6.9),
        Burger("Hamburger", "비프패티를 기반으로 야채가 들어간 기본버거", 5.4),
    )
    private val drinkMenu = arrayOf(
        Drink("Coke", "코카콜라", 2.0),
        Drink("Pepsi", "펩시", 2.0),
        Drink("Cider", "칠성 사이다", 2.0),
        Drink("OrangeJuice", "오렌지 쥬스", 2.5)
    )

    fun main() {
        while (true) {
            println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.\n")

            val categoryInput = getCategory()
            if(categoryInput == 0) exitProcess(0)

            val menuInput = getMenu(categoryInput)
            if(menuInput == 0) exitProcess(0)

            println("장바구니에 넣었습니다!")
        }
    }

    private fun getCategory(): Int {
        println("Category")
        for (i in category.indices) {
            println("${i+1}. ${category[i].getInfo()}")
        }
        println("0. 종료")
        println("--------------------------------------------------------")

        var result = -1
        while (true) {
            print("입력: ")
            result = readln().toInt()
            if(result !in 0..category.size) {
                println("잘못된 값을 입력하였습니다. 다시 입력해주세요.")
            } else {
                break
            }
        }

        return result
    }
    private fun getMenu(index: Int): Int {
        var result = -1
        if(index == 1) {
            val burgerList = burgerMenu
            for (i in burgerList.indices) {
                println("${i+1}: ${burgerList[i].getInfo()}")
            }
            println("0. 뒤로가기")
            println("--------------------------------------------------------")

            while (true) {
                print("입력: ")
                result = readln().toInt()
                if(result !in 0..burgerMenu.size) {
                    println("잘못된 값을 입력하였습니다. 다시 입력해주세요.")
                } else {
                    break
                }
            }
        } else if(index == 2) {
            val drinkList = drinkMenu
            for (i in drinkList.indices) {
                println("${i+1}: ${drinkList[i].getInfo()}")
            }
            println("0. 뒤로가기")
            println("--------------------------------------------------------")

            while (true) {
                print("입력: ")
                result = readln().toInt()
                if(result !in 0..drinkList.size) {
                    println("잘못된 값을 입력하였습니다. 다시 입력해주세요.")
                } else {
                    break
                }
            }
        } else throw Exception("잘못된 값 입력")

        return result
    }
}

class Category(
    val name: String,
    val description: String
) {
    fun getInfo(): String
        = "\"$name        | $description"
}

class Burger(
    val name: String,
    val description: String,
    val price: Double
) {
    fun getInfo(): String
        = "$name        | W $price  | $description"
}

class Drink(
    val name: String,
    val description: String,
    val price: Double
) {
    fun getInfo(): String
            = "$name        | W $price  | $description"
}