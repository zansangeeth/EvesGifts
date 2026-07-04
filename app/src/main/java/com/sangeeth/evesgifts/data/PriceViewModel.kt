package com.sangeeth.evesgifts.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PriceViewModel : ViewModel() {
    private val repository = PriceRepository(RetrofitClient.api)

    var prices by mutableStateOf<PriceResponse?>(null)
        private set

    var loading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    init {
        loadData()
    }
    fun loadData() {
        viewModelScope.launch {
            loading = true

            try {
                prices = repository.fetchPrices()
            }catch (e: Exception){
                error = e.localizedMessage
            }
            loading = false
        }

    }
}
