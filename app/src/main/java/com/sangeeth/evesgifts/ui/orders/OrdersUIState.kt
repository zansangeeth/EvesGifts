package com.sangeeth.evesgifts.ui.orders

import android.os.Message
import com.sangeeth.evesgifts.data.Quotation

sealed class OrdersUIState {
    object Loading : OrdersUIState()
    data class Success(val quotations: List<Quotation>): OrdersUIState()
    data class Error(val message: Message): OrdersUIState()
    object Empty: OrdersUIState()
}