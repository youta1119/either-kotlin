package com.github.youta1119.either_kotlin

/**
 * Created by youta on 2017/07/12.
 */

@Suppress("unused")
inline fun <A, B, X> Either<A, B>.fold(left: (A) -> X, right: (B) -> X): X = when (this) {
    is Right -> right(value)
    is Left -> left(value)
}

@Suppress("unused")
fun <A, B> Either<A, B>.swap(): Either<B, A> = when (this) {
    is Right -> Left(value)
    is Left -> Right(value)
}

@Suppress("unused")
inline fun <A, B> Either<A, B>.foreach(action: (B) -> Unit): Unit = when (this) {
    is Right -> action(this.value)
    is Left -> Unit
}

@Suppress("unused")
fun <A, B> Either<A, B>.getOrElse(defaultValue: B): B = when (this) {
    is Right -> this.value
    is Left -> defaultValue
}

@Suppress("unused")
fun <A, B> Either<A, B>.contains(elem: B): Boolean = when (this) {
    is Right -> elem == this.value
    is Left -> false
}

@Suppress("unused")
inline fun <A, B> Either<A, B>.forall(action: (B) -> Boolean): Boolean = when (this) {
    is Right -> action(this.value)
    is Left -> false
}

@Suppress("unused")
inline fun <A, B> Either<A, B>.exists(predicate: (B) -> Boolean): Boolean = when (this) {
    is Right -> predicate(this.value)
    is Left -> false
}

@Suppress("UNCHECKED_CAST", "unused")
inline fun <A, B, C> Either<A, B>.flatMap(transform: (B) -> Either<A, C>): Either<A, C> = when (this) {
    is Right -> transform(this.value)
    is Left -> this as Either<A, C>
}


@Suppress("UNCHECKED_CAST", "unused")
inline fun <A, B, C> Either<A, B>.map(transform: (B) -> C): Either<A, C> = when (this) {
    is Right -> Right(transform(this.value))
    is Left -> this as Either<A, C>
}


@Suppress("UNCHECKED_CAST", "unused")
inline fun <A, B> Either<A, B>.filterOrElse(predicate: (B) -> Boolean,defaultValue: A): Either<A, B> = when (this) {
    is Right -> if (predicate(this.value)) this else Left(defaultValue)
    is Left -> this
}


@Suppress("unused")
fun <A, B> Either<A, B>.toSeq(): Sequence<B> = when (this) {
    is Right -> sequenceOf(this.value)
    is Left -> emptySequence()
}

@Suppress("unused")
fun <A, B> Either<A, B>.toList(): List<B> = when (this) {
    is Right -> listOf(this.value)
    is Left -> emptyList()
}








