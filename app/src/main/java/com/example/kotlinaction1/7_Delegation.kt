package com.example.kotlinaction1

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import kotlin.properties.Delegates
import kotlin.reflect.KProperty


class DelegatingCollection<T>(
    innerList: Collection<T> = ArrayList<T>()
) : Collection<T> by innerList

/**
 * by 키워드를 활용한 클래스 위임.
 */
class CountingSet<T>(
    val innerSet: MutableCollection<T> = HashSet<T>()
) : MutableCollection<T> by innerSet {
    var objectAdded = 0

    override fun add(element: T): Boolean {
        objectAdded++
        return innerSet.add(element)
    }

    override fun addAll(c: Collection<T>): Boolean {
        objectAdded += c.size
        return innerSet.addAll(c)
    }
}

/**
 * delegated property
 *
 *
 */
class Email{}
fun loadEmails(person: PersonB): List<Email>{
    println("${person.name} 의 email 을 가져옴")
    return listOf()
}
fun loadEmails(person: PersonA): List<Email>{
    println("${person.name} 의 email 을 가져옴")
    return listOf()
}
/** backing property
 * */
class PersonB(val name: String){
    private var mails: List<Email>? = null
    val emails: List<Email>
        get() {
            if (mails == null) {
                mails = loadEmails(this)
            }
            return mails!!
        }
}
/** 하지만 이런 코드는 지연 초기화하는 프로퍼티가 많아지면 불편하고 스레드 안전하지 않다.
 *  위임 프로퍼티를 사용하면 코드가 간단해진다. 그리고 한번만 초기화됨을 보장한다.
 */
class PersonA(val name: String){
    val emails by lazy{ loadEmails(this) }
}

class PersonStep1(
    val name: String, age: Int, salary: Int
): PropertyChangeAware(){
    //setter code 를 보면 중복이 많다.
    var age: Int = age
        set(value) {
            val oldValue = field
            field = value
            changeSupport.firePropertyChange(
                "age", oldValue, value
            )
        }
    var salary: Int = salary
        set(value) {
            val oldValue = field
            field = value
            changeSupport.firePropertyChange(
                "salary", oldValue, value
            )
        }
}

// 도우미 클래스를 보완해서 프로퍼티 변경 통지를 구현해보자
class ObservableProperty(
    val propName: String, var propValue: Int,
    val changeSupport: PropertyChangeSupport
) {
    fun getValue(): Int = propValue
    fun setValue(newValue: Int){
        val oldValue = propValue
        propValue = newValue
        changeSupport.firePropertyChange(
            propName, oldValue, newValue
        )
    }
}

class PersonStep2(
    val name: String,
    ageParam: Int, salaryParam: Int
): PropertyChangeAware(){
    val _age = ObservableProperty("age", ageParam, changeSupport)
    var age: Int
        get() = _age.getValue()
        set(value) {_age.setValue(value)}
    val _salary = ObservableProperty("salary", salaryParam, changeSupport)
    var salary: Int
        get() = _salary.getValue()
        set(value) {_salary.setValue(value)}
}

// step3 - by keyword 를 사용해서
class ObservablePropertyStep3(
    var propValue: Int, val changeSupport: PropertyChangeSupport
){
    operator fun getValue(personStep3: PersonStep3, property: KProperty<*>): Int {
        return propValue
    }
    operator fun setValue(personStep3: PersonStep3, property: KProperty<*>, newValue: Int) {
        val oldValue = propValue
        propValue = newValue
        changeSupport.firePropertyChange(property.name, oldValue, newValue)
    }
}


class PersonStep3(
    val name: String, ageParam: Int, salaryParam: Int
): PropertyChangeAware(){
    var age: Int by ObservablePropertyStep3(ageParam, changeSupport)
    var salary: Int by ObservablePropertyStep3(salaryParam, changeSupport)
}

/** 위임 프로퍼티 구현
 */
// PropertyChangeSupport 를 사용하기 위한 도우미 클래스
open class PropertyChangeAware{
    protected val changeSupport = PropertyChangeSupport(this)

    fun addPropertyChangeListener(listener: PropertyChangeListener){
        changeSupport.addPropertyChangeListener(listener)
    }
    fun removePropertyChangeListener(listener: PropertyChangeListener){
        changeSupport.removePropertyChangeListener(listener)
    }
}

class PersonStep4(
    val name: String, ageParam: Int, salaryParam: Int
): PropertyChangeAware(){
    private val observer = {
        prop: KProperty<*>, oldValue:Int, newValue:Int ->
            changeSupport.firePropertyChange(prop.name, oldValue, newValue)
    }
    //var age: Int by Delegates.observable(ageParam,observer)
    var age: Int by PropertyDelegate(ageParam)
    var salary: Int by Delegates.observable(salaryParam, observer)
}

class PropertyDelegate(
    var propValue: Int
){
    operator fun getValue(personStep4: PersonStep4, property: KProperty<*>): Int {
        return propValue
    }
    operator fun setValue(personStep4: PersonStep4, property: KProperty<*>, newValue: Int) {
        propValue = newValue + newValue
    }
}

class Persson{
    private val _attributes = hashMapOf<String, String>()
    fun setAttribute(attrName: String, value: String){
        _attributes[attrName] = value;
    }
    val name: String by _attributes
    val company: String by _attributes
}
