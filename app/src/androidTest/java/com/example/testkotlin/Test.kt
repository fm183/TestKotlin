package com.example.testkotlin

import org.junit.Test

class Test {

    @Test
    fun test(){
        val count = 96
        val ringValue = (360f - count) / count
        println("==== test ringValue=$ringValue")
        var rotateValue = 0f
        for (i in 0 until count) {
            rotateValue += ringValue + 1
            println("==== test rotateValue=$rotateValue")
        }
    }

}