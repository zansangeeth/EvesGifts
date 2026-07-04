package com.sangeeth.evesgifts.data

class PriceRepository(
    private val api: PriceAPI
) {
    suspend fun fetchPrices(): PriceResponse{
        return api.getPrices()
    }
}
