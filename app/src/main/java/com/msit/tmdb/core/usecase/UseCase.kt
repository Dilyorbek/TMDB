package com.msit.tmdb.core.usecase

import com.msit.tmdb.core.data.remote.ApiError
import com.msit.tmdb.core.util.Either
import com.msit.tmdb.features.numberTrivia.domain.entity.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber

abstract class UseCase<in P, R> {

    /** Executes the use case asynchronously and returns a [Result].
     *
     * @return a [Result].
     *
     * @param parameters the input parameters to run the use case with
     */
    abstract operator fun invoke(parameters: P): Flow<Either<Exception, R>>

}