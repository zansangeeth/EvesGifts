package com.sangeeth.evesgifts.data

import com.google.firebase.Timestamp

data class Quotation(
    val quotationId: String = "",
    val customerName: String = "",
    val totalPrice: Double = 0.0,
    val frames: List<SelectedFrame> = emptyList(),
    val cakes: List<SelectedCake> = emptyList(),
    val gifts: List<SelectedGifts> = emptyList(),
    val createdAt: Timestamp? = null
)

