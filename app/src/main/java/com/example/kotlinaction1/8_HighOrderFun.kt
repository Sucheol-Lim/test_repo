package com.example.kotlinaction1

import java.util.concurrent.locks.Lock

fun performRequest(
    url: String,
    callback: (code: Int, content: String) -> Unit
){
    /*...*/
}

val url = "https://abc.bbb"
fun abc() {
    performRequest(url,{ age, name -> println(name)})
    performRequest(url){page, desc ->  println("$desc is $page") }
}

fun twoAndThree(operaion: (Int, Int) -> Int){
    val result = operaion(2, 3)
    println("result is $result")
}

fun String.filter(predicate: (Char) -> Boolean ) : String{
    val sb = java.lang.StringBuilder()
    for(index in indices){
        val element = get(index)
        if (predicate(element)) sb.append(element)
    }
    return sb.toString()
}

fun <T> Collection<T>.joinToString(
    preFix:String = "",
    separator:String = ",",
    postFix:String = "",
    transform: (T) -> String = {it.toString()}
): String{
    val stringBuffer = StringBuffer().append(preFix)
    for( (index, element) in this.withIndex()){
        if(index > 0) stringBuffer.append(separator)
        stringBuffer.append(transform(element))
    }
    return stringBuffer.append(postFix).toString()
}

fun <T> Collection<T>.joinToStringOrNull(
    preFix:String = "",
    separator:String = ",",
    postFix:String = "",
    transform: ((T) -> String)? = null
): String{
    val stringBuffer = StringBuffer().append(preFix)
    for( (index, element) in this.withIndex()){
        if(index > 0) stringBuffer.append(separator)
        stringBuffer.append(transform?.invoke(element) ?: element.toString())
    }
    return stringBuffer.append(postFix).toString()
}

enum class Delivery { STANDARD, EXPEDITED}

class Order(val itemCount: Int)

//함수 반환 예제
fun getShippingCostCalculator(delivery: Delivery): (Order) -> Double {
    if (delivery == Delivery.EXPEDITED) {
        return { order -> 6 + 2.1 * order.itemCount }
    }
    return { order -> 1.2 * order.itemCount }
}

data class Person(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String?
)
//함수 반환 예제
class ContactListFilters{
    var prefix: String = ""
    var onlyWithPhoneNumber: Boolean = false

    // collection filter parameter 용 (T) -> Boolean 을 반환하는 함수 선언
    fun getPredicate(): (Person) -> Boolean{
        val startWithPrefix = {p: Person ->
            p.firstName.startsWith(prefix) || p.lastName.startsWith(prefix)
        }
        if (!onlyWithPhoneNumber)
            return startWithPrefix
        return {startWithPrefix(it) && it.phoneNumber != null}
    }
}

data class SiteVisit(
    val path: String,
    val duration: Double,
    val os: OS
)
enum class OS{WINDOWS, LINUX, MAC, IOS, ANDROID}

val log = listOf(
    SiteVisit("/", 34.0, OS.ANDROID),
    SiteVisit("/login", 22.0, OS.WINDOWS),
    SiteVisit("/home", 111.0, OS.LINUX),
    SiteVisit("/signup", 12.0, OS.MAC),
    SiteVisit("/help", 2323.0, OS.IOS),
    SiteVisit("/community", 321.0, OS.WINDOWS),
    SiteVisit("/notice", 41.0, OS.MAC),
)
//확장함수로 os 파라미터를 받아 본다면
fun List<SiteVisit>.averageDurationFor(os: OS) =
    filter { it.os == os }.map(SiteVisit::duration).average()

//아예 필터 조건을 람다로 받아보자
fun List<SiteVisit>.averageDuration( predicate: (SiteVisit) -> Boolean) =
    log.filter(predicate).map(SiteVisit::path)

inline fun<T> synchronized(lock: Lock, action: () ->T): T{
    lock.lock()
    try {
        return action()
    }
    finally {
        lock.unlock()
    }
}

fun foo(l: Lock){
    println("Before sync")
    synchronized(l){
        println("Action")
    }
    println("After sync")
}

class LockOwner(val lock: Lock){
    fun runUnderLock(body: () -> Unit){
        synchronized(lock, body)
    }
}