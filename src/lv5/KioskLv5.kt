package lv5

import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import lv3.Burger
import lv3.Category
import lv3.Drink
import lv3.Food
import lv3.burgers.CheeseBurger
import lv3.burgers.Hamburger
import lv3.burgers.ShackBurger
import lv3.burgers.ShroomBurger
import lv3.drinks.Cider
import lv3.drinks.Coke
import lv3.drinks.OrangeJuice
import lv3.drinks.Pepsi
import java.time.LocalDate
import java.time.LocalTime
import kotlin.system.exitProcess

class KioskLv5 {
    private var budget: Double = 0.0
    private val cart = mutableListOf<Food>()
    private val bankInspectionTime = arrayOf(
        LocalTime.of(18, 0, 0),
        LocalTime.of(19, 0, 0)
    )
    private var remainOrder = 2
    private var isOnPayment = false

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
        print("가지고 있는 자금을 입력하세요(10W = 10,000원): ")
        budget = readln().toDouble()
        printRemainOrder()

        while (true) {
            println("--------------------------------------------------------")
            println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.")
            println("자금: $budget W\n")

            printCategory()

            val size = categoryList.size
            val menuList = when(val selectedIndex = getCategoryIndex()) {
                size+1 -> {
                    onOrder()
                    continue
                }
                size+2-> exitProcess(0)
                in 1..size -> categoryList[selectedIndex-1].getMenuList()
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


    @OptIn(DelicateCoroutinesApi::class)
    private fun printRemainOrder() {
        GlobalScope.launch(IO) {
            while (true) {
                if(!isOnPayment) println("(현재 주문 대기수: ${remainOrder})")
                delay(5000)
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
            return when(readln().toInt()) {
                1 -> true
                2 -> false
                else -> throw Exception()
            }
        } catch (e: Exception) {
            println("잘못된 값을 입력하였습니다. 다시 입력해주세요.")
            return confirmToCart(food)
        }
    }

    private fun onOrder() {
        if(cart.isEmpty()) {
            println("장바구니가 비었습니다.")
            return
        }

        printOrderPage()
        when(orderOrBack()) {
            1 ->{
                val price = cart.sumOf { it.price }
                if(budget >= price){
                    doPayment(price)
                } else {
                    runBlocking {
                        println("현재 잔액은 ${budget}W 으로 ${String.format("%.2f", price-budget)}W이 부족해서 주문할 수 없습니다.")
                        delay(3000)
                    }
                }
            }
            2 -> return
            else -> throw Exception("절대 나오면 안되는 에러: orderOrBack() 예외 발생")
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

    private fun doPayment(price: Double) {
        val nowTime = LocalTime.now()
        val inspectStart = bankInspectionTime[0]
        val inspectEnd = bankInspectionTime[1]

        if(nowTime.isBiggerThan(inspectStart) && inspectEnd.isBiggerThan(nowTime)) {
            println("현재 시각은 ${timeToString(nowTime)}입니다.")
            println("은행 점검 시간은 ${timeToString(inspectStart)} ~ ${timeToString(inspectEnd)}이므로 결제할 수 없습니다.")

            runBlocking { delay(1500) }

            return
        }

        runBlocking {
            isOnPayment = true
            print("${price}W 결제중")
            delay(1500)
            print(".")
            delay(1500)
            print(".")
            delay(2000)
            print(".")

            val now = LocalTime.now()
            println("결제를 완료했습니다. (${LocalDate.now()} ${now.hour}:${now.minute}:${now.second}) 3초 후 메인화면으로 돌아갑니다.")
            remainOrder++
            isOnPayment = false

            delay(3000)
        }
    }


    private fun timeToString(time: LocalTime): String {
        val hour = time.hour

        val hourStr = if(hour > 12) "오후 ${time.hour-12}시"
                    else if(hour == 12) "오후 12시"
                    else if(hour == 0) "오전 12시"
                    else "오전 ${hour}시"

        return "$hourStr ${time.minute}분 ${time.second}초"
    }
    private fun LocalTime.isBiggerThan(comparison: LocalTime): Boolean
        = if(this.hour > comparison.hour) true
            else if(this.minute > comparison.minute) true
            else if(this.second >= comparison.second) true
            else false
}