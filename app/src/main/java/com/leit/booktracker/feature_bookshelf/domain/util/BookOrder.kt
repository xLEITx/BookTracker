package com.leit.booktracker.feature_bookshelf.domain.util

sealed class BookOrder(val orderType: OrderType){
    class Status(orderType: OrderType):BookOrder(orderType)
    class Title(orderType: OrderType):BookOrder(orderType)
    class Author(orderType: OrderType):BookOrder(orderType)
    class Type(orderType: OrderType):BookOrder(orderType)

    fun copy(orderType: OrderType): BookOrder{
        return when(this){
            is Status -> Status(orderType)
            is Title -> Title(orderType)
            is Author -> Author(orderType)
            is Type -> Type(orderType)
        }
    }

}
