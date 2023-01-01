package com.example.kotlinaction1

import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.system.measureTimeMillis

/**
 * <code>Coroutine</code>
 *
 * @version $
 * @author lsc
 * @since 2022/12/27
 */

fun now() = ZonedDateTime.now().toLocalTime().truncatedTo(ChronoUnit.MILLIS)

fun log(msg: String) = println("${now()}:${Thread.currentThread()}:${msg}")

fun launchInGlobalScope(){
    GlobalScope.launch {
        log("coroutine started.")
    }
}

fun testGlobalScope(){
    log("main started")
    launchInGlobalScope()
    log("launchInGlobalScope() executed")
    Thread.sleep(5000L)
    log("main terminated")
}

fun runBlockingExample(){
    runBlocking{
        launch {
            log("CoroutineScope.launch started")
        }
    }
}

fun testBlockingGlobalScope(){
    log("main started")
    log("runBlockingExample() executed")
    runBlockingExample()
    //Thread.sleep(5000L)
    log("main terminated")
}

fun yieldExample(){
    var firstLaunch: Job?
    runBlocking {
        firstLaunch = launch {
            log("A-1")
            yield()
            log("A-2")
            yield()
            log("A-3")
            yield()
            log("A-4")
        }
        log("after first launch")
        launch {
            log("B-1")
            log("B-2")
            yield()
            log("B-3")
            firstLaunch!!.cancel()
            log("B-4")
        }
        log("after second launch")
    }
}

fun yieldExampleCall(){
    log("main started")
    log("yieldExample() executed")
    yieldExample()
    //Thread.sleep(5000L)
    log("main terminated")
}

fun sumAllAsync(){
    runBlocking {
        val d1 = async {
            log("start d1")
            delay(1000L)
            log("end d1")
            1 }
        log("after async d1")

        val d2 = async {
            log("start d2")
            delay(5000L)
            log("end d2")
            2
        }
        log("after async d2")

        val d3 = async {
            log("start d3")
            delay(3000L)
            log("end d3")
            3
        }
        log("after async d3")

        log("1+2+3 = ${d1.await() + d2.await() + d3.await()}")
        log("after await all & add")
    }
}

fun sumAllAsyncCall(){
    log("main started")
    log("sumAllAsync() executed")
    sumAllAsync()
    //Thread.sleep(5000L)
    log("main terminated")
}

@OptIn(DelicateCoroutinesApi::class)
fun coroutineContextTest(){
    GlobalScope.launch {
        log("main1 ")
    }

    GlobalScope.launch(Dispatchers.Unconfined){
        log("unconfined")
    }

    GlobalScope.launch(Dispatchers.Default){
        log("default")
    }

//    CoroutineScope(Main).launch {
//        log("main")
//    }

    CoroutineScope(IO).launch {
        log("io")
    }

    var executor: ExecutorCoroutineDispatcher = newSingleThreadContext("MyOwn")
    GlobalScope.launch(executor){
        log("newSingleThread1")
    }

    GlobalScope.launch {
        log("main2")
    }

    GlobalScope.launch(executor){
        log("newSingleThread2")
    }
}

fun coroutineBenchTest() {
    runBlocking {
        println("시작::활성화 된 스레드 갯수 = ${Thread.activeCount()}")
        val time = measureTimeMillis {
            //Note : measureTimeMillis는 코드 블럭을 갖는 인라인 함수로써 실행시간(ms)을 반환한다.
            val jobs = ArrayList<Job>()
            repeat(10000) {
                jobs += launch(Dispatchers.Default) {
                    delay(500L)
                }
            }
            println("끝::활성화 된 스레드 갯수 = ${Thread.activeCount()}")
            jobs.forEach { it.join() }
        }
        println("Took $time ms")
    }
}

fun threadBenchTest() {
    runBlocking {
        println("시작::활성화 된 스레드 갯수 = ${Thread.activeCount()}")
        val time = measureTimeMillis {
            val jobs = ArrayList<Thread>()
            repeat(128) {
                jobs += Thread {
                    Thread.sleep(500L)
                }.also { it.start() }
            }
            println("끝::활성화 된 스레드 갯수 = ${Thread.activeCount()}")
            jobs.forEach { it.join() }
        }
        println("Took $time ms")
    }
}