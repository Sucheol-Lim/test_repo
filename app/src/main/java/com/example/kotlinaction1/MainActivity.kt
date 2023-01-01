package com.example.kotlinaction1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    @Inject
    lateinit var coffeeMaker: CoffeeMaker

    override fun onStart() {
        super.onStart()
        testCoffeeMaker()
    }
    private fun testCoffeeMaker(){
        if (::coffeeMaker.isInitialized) {
            coffeeMaker.brew()
        } else {
            Log.d("MainActivity", "testCoffeeMaker: not initialized")
        }
    }
}

sealed class Expr(val id: Int){


    class Num(val value: Int) : Expr(value)
    class Sum(val value: Int, val left: Expr, val right: Expr) : Expr(value)
}

fun eval(e: Expr): Int =
    when(e){
        is Expr.Num -> e.id
        is Expr.Sum -> 0
    }

class Test(){
    constructor(id: Int) : this(id, "default name")  {

    }
    constructor(id: Int, name: String) : this() {

    }
}