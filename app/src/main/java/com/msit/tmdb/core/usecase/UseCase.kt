package com.msit.tmdb.core.usecase

import com.msit.tmdb.core.util.Either
import kotlinx.coroutines.flow.Flow

abstract class UseCase<in P, R> {

    /** Executes the use case asynchronously and returns a [Result].
     *
     * @return a [Result].
     *
     * @param parameters the input parameters to run the use case with
     */
    abstract operator fun invoke(parameters: P): Flow<Either<Exception, R>>

}