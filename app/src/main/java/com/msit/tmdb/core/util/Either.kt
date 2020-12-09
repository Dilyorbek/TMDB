package com.msit.tmdb.core.util

sealed class Either<out L, out R>

data class Left<out L>(val l: L) : Either<L, Nothing>()

data class Right<out R>(val r: R) : Either<Nothing, R>()

inline fun <L, R1, R2> Either<L, R1>.map(f: (R1) -> R2): Either<L, R2> =
    when (this) {
        is Right -> Right(f(this.r))
        is Left -> this
    }

inline fun <L, R1, R2> Either<L, R1>.flatMap(f: (R1) -> Either<L, R2>): Either<L, R2> =
    when (this) {
        is Right -> f(this.r)
        is Left -> this
    }

inline fun <L, R, T> Either<L, R>.fold(fl: (L) -> T, fr: (R) -> T): T =
    when (this) {
        is Right -> fr(this.r)
        is Left -> fl(this.l)
    }

inline fun <R> resultOf(f: () -> R): Either<Exception, R> =
    try {
        Right(f())
    } catch (e: Exception) {
        Left(e)
    }
