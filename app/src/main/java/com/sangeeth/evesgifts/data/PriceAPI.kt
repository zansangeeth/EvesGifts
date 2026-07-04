package com.sangeeth.evesgifts.data

import retrofit2.http.GET

interface PriceAPI {
    @GET("eve-gifts-prices/prices.json")
    suspend fun getPrices(): PriceResponse
}