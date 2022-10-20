package com.mechamanul.cocktaildb.domain.common

open class AppException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}

class JsonDeserializationException(message: String) : AppException(message)

class ConnectionException(cause: Throwable) : AppException(cause)

class EmptyRetrofitResultException(message: String) : AppException(message)


