package com.example.kotlinaction1

import org.junit.Test

import org.junit.Assert.*

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
        val user2: User2 = User2("sucheol")
        user2.address = "kkkk"
    }
}