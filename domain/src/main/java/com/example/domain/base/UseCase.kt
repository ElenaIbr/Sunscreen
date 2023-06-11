package com.example.domain.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface SingleUseCaseInterface<in Input, out Output> {
    suspend fun action(input: Input): Output
    suspend fun execute(input: Input): Output
    fun executeWithCallback(
        input: Input,
        onSuccess: ((Output) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    )

    fun cancel()
}

abstract class SingleUseCase<in Input, out Output>(
    private val dispatcher: CoroutineDispatcher
) : SingleUseCaseInterface<Input, Output> {
    private var job: Job? = null

    override suspend fun execute(input: Input): Output {
        return withContext(SupervisorJob() + dispatcher) {
            action(input)
        }
    }

    override fun executeWithCallback(
        input: Input,
        onSuccess: ((Output) -> Unit)?,
        onError: ((Throwable) -> Unit)?
    ) {
        val handler = CoroutineExceptionHandler { _, exception ->
            onError?.invoke(exception)
        }
        job = CoroutineScope(context = SupervisorJob()).launch(handler) {
            val deferred = async(SupervisorJob() + dispatcher) {
                action(input)
            }
            onSuccess?.invoke(deferred.await())
        }
    }

    override fun cancel() {
        job?.cancel()
    }
}

interface FlowUseCaseInterface<in Input, out Output> {
    fun action(input: Input): Flow<Output>
    fun execute(input: Input): Flow<Output>
}

abstract class FlowUseCase<in Input, out Output>(
    private val dispatcher: CoroutineDispatcher
) : FlowUseCaseInterface<Input, Output> {
    override fun execute(input: Input): Flow<Output> {
        return action(input).flowOn(dispatcher)
    }
}
