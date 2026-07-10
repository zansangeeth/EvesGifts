package com.sangeeth.evesgifts.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sangeeth.evesgifts.R
import com.sangeeth.evesgifts.data.PriceViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GiftsScreen(
    onDismiss: () -> Unit,
    viewModel: PriceViewModel = viewModel(),
    onConfirm: (category: String) -> Unit
) {

    val giftCategory = listOf(
        "customized_bouquet",
        "personalized_clock",
        "baby_photo_collage",
        "customized_chocolate",
        "spotify_frame"
    )

    var selectedCategory by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Add Gifts",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        titleContentColor = colorResource(R.color.primary_color),
        iconContentColor = colorResource(R.color.primary_color),
        text = {
            Column {
                var expandedCategory by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expandedCategory,
                    onExpandedChange = { expandedCategory = !expandedCategory }
                ) {
                    OutlinedTextField(
                        modifier = Modifier.menuAnchor(
                            ExposedDropdownMenuAnchorType.PrimaryEditable,
                            true
                        ),
                        value = selectedCategory?.replace("_", " ")
                            ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale = Locale.ROOT) else it.toString() }
                            ?: "",
                        onValueChange = {},
                        label = { Text("Gift Type") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expandedCategory)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                            focusedIndicatorColor = colorResource(R.color.primary_color),
                            unfocusedIndicatorColor = colorResource(R.color.primary_color),
                            unfocusedLabelColor = colorResource(R.color.primary_color),
                            focusedLabelColor = colorResource(R.color.primary_color),
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandedCategory,
                        onDismissRequest = { expandedCategory = false }
                    ) {
                        giftCategory.forEach { category ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        category.replace("_", " ").replaceFirstChar {
                                            if (it.isLowerCase()) it.titlecase(locale = Locale.ROOT) else it.toString()
                                        })
                                },
                                onClick = {
                                    selectedCategory = category
                                    expandedCategory = false
                                }
                            )

                        }
                    }
                }

                //show prices
                if (selectedCategory != null) {
                    val price = viewModel.prices?.gifts?.let { gifts ->
                        when (selectedCategory) {
                            "customized_bouquet" -> gifts.customized_bouquet.price
                            "personalized_clock" -> gifts.personalized_clock.price
                            "baby_photo_collage" -> gifts.baby_photo_collage.price
                            "customized_chocolate" -> gifts.customized_chocolate.price
                            "spotify_frame" -> gifts.spotify_frame.price
                            else -> null
                        }
                    }

                    if (price != null) {
                        Text(
                            text = "price is $price",
                            modifier = Modifier.padding(16.dp),
                            color = colorResource(R.color.primary_color)
                        )
                    }
                }
            }
        },
        confirmButton = {

            TextButton(
                enabled = selectedCategory != null,
                onClick = {
                    onConfirm(selectedCategory!!)
                    onDismiss()
                },
                colors = ButtonColors(
                    contentColor = Color.White,
                    containerColor = colorResource(R.color.primary_color),
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White
                )
            ) {
                Text("Add".uppercase())
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel".uppercase(), color = colorResource(R.color.primary_color))
            }
        },
        icon = {
            Icon(
                painter = painterResource(R.drawable.ic_gifts),
                contentDescription = ""
            )
        }
    )

}