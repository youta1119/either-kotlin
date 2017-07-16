package com.github.youta1119.either_kotlin

@Suppress("unused")
sealed class Either<A, B> {
    abstract val isLeft: Boolean
    abstract val isRight: Boolean
}

@Suppress("unused")
data class Left<A, B>(val value: A) : Either<A, B>() {
    override val isLeft: Boolean = true
    override val isRight: Boolean = false
}

@Suppress("unused")
data class Right<A, B>(val value: B) : Either<A, B>() {
    override val isLeft: Boolean = false
    override val isRight: Boolean = true
}

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

@Suppress("UNCHECKED_CAST", "unused")
fun <A, B : Either<A1, C>, A1, C> Either<A, B>.joinRight(): Either<A1, C> = when (this) {
    is Right -> this.value
    is Left -> this as Either<A1, C>
}

@Suppress("UNCHECKED_CAST", "unused")
fun <A : Either<C, B1>, B, B1, C> Either<A, B>.joinLeft(): Either<C, B1> = when (this) {
    is Right -> this as Either<C, B1>
    is Left -> this.value
}

@Suppress("unused")
inline fun <A, B> Either<A, B>.foreach(action: (B) -> Unit): Unit = when (this) {
    is Right -> action(this.value)
    is Left -> Unit
}

@Suppress("unused")
fun <A, B : B1, B1> Either<A, B>.getOrElse(defaultValue: B1): B1 = when (this) {
    is Right -> this.value
    is Left -> defaultValue
}

@Suppress("unused")
fun <A, B : B1, B1> Either<A, B>.contains(elem: B1): Boolean = when (this) {
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
inline fun <A, B,A1,B1> Either<A, B>.flatMap(transform: (B) -> Either<A1, B1>): Either<A1, B1> = when (this) {
    is Right -> transform(this.value)
    is Left -> this as Either<A1, B1>
}


@Suppress("UNCHECKED_CAST", "unused")
inline fun <A, B, C> Either<A, B>.map(transform: (B) -> C): Either<A, C> = when (this) {
    is Right -> Right(transform(this.value))
    is Left -> this as Either<A, C>
}


@Suppress("UNCHECKED_CAST", "unused")
inline fun <A : A1, B, A1> Either<A, B>.filterOrElse(predicate: (B) -> Boolean, defaultValue: A1): Either<A1, B> = when (this) {
    is Right -> if (predicate(this.value)) this as Either<A1, B> else Left(defaultValue)
    is Left -> this as Either<A1, B>
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





