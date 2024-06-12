import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lv1.KioskLv1
import lv2.KioskLv2
import lv3.KioskLv3
import lv4.KioskLv4
import lv5.KioskLv5
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Date
import kotlin.math.pow
import kotlin.math.sqrt

//TODO: Lv0: 요구사항 및 상세기능 정리
//      요구사항)
//      1.메뉴 카테고리: 번호에 매핑 후 번호 입력받기
//          > 번호 예외 처리, -1 입력 시 종료, 0 입력 시 주문하기
//      2. 상세 메뉴판 띄우기: 메뉴 설명 및 가격 표시, 메뉴 번호에 매핑 후 번호 입력 받기
//          > 번호 예외 처리, -1 입력시 뒤로가기
//      3. 메뉴 구매 페이지: 단품 가격을 표시하고 수량 선택하기
//          > 수량 음수 예외 처리, 장바구니에 담고 메뉴 카테고리 화면으로
//      4. 주문하기: 장바구니 메뉴 확인 및 총 가격 표시
//          > 주문 후 종료


fun main() {
    val kioskLv1 = KioskLv1()
    val kioskLv2 = KioskLv2()
    val kioskLv3 = KioskLv3()
    val kioskLv4 = KioskLv4()
    val kioskLv5 = KioskLv5()

    //kioskLv1.main()
    //kioskLv2.main()
    //kioskLv3.main()
    //kioskLv4.main()
    kioskLv5.main()
}