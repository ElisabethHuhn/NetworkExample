package org.elisabethhuhn.networkexample.model

import kotlinx.serialization.Serializable

    /*
    * Customer example
    *
    */

@Serializable
data class Customer(
    val id: Int,
    val firstName: String,
    val lastName: String
) {
    val customerStorage = mutableListOf<Customer>()
    init {
        customerStorage.addAll(
            arrayOf(
                Customer(1, "Jane", "Smith"),
                Customer(2, "John", "Smith")
            )
        )
    }
}
