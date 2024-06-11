package lv4

import lv3.Burger
import lv3.Category
import lv3.Drink
import lv3.Food
import kotlin.system.exitProcess

class KioskLv4 {
    private var budget: Double = 0.0
    private val cart = mutableListOf<Food>()

    //Lv3 클래스 재사용
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
        val shackBurger = Burger("ShackBurger", "토마토, 양상추, 쉑소스가 토핑된 치즈버거", 6.9)
        val smokeShack = Burger("SmokeShack", "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거", 8.9)
        val shroomBurger = Burger("ShroomBurger", "몬스터 치즈와 체다 치즈로 속을 채운 베지테리안 버거", 9.4)
        val cheeseburger = Burger("Cheeseburger", "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거", 6.9)
        val hamburger = Burger("Hamburger", "비프패티를 기반으로 야채가 들어간 기본버거", 5.4)

        burgerCategory.addMenu(shackBurger)
        burgerCategory.addMenu(smokeShack)
        burgerCategory.addMenu(shroomBurger)
        burgerCategory.addMenu(cheeseburger)
        burgerCategory.addMenu(hamburger)

        val cock = Drink("Coke", "코카콜라", 2.0)
        val pepsi = Drink("Pepsi", "펩시", 2.0)
        val cider = Drink("Cider", "칠성 사이다", 2.0)
        val orangeJuice = Drink("OrangeJuice", "오렌지 쥬스", 2.5)

        drinkCategory.addMenu(cock)
        drinkCategory.addMenu(pepsi)
        drinkCategory.addMenu(cider)
        drinkCategory.addMenu(orangeJuice)

        categoryList =  listOf(burgerCategory, drinkCategory)
    }

    fun main() {
        print("가지고 있는 자금을 입력하세요(10W = 10,000원): ")
        budget = readln().toDouble()

        while (true) {
            println("--------------------------------------------------------")
            println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.")
            println("자금: $budget W\n")

            printCategory()
            val menuList = when(val categoryIndex = getCategoryIndex()) {
                categoryList.size+1 -> {
                    if(cart.isNotEmpty()) {
                        printOrderPage()
                        when(orderOrBack()) {
                            1 ->{
                                val price = cart.sumOf { it.price }
                                if(budget >= price) println("주문이 완료되었습니다 :$price")
                                else println("현재 잔액은 ${budget}W 으로 ${String.format("%.2f", price-budget)}W이 부족해서 주문할 수 없습니다.")
                                break
                            }
                            2 -> continue
                            else -> throw Exception("절대 나오면 안되는 에러: orderOrBack() 예외 발생")
                        }
                    }
                    else {
                        println("장바구니가 비었습니다.")
                        continue
                    }
                }
                categoryList.size+2 -> exitProcess(0)
                in 1..categoryList.size -> categoryList[categoryIndex-1].getMenuList()
                else -> throw Exception("절대 나오면 안되는 에러: categoryIndex 예외 발생")
            }

            printMenu(menuList)
            val food = getMenu(menuList) ?: continue

            askConfirm(food)
            if(confirmToCart(food)) {
                println("${food.name}이/가 장바구니에 추가되었습니다.")
                cart.add(food)
            }
        }



    }

    private fun printCategory() {
        println("[ SHAKESHACK MENU ]")
        for (i in categoryList.indices) {
            println("${i+1}. ${categoryList[i].getInfo()}")
        }
        println("[ ORDER MENU ]\n")
        println("${categoryList.size+1}. 주문")
        println("${categoryList.size+2}. 종료")
        println("--------------------------------------------------------")
    }
    private fun getCategoryIndex(): Int {
        try {
            print("입력: ")
            when(val index = readln().toInt()) {
                in 1..categoryList.size+2 -> return index
                else -> throw Exception()
            }
        }catch (e: Exception) {
            println("잘못된 값을 입력하였습니다. 다시 입력해주세요.")
            return getCategoryIndex()
        }
    }

    private fun printMenu(menu: List<Food>) {
        for (i in menu.indices) {
            println("${i+1}: ${menu[i].getInfo()}")
        }
        println("0. 뒤로가기")
        println("--------------------------------------------------------")
    }
    private fun getMenu(menu: List<Food>): Food? {
        try {
            print("입력: ")
            return when(val index = readln().toInt()) {
                in 1..menu.size -> menu[index-1]
                0 -> null
                else -> throw Exception()
            }
        }catch (e: Exception) {
            println("잘못된 값을 입력하였습니다. 다시 입력해주세요.")
            return getMenu(menu)
        }
    }

    private fun askConfirm(food: Food) {
        println(food.getInfo())
        println("위 메뉴를 장바구니에 추가하시겠습니까?")
        println("1. 확인        2. 취소")
    }
    private fun confirmToCart(food: Food): Boolean {
        try {
            print("입력: ")
            return when(val index = readln().toInt()) {
                1 -> true
                2 -> false
                else -> throw Exception()
            }
        } catch (e: Exception) {
            println("잘못된 값을 입력하였습니다. 다시 입력해주세요.")
            return confirmToCart(food)
        }
    }

    private fun printOrderPage() {
        println("--------------------------------------------------------")
        println("아래와 같이 주문 하시겠습니까?")
        println("[ Orders ]")
        cart.forEach {
            println(it.getInfo())
        }
        println("\n[ Total ]")
        println(" W ${cart.sumOf { it.price }}")
        println("--------------------------------------------------------")
        println("\n1. 주문      2. 메뉴판")
    }
    private fun orderOrBack(): Int {
        try {
            return when(val input = readln().toInt()) {
                in 1..2 -> input
                else -> throw Exception()
            }
        } catch (e: Exception) {
            println("잘못된 값을 입력하였습니다. 다시 입력해주세요.")
            return orderOrBack()
        }
    }
}