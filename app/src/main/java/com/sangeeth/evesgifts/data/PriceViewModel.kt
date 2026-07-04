package com.sangeeth.evesgifts.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PriceViewModel : ViewModel() {
    private val repository = PriceRepository(RetrofitClient.api)

    var state = mutableStateOf<PriceResponse?>(null)
        private set

    var loading = mutableStateOf(false)
        private set

    var error = mutableStateOf<String?>(null)
        private set

    fun loadData() {
        viewModelScope.launch {
            loading.value = true
            error.value = null

            try {
                state.value = repository.fetchPrices()
            }catch (e: Exception){
                error.value = e.message
            }
            loading.value = false
        }

    }
}
