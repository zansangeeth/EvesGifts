package com.sangeeth.evesgifts.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
fun AddCakeScreen(
    onDismiss: () -> Unit,
    viewModel: PriceViewModel = viewModel(),
    onConfirm: (category: String, subType: String?) -> Unit
) {

    val cakes = viewModel.prices?.cakes
    val cakeCategories = listOf(
        "butter_cake",
        "chocolate_cake",
        "birthday_cake",
        "butter_cup_cake",
        "chocolate_cup_cake",
        "marble_cake",
        "ribbon_cake"
    )

    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var selectedSubType by remember { mutableStateOf<String?>(null) }

    val showSubType = selectedCategory == "birthday_cake"
    val subTypes = listOf("butter", "chocolate")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Add Cakes",
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
                        readOnly = true,
                        label = { Text("Cake Type") },
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
                        cakeCategories.forEach { category ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        category.replace("_", " ").replaceFirstChar {
                                            if (it.isLowerCase()) it.titlecase(locale = Locale.ROOT) else it.toString()
                                        })
                                },
                                onClick = {
                                    selectedCategory = category
                                    selectedSubType = null
                                    expandedCategory = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.heightIn(16.dp))

                if (showSubType) {
                    var expandedSubType by remember { mutableStateOf(false) }

                    ExposedDropdownMenuBox(
                        expanded = expandedSubType,
                        onExpandedChange = {
                            expandedSubType = !expandedSubType
                        }
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.menuAnchor(
                                ExposedDropdownMenuAnchorType.PrimaryEditable,
                                true
                            ),
                            value = selectedSubType?.replace("_", " ")
                                ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale = Locale.ROOT) else it.toString() }
                                ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Cake Flavor") },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                focusedIndicatorColor = colorResource(R.color.primary_color),
                                unfocusedIndicatorColor = colorResource(R.color.primary_color),
                                unfocusedLabelColor = colorResource(R.color.primary_color),
                                focusedLabelColor = colorResource(R.color.primary_color),
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandedSubType,
                            onDismissRequest = { expandedSubType = false }
                        ) {
                            subTypes.forEach { subType ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            subType.replace("_", " ").replaceFirstChar {
                                                if (it.isLowerCase()) it.titlecase(locale = Locale.ROOT) else it.toString()
                                            })
                                    },
                                    onClick = {
                                        selectedSubType = subType
                                        expandedSubType = false
                                    }
                                )
                            }
                        }
                    }
                }

                if (selectedCategory != null) {
                    val price = when {
                        selectedCategory == "birthday_cake" && selectedSubType != null -> {
                            viewModel.prices?.cakes?.birthday_cake?.let { cake ->
                                when (selectedSubType) {
                                    "butter" -> cake.butter.price
                                    "chocolate" -> cake.chocolate.price
                                    else -> null
                                }
                            }
                        }

                        else -> {
                            viewModel.prices?.cakes?.let { cakes ->
                                when (selectedCategory) {
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

                    if (price != null) {
                        Text(
                            text = "Price: Rs. $price",
                            modifier = Modifier.padding(top = 16.dp),
                            color = colorResource(R.color.primary_color)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = selectedCategory != null &&
                        (selectedCategory != "birthday_cake" || selectedSubType != null),
                onClick = {
                    onConfirm(
                        selectedCategory!!,
                        if (selectedCategory == "birthday_cake") selectedSubType else null
                    )
                    onDismiss()
                },
                colors = ButtonColors(
                    containerColor = colorResource(R.color.primary_color),
                    contentColor = Color.White,
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
                painterResource(R.drawable.ic_cakes),
                contentDescription = ""
            )
        }
    )
}