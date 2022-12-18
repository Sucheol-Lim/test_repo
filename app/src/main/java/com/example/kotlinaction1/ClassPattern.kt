package com.example.kotlinaction1

import android.app.Person


/**
 * private constructor
 * companion object 를 활용한 factory pattern
 */
class User3 private constructor(val nickname: String){
    companion object{
        fun newSubUser(email:String): User3{
            return User3(email.substringBefore('@'))
        }
        fun newFaceUser(id: Int) = User3(getFacebookName(id))
    }
}

/**
 * 위 방식은 원래 부 생성자를 이용해서 많이 선언.
 */

class User4 {
    val nickname: String
    constructor(email: String) {
        nickname = email.substringAfter('@')
    }
    constructor(id: Int){
        nickname = getFacebookName(id)
    }
}

/**
 * singleton 패턴
 */
object Payroll{
    val allEmployee = arrayListOf<Person>()
    fun calculateSalary(){
        for (person in allEmployee) {

        }
    }
}

fun testPayRoll(){
    println(Payroll.allEmployee)
    Payroll.calculateSalary()
    val people = listOf(People("a"), People("b"))
    println(people.sortedWith(People.NameComparator))
}

/**
 * 중첩객체를 이용해 comparator 구현
 */
data class People(val name: String){
    object NameComparator: Comparator<People>{
        override fun compare(p0: People, p1: People): Int =
            p0.name.compareTo(p1.name)
    }
}