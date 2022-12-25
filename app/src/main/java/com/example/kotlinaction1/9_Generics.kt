package com.example.kotlinaction1

import android.app.Activity
import android.content.Context
import android.content.Intent

val readers: MutableList<String> = mutableListOf()
val readers2 = mutableListOf<String>()

fun testGeneric(){
    val letters = ('a'..'z').toList()
    println(letters.slice(0..2))

    val authors = listOf("Dikalkey", "akejlkv")

    val readers3 = mutableListOf<String>()
    readers3.filter { it !in authors }

    val list:List<String> = listOf("Dikalkey", "akejlkv")
    if (list is List<String>) {
        println(authors.lastone)
    }
}

fun printSum(c: Collection<*>){
    val strList = c as? List<String>
        ?: throw java.lang.IllegalArgumentException("list")
}

val <LAST_ONE> List<LAST_ONE>.lastone: LAST_ONE
    get() = this[size - 1]

fun <T: Number> oneHalf(value: T): Double{
    return value.toDouble() / 2.0
}
fun <T> ensureTrailingPeriod(seq: T): String
    where T: Appendable, T: CharSequence{
    var result = ""
    if (!seq.endsWith('.')) {
        result = "$seq."
    }
    return result
}

open class Alcohol
class Soju : Alcohol()

interface Drinker<T> {
    fun drink()
}

fun varianceTest(input: Drinker<out Alcohol>){ // out keyword 추가
    input.drink()
}

fun tasteOutDrink(input:Drinker<out Alcohol>){
    input.drink()
}

fun tasteInDrink(input:Drinker<in Alcohol>){
    input.drink()
}

fun testVariance(){
    val alcohol: Drinker<Alcohol> = object : Drinker<Alcohol> {
        override fun drink() {
            println("drink Alcohol")
        }
    }
    val soju: Drinker<Soju> = object : Drinker<Soju> {
        override fun drink() {
            println("drink Soju")
        }
    }
    val any: Drinker<Any> = object : Drinker<Any> {
        override fun drink() {
            println("drink any")
        }
    }
    tasteOutDrink(soju)
    //tasteOutDrink(any)
    //tasteInDrink(soju)
    tasteInDrink(any)
}

inline fun <reified T: Activity> Context.startActivity(){
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}