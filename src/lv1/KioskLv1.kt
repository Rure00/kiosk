package lv1

import kotlin.system.exitProcess

class KioskLv1 {
    private val category = arrayOf("Burgers", "Drinks")
    private val categoryDescription = arrayOf(
        "앵거스 비프 통살을 다져만든 버거",
        "매장에서 직접 만드는 음료",
    )

    private val burgerMenu = arrayOf("ShackBurger", "SmokeShack", "ShroomBurger", "Cheeseburger", "Hamburger")
    private val burgerDescription = arrayOf(
        "토마토, 양상추, 쉑소스가 토핑된 치즈버거",
        "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거",
        "몬스터 치즈와 체다 치즈로 속을 채운 베지테리안 버거",
        "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거",
        "비프패티를 기반으로 야채가 들어간 기본버거"
    )
    private val burgerPrice = arrayOf(6.9, 8.9, 9.4, 6.9, 5.4)

    private val drinkMenu = arrayOf("Coke", "Pepsi", "Cider", "OrangeJuice")
    private val drinkDescription = arrayOf(
        "코카콜라",
        "펩시",
        "칠성 사이다",
        "오렌지 쥬스"
    )
    private val drinkPrice = arrayOf(2.0, 2.0, 2.0, 2.5)

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
            println("${i+1}. ${category[i]}      | ${categoryDescription[i]}")
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
        val menuName = when(index) {
            1 -> burgerMenu
            2 -> drinkMenu
            else -> throw Exception("잘못된 값 입력")
        }
        val menuDes = when(index) {
            1 -> burgerDescription
            2 -> drinkDescription
            else -> throw Exception("잘못된 값 입력")
        }
        val menuPrice = when(index) {
            1 -> burgerPrice
            2 -> drinkPrice
            else -> throw Exception("잘못된 값 입력")
        }

        for (i in menuName.indices) {
            println("${i+1}: ${menuName[i]}     | W ${menuPrice[i]} |   ${menuDes[i]}")
        }
        println("0. 뒤로가기")
        println("--------------------------------------------------------")

        var result = -1
        while (true) {
            print("입력: ")
            result = readln().toInt()
            if(result !in 0..menuName.size) {
                println("잘못된 값을 입력하였습니다. 다시 입력해주세요.")
            } else {
                break
            }
        }

        return result
    }
}