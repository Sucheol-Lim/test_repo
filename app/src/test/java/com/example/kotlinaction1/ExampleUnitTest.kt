package com.example.kotlinaction1

import org.junit.Test

import org.junit.Assert.*
import java.beans.PropertyChangeListener
import java.time.LocalDate
import java.util.*

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

    @Test
    fun highOrderTest(){
        //twoAndThree { a, b -> a * b }

//        println("su_cheo_l".filter {
//            it in 'a'..'z' || it in 'A'..'Z'
//        })

        val letters = listOf("Sucheol", "Jiyoung", "Jihyo")
        println(letters.joinToString(separator = " - "))
        println(letters.joinToString(
            separator = ", ",
            preFix = "My Family : ",
            transform = {
                it.uppercase(Locale.getDefault())
            }
        ))
    }

    @Test
    fun highOrderTest2(){
        var calculator = getShippingCostCalculator(Delivery.EXPEDITED)
        println(calculator(Order(30)))

        val contacts = listOf(
            Person("Dmitry", "Choi", "010-6390-4686"),
            Person("Kornel", "Park", null)
        )
        val contactListFilters = ContactListFilters()
        with(contactListFilters){
            prefix = "K"
            onlyWithPhoneNumber = true
        }
        println(contacts.filter(contactListFilters.getPredicate()) )

        val averageWindowDuration = log.filter { it.os == OS.WINDOWS }
            .map(SiteVisit::duration)
            .average()
        val averageWindowDuration2 = log.averageDurationFor(OS.WINDOWS)
        println("average : $averageWindowDuration")

        //mobile 만 뽑아본다면..
        val averageMobile = log.filter { it.os in setOf(OS.IOS, OS.ANDROID) }
            .map(SiteVisit::duration)
            .average()
        println(
            log.averageDuration { it.os == OS.WINDOWS }
        )
    }

    @Test
    fun inlineTest(){
        val sb = java.lang.StringBuilder()
        sb.apply pp@{
            listOf(1,2,4).apply {
                this@pp.append(this.toString())
            }
        }
        println(sb)

        lookForAlice(listOf(Person("","Alice", "")))
    }

    @Test
    fun testGenerics(){
        var list: MutableList< Int> = mutableListOf(1,2,3)
        var listInt: MutableList<out Number> = mutableListOf(3)

        listInt.add(5 as Nothing)
        println(listInt)
    }

    @Test
    fun testCoroutines(){
        //testGlobalScope()
        //testBlockingGlobalScope()
        //yieldExampleCall()
        //sumAllAsyncCall()
        //coroutineContextTest()
        coroutineBenchTest()
        threadBenchTest()
    }

    @Test
    fun testKotlinBase(){
        addLanguage("c++")
    }

    @Test
    fun testScopeMethod(){
        applySample()
    }
}