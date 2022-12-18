package com.example.kotlinaction1

import org.junit.Test

import org.junit.Assert.*
import java.beans.PropertyChangeListener
import java.time.LocalDate

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testInterfaceProperty() {
        //println(PrivateUser("test@Kotlin.org").nickname)
        val UserSub = SubscribeUser("test@Kotlin.org")
        val UserFace = FacebookUser(11)
//        println("=== ${UserSub.nickname}")
//        println("=== ${UserSub.nickname}")
//        println("=== ${UserSub.nickname}")
//        println("=== ${UserFace.nickname}")
//        println("=== ${UserFace.nickname}")
//        println("=== ${UserFace.nickname}")
        //val user2: User2 = User2("sucheol")
        //user2.address = "kkkk"
        //testPayRoll()
    }

    /**
     * 범위 연산자.
     */
    @Test
    fun dateTest(){
        val now = LocalDate.now()
        val vacation = now..now.plusMonths(1)
        println(now.plusWeeks(1) in vacation)
        println(vacation)
        //괄호로 묶으면 아래와 같은 식도 가능.
        (0..12).forEach{println(it)}
    }

    @Test
    fun delegationTest(){
        //val p = PersonStep1("sucheol", 35, 2000)
        val p = PersonStep2("sucheol", 35, 2000)
        p.addPropertyChangeListener(
            PropertyChangeListener {
                event -> println("${event.propertyName} changed from ${event.oldValue} to ${event.newValue}")
            }
        )
        p.age = 36
        p.salary = 9800
    }

}