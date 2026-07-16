package com.sangeeth.evesgifts.ui.orders

import android.view.View
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.sangeeth.evesgifts.data.Quotation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OrdersViewModel : ViewModel() {

    private val db = Firebase.firestore

    private val _uiState = MutableStateFlow<OrdersUIState>(OrdersUIState.Loading)
    val uiState: StateFlow<OrdersUIState> = _uiState.asStateFlow()

    init {
        loadQuotations()
    }

    private fun loadQuotations() {
        viewModelScope.launch {
            _uiState.value = OrdersUIState.Loading
            try {
                val snapshot = db.collection("quotations")
                    .orderBy("createdAt", Query.Direction.DESCENDING)
                    .get()
                    .await()

                val quotationList = snapshot.documents.mapNotNull { documentSnapshot ->
                    val quotation = documentSnapshot.toObject<Quotation>()
                    quotation?.copy(quotationId = documentSnapshot.id)

                }

                _uiState.value = if (quotationList.isEmpty()) {
                    OrdersUIState.Empty
                } else{
                    OrdersUIState.Success(quotationList)
                }
            } catch (e: Exception){
                _uiState.value = OrdersUIState.Error(e.message ?: "Failed to load orders")
                e.printStackTrace()
            }
        }
    }
    fun retry(){
        loadQuotations()
    }
}