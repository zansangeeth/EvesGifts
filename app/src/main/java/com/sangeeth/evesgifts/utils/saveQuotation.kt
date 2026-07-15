package com.sangeeth.evesgifts.utils

import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.sangeeth.evesgifts.data.SelectedCake
import com.sangeeth.evesgifts.data.SelectedFrame
import com.sangeeth.evesgifts.data.SelectedGifts
import kotlin.collections.hashMapOf
import kotlin.collections.map

fun saveQuotation(
    quotationId: String,
    customerName: String,
    frames: List<SelectedFrame>,
    cakes: List<SelectedCake>,
    gifts: List<SelectedGifts>,
    totalPrice: Double,
    onComplete: () -> Unit
) {

    val db = Firebase.firestore

    val quotation = hashMapOf(
        "quotationId" to quotationId,
        "customerName" to customerName,
        "totalPrice" to totalPrice,
//        "pdfUrl" to pdfUrl,

        "frames" to frames.map {
            hashMapOf(
                "category" to it.category,
                "size" to it.size,
                "quantity" to it.quantity,
                "price" to it.price,
            )
        },

        "cakes" to cakes.map {
            hashMapOf(
                "category" to it.category,
                "subType" to it.subType,
                "quantity" to it.quantity,
                "price" to it.price,
            )
        },

        "gifts" to gifts.map {
            hashMapOf(
                "category" to it.category,
                "quantity" to it.quantity,
                "price" to it.price,
            )
        },

        "createdAt" to FieldValue.serverTimestamp(),

    )

    db.collection("quotations")
        .document(quotationId)
        .set(quotation)
        .addOnSuccessListener {
            onComplete()
        }
        .addOnFailureListener { error->
            error.printStackTrace()
            onComplete()
        }

}