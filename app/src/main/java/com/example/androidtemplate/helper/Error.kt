package com.example.androidtemplate.helper

sealed class Error {

    class NetworkError() : Error()
    class GenericError() : Error()
    class ResponseError() : Error()
    class EmptyBodyError(): Error()
    class RandomError(val cause: Throwable?): Error()
}
