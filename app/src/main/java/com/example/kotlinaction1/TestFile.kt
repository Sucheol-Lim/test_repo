package com.example.kotlinaction1

import android.provider.ContactsContract.CommonDataKinds.Nickname

class TestFile {
}

interface User {
    val nickname: String
}

class PrivateUser(override val nickname: String): User

class SubscribeUser(val email: String):User{
    override val nickname: String
        get() {
            println("subuser nickname called")
            return email.substringBefore('@')
        }
}

class FacebookUser(val accountId: Int): User {
    override val nickname = getFacebookName(accountId)

}

fun getFacebookName(id: Int):String{
    println("getFacebookName $id")
    when(id){
        11 -> return "Nick"
        else -> return "Hi"
    }
}

class User2(val name: String){
    var address: String = "unspecified"
        set(value) {
            println("""
                Address was changed for $name:
                "$field" -> "$value"
            """.trimIndent())
            field = value
        }
}



