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

    var selectedFrames by mutableStateOf<List<SelectedFrame>>(emptyList())
        private set

    var selectedCakes by mutableStateOf<List<SelectedCake>>(emptyList())
        private set

    var selectedGifts by mutableStateOf<List<SelectedGifts>>(emptyList())
        private set

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            loading = true

            try {
                prices = repository.fetchPrices()
            } catch (e: Exception) {
                error = e.localizedMessage
            }
            loading = false
        }
    }

    // add frames
    fun addFrame(category: String, size: String, quality: Int = 1) {
        val price = prices
            ?.frames
            ?.get(category)
            ?.get(size)

        val existingIndex = selectedFrames.indexOfFirst {
            it.category == category && it.size == size
        }

        if (existingIndex != -1) {

            selectedFrames = selectedFrames.mapIndexed { index, frame ->
                if (index == existingIndex) {
                    frame.copy(quantity = frame.quantity + quality)
                } else
                    frame
            }
        } else {
            val newFrame = SelectedFrame(
                category = category,
                size = size,
                price = price,
                quantity = quality
            )

            selectedFrames = selectedFrames.plus(newFrame)
        }
    }

    fun removeFrame(frame: SelectedFrame) {
        selectedFrames = selectedFrames.minus(frame)
    }

    //updateQuantity
    fun updateFrameQuantity(frame: SelectedFrame, newQuantity: Int) {

        if (newQuantity <= 0) {
            removeFrame(frame)
        } else {
            selectedFrames = selectedFrames.map {
                if (it == frame) it.copy(quantity = newQuantity) else it
            }
        }
    }

    fun clearSelectedFrame() {
        selectedFrames = emptyList()
    }

    //add cakes
    fun addCake(category: String, subType: String? = null, quantity: Int = 1) {
        val price = when {
            category == "birthday_cake" && subType != null -> {
                prices?.cakes?.birthday_cake?.let { cake ->
                    when (subType) {
                        "butter" -> cake.butter.price
                        "chocolate" -> cake.chocolate.price
                        else -> null
                    }
                }
            }

            else -> {
                prices?.cakes?.let { cakes ->
                    when (category) {
                        "butter_cake" -> cakes.butter_cake.price
                        "chocolate_cake" -> cakes.chocolate_cake.price
                        "butter_cup_cake" -> cakes.butter_cup_cake.price
                        "chocolate_cup_cake" -> cakes.chocolate_cup_cake.price
                        "marble_cake" -> cakes.marble_cake.price
                        "ribbon_cake" -> cakes.ribbon_cake.price
                        else -> null
                    }
                }
            }
        }

        val existingIndex = selectedCakes.indexOfFirst {
            it.category == category && it.subType == subType
        }

        if (existingIndex != -1) {
            selectedCakes = selectedCakes.mapIndexed { index, cake ->
                if (index == existingIndex) {
                    cake.copy(quantity = cake.quantity + quantity)
                } else {
                    cake
                }
            }
        } else {
            val newCake = SelectedCake(
                category = category,
                subType = subType,
                price = price,
                quantity = quantity
            )
            selectedCakes = selectedCakes.plus(newCake)
        }


    }

    fun clearCakes() {
        selectedCakes = emptyList()
    }

    fun removeCake(cake: SelectedCake) {
        selectedCakes = selectedCakes.minus(cake)
    }

    fun updateCakeQuantity(cake: SelectedCake, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeCake(cake)
        } else {
            selectedCakes = selectedCakes.map {
                if (it == cake) it.copy(quantity = newQuantity) else it
            }
        }
    }

    fun addGift(category: String, quantity: Int = 1) {
        val price = prices?.gifts?.let { gifts ->
            when (category) {
                "customized_bouquet" -> gifts.customized_bouquet.price
                "personalized_clock" -> gifts.personalized_clock.price
                "baby_photo_collage" -> gifts.baby_photo_collage.price
                "customized_chocolate" -> gifts.customized_chocolate.price
                "spotify_frame" -> gifts.spotify_frame.price
                else -> null
            }
        }

        val existingIndex = selectedGifts.indexOfFirst {
            it.category == category
        }

        if (existingIndex != -1) {
            selectedGifts = selectedGifts.mapIndexed { index, gifts ->
                if (index == existingIndex) {
                    gifts.copy(quantity = gifts.quantity + quantity)
                } else
                    gifts
            }
        } else {
            val newGift = SelectedGifts(
                category = category,
                price = price,
                quantity = quantity
            )
            selectedGifts = selectedGifts.plus(newGift)
        }
    }

    fun removeGift(gift: SelectedGifts) {
        selectedGifts = selectedGifts.minus(gift)
    }

    fun updateGiftQuantity(gifts: SelectedGifts, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeGift(gifts)
        } else {
            selectedGifts = selectedGifts.map {
                if (it == gifts) it.copy(quantity = newQuantity) else it
            }
        }
    }

    fun clearGifts() {
        selectedGifts = emptyList()
    }

    fun getTotalPrice(): Double {
        val frameTotal = selectedFrames.sumOf { (it.price?.toDouble() ?: 0.0) * it.quantity }
        val cakeTotal = selectedCakes.sumOf { (it.price?.toDouble() ?: 0.0) * it.quantity }
        val giftTotal = selectedGifts.sumOf { (it.price?.toDouble() ?: 0.0) * it.quantity }
        return frameTotal + cakeTotal + giftTotal
    }

}

