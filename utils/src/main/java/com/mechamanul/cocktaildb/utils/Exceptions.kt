package com.mechamanul.cocktaildb.utils

open class AppException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}

class JsonDeserializationError(message: String) : AppException(message)

class ConnectionException(cause: Throwable) : AppException(cause)
