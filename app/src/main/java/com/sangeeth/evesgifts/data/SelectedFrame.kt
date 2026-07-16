package com.sangeeth.evesgifts.data

import java.util.UUID

data class SelectedFrame(
    var category: String = "",
    var size: String = "",
    var price: Int? = null,
    var quantity: Int = 1,
    val id: String = UUID.randomUUID().toString()
)
