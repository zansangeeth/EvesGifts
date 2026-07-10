package com.sangeeth.evesgifts.data

data class SelectedGifts(
    val category: String,
    val price: String? = null,
    val quantity: Int = 1
)