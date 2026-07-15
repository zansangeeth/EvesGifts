package com.sangeeth.evesgifts.ui.home

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sangeeth.evesgifts.R
import com.sangeeth.evesgifts.data.PriceViewModel
import com.sangeeth.evesgifts.utils.pdfGenerator
import com.sangeeth.evesgifts.utils.saveQuotation
import com.sangeeth.evesgifts.utils.uploadQuotationPdf
import java.util.Locale
import kotlin.text.replace


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: PriceViewModel
) {
    val selectedFrames = viewModel.selectedFrames
    val selectedCake = viewModel.selectedCakes
    val selectedGifts = viewModel.selectedGifts

    var isGenerating by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val customerName = rememberTextFieldState()
    val quotationId = "Q-${System.currentTimeMillis()}"

    val hasCustomerName = customerName.text.isNotBlank()
    val hasItems =
        selectedFrames.isNotEmpty() || selectedGifts.isNotEmpty() || selectedCake.isNotEmpty()

    val savePdf = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/pdf")
    ) { uri: Uri? ->
        uri?.let {
            pdfGenerator(
                context = context,
                uri = it,
                quotationId = "Q-${System.currentTimeMillis()}",
                customerName = customerName.text.toString(),
                frames = selectedFrames,
                cakes = selectedCake,
                gifts = selectedGifts,
                totalPrice = viewModel.getTotalPrice()
            )

//            uploadQuotationPdf(
//                pdfByte = pdfBytes,
//                quotationId = quotationId,
//                onComplete = { pdfUrl ->
            saveQuotation(
                quotationId = quotationId,
                customerName = customerName.text.toString(),
                frames = selectedFrames,
                cakes = selectedCake,
                gifts = selectedGifts,
                totalPrice = viewModel.getTotalPrice()
//                        pdfUrl = pdfUrl
            ) {
                isGenerating = false
            }
//                },
//                onError = {
//                    it.printStackTrace()
//                    isGenerating = false
//                }
//            )
        }
    }


    Scaffold(
//        floatingActionButton = { FloatingActionButton(viewModel) }
        floatingActionButton = {
            FloatingActionButton(
                viewModel = viewModel,
                modifier = Modifier.padding(bottom = 70.dp)
            )
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)

        ) {
            Text(
                text = "Create Quotation",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Create new quote",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 2.dp, top = 5.dp),
                textAlign = TextAlign.Left,

                )

            OutlinedTextField(
                state = customerName,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                lineLimits = TextFieldLineLimits.SingleLine,
                label = { Text("Customer Name", color = colorResource(R.color.primary_color)) },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = colorResource(R.color.primary_color),
                    unfocusedIndicatorColor = colorResource(R.color.primary_color),
                    cursorColor = colorResource(R.color.primary_color),
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                //                Text("Frames Details")

                //                if (viewModel.selectedFrames.isEmpty() && viewModel.selectedCakes.isEmpty() && viewModel.selectedGifts.isEmpty()) {
                //                    Text("no items selected yet")
                //                } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(top = 8.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    if (viewModel.selectedFrames.isEmpty() && viewModel.selectedCakes.isEmpty() && viewModel.selectedGifts.isEmpty()) {
                        Text("no items selected yet")
                    } else {
                        selectedFrames.forEach { frame ->
                            AddedItemCardView(
                                item = frame.category,
                                size = frame.size,
                                price = frame.price?.toString() ?: "N/A",
                                onDelete = {
                                    viewModel.removeFrame(frame)
                                },
                                quantity = frame.quantity,
                                onQuantityChange = { newQuantity ->
                                    viewModel.updateFrameQuantity(
                                        frame = frame,
                                        newQuantity = newQuantity
                                    )

                                }
                            )
                            //                            Text(
                            //                                text = "id: ${index+1}. category ${frame.category}, size: ${frame.size}, price: ${frame.price?.toString() ?: "N/A"}",
                            //                                modifier = Modifier.padding(4.dp)
                            //                            )
                        }

                        selectedCake.forEach { cake ->
                            AddedItemCardView(
                                item = cake.category.replace("_", " ")
                                    ?.replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(
                                            locale = Locale.ROOT
                                        ) else it.toString()
                                    }
                                    ?: "",
                                size = cake.subType?.replace("_", " ")
                                    ?.replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(
                                            locale = Locale.ROOT
                                        ) else it.toString()
                                    }
                                    ?: "Standard",
                                price = cake.price.toString(),
                                quantity = cake.quantity,
                                onQuantityChange = { newQuantity ->
                                    viewModel.updateCakeQuantity(
                                        cake = cake,
                                        newQuantity = newQuantity
                                    )
                                },
                                onDelete = {
                                    viewModel.removeCake(cake = cake)
                                }
                            )
                        }

                        selectedGifts.forEach { gifts ->
                            AddedItemCardView(
                                item = gifts.category.replace("_", " ")
                                    .replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(
                                            locale = Locale.ROOT
                                        ) else it.toString()
                                    }
                                    ?: "",
                                size = "Gift",
                                price = gifts.price ?: "N/A",
                                quantity = gifts.quantity,
                                onQuantityChange = { newQuantity ->
                                    viewModel.updateGiftQuantity(gifts, newQuantity)
                                },
                                onDelete = {
                                    viewModel.removeGift(gifts)
                                }
                            )
                        }


                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp)
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total: Rs${
                                String.format(
                                    "%.2f",
                                    viewModel.getTotalPrice()
                                )
                            }",
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(top = 8.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
//                    Spacer(modifier = Modifier.height(12.dp))
                    GenerateQuoteButton(
                        hasItems = hasItems && hasCustomerName,
                        loading = isGenerating,
                        onClick = {
                            isGenerating = true
                            savePdf.launch("Quotation_$quotationId.pdf")
                        },

                        )
                }
            }
        }
    }
}

