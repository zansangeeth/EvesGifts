package com.sangeeth.evesgifts.data

import retrofit2.http.GET

interface PriceAPI {
    @GET("eve-gifts-prices/price.json")
    suspend fun getPrices(): PriceResponse
}