package com.github.youta1119.either_kotlin

/**
 * Created by youta on 2017/07/14.
 */
@Suppress("unused")
sealed class Either<out A, out B> {
    abstract val isLeft: Boolean
    abstract val isRight: Boolean
}

@Suppress("unused")
data class Left<out A, out B>(val value: A) : Either<A, B>() {
    override val isLeft: Boolean = true
    override val isRight: Boolean = false
}

@Suppress("unused")
data class Right<out A, out B>(val value: B) : Either<A, B>() {
    override val isLeft: Boolean = false
    override val isRight: Boolean = true
}
