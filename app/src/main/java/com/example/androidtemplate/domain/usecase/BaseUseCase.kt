package com.example.androidtemplate.domain.usecase

import com.example.androidtemplate.helper.Result
import com.example.androidtemplate.helper.Error

abstract class BaseUseCase<T>() {
    abstract suspend fun create(data: Map<String, Any>? = null):  Result<T, Error>
}