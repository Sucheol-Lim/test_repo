package com.example.kotlinaction1

/**
 * <code>`2_KotlinBasic`</code>
 *
 * @version $
 * @author lsc
 * @since 2023/01/01
 */



fun addLanguage(lang: String){

    val language: ArrayList<String> = arrayListOf("Java")
    language.add(lang)
    println(
        """
            ${if(lang.length < 3) "too short language" else {
            "normal String ${lang}"
            }
        }
        """.trimIndent()
    )
}