package com.example.androidtemplate.domain.old

import io.reactivex.Observable

abstract class UseCase<T>() {
    abstract fun createObservable(data: Map<String, Any>? = null): Observable<T>
}