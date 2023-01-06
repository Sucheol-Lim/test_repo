package com.example.kotlinaction1

/**
 * <code>ScopeFunctions</code>
 *
 * @version $
 * @author lsc
 * @since 2023/01/04
 */


/**
 * fun <T, R> T.let(block: (T) -> R): R
 * fun <T, R> with(receiver: T, block: T.() -> R): R
 * fun <T, R> T.run(block: T.() -> R): R
 * fun <R> run(block: () -> R): R
 * fun <T> T.apply(block: T.() -> Unit): T
 * fun <T> T.also(block: (T) -> Unit): T
 */
//also and apply 자기 자신을 반환.. builder 패턴으로 쓰기 좋음



data class Person3(var name: String?, var age: Int)

/**
 * let 함수를 사용하면 객체의 상태를 변경할 수 있다.
 * fun <T, R> T.let(block: (T) -> R): R
 * 자세히 보면 T.() 로 받은게 아니어서 it keyword 를 쓴다.
 * let 은 매개화 된 변수 T 의 확장함수! 이다.
 */
fun letSample(){
    val person = Person3(null, 0)
    val resultIt = person.let {
        it.name = it.name ?: "No Name"
        it.age = 30
        "thanks"
        // return 과 무관하게 당연히 property 도 바뀌는 걸 볼 수 있음.
    }
    println("result 1 $resultIt")
    println("result 1 $person")
    val resultIt2 = person.let {
        it.name = "Sole"
        it.age = 12
        it
    }
    println("result 2 $resultIt2")
    println("result 2 $person")

//    result 1 thanks
//    result 1 Person3(name=James, age=30)
//    result 2 Person3(name=Sola, age=12)
//    result 2 Person3(name=Sola, age=12)
}

//
/**
 * fun <T, R> with(receiver: T, block: T.() -> R): R
 * with 는 일반 함수 이다. 그래서 객체를 직접 받고 이를 사용하기 위한 block 을 두 번째로 받는다!
 * 객체 함수를 여러개 호출 할 때 코드를 block 화 하기 조으다!
 */
fun withSample(){
    val person = Person3(null, 0)
    var result = with(person){
        name = name ?: "no name"
        age = 30
        //this // or 필요에 따라 다른 객체 반환.
    }
    println(result)
}

/**
 * run은 두 가지 형태로 선언되어 있다. 먼저 첫 번째
 * fun <T, R> T.run(block: T.() -> R): R
 *  T 의 확장 함수로 동작할 때
 */
fun runSample1(){
    val person = Person3("James", 56)
    val ageNextYear = person.run{
        ++age
    }
    println(ageNextYear)
}

/**
 * 두 번째는 그냥 명령문을 블럭 안에 넣어 가독성을 높이고 리턴해 주고 끝!
 * 마지막 리턴 문이 없으면 Unit 리턴
 * fun <R> run(block: () -> R): R
 */
fun runSample2(){
    val tmp = run {
        val name = "James"
        val age = 36
        //Person3(name, age)
    }
    println(tmp)
}

/**
 * fun <T> T.apply(block: T.() -> Unit): T
 * T의 확장 함수이고, 블럭 함수의 입력을 람다 리시버로 받았기 때문에 블럭 안에서 객체의 프로퍼티를 호출할 때 it이나 this를 사용할 필요가 없다
 * run 과 유사하지만 block 수행 후 리턴 문 없이 자기자신을 반환한다.
 */
fun applySample(){
    val person = Person3(null, 25)
    var name: String
    val person2 = person.apply {
        name = "James"
        // 범위 내 같은 이름이 있다면 주의해야 함.
        ++age
    }
    //println(person)
    //println(person2)
    println("$name .. and ${person.name}")
}

/**
 * fun <T> T.also(block: (T) -> Unit): T
 * apply 와 다른 점은 T가 block param 으로 들어 간다는 점.
 */
fun alsoSample(){
    val person = Person3(null, 25)
    val result = person.also {
        it.name = "James"
        requireNotNull(it.name)
        ++it.age
        "hi" //this expression is unused. 의미 없음
    }
    println(person == result)
    //>>> true
}
