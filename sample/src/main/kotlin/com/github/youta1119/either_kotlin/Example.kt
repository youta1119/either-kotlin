package com.github.youta1119.either_kotlin

/**
 * Created by youta on 2017/07/14.
 */

fun main(args: Array<String>) {
   println("hello world")
   val left = Left<String,Int>("aaaaa")

   println(left)
}