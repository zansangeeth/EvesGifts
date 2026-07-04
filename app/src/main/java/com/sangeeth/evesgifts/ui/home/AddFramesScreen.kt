package com.sangeeth.evesgifts.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sangeeth.evesgifts.R
import com.sangeeth.evesgifts.data.PriceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FramesScreen(
    onDismiss: () -> Unit,
    viewModel: PriceViewModel = viewModel(),
    onConfirm: (category: String, size: String) -> Unit
) {
//    var showDialog by remember { mutableStateOf(false) }

    val frames = viewModel.prices?.frames
    val framesCategory = frames?.keys?.toList()

    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var selectedSize by remember { mutableStateOf<String?>(null) }

    var sizes = remember(selectedCategory) {
        selectedCategory?.let {
            frames?.get(it)?.keys?.toList() ?: emptyList()
        } ?: emptyList()
    }
//    val glassFrameSizes = viewModel.prices?.frames["glass"]?.keys
//    Log.i("frames",framesCategory.toString())
//    Log.i("glassframes", glassFrameSizes.toString())
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Add Frames",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column {

                var expandedCategory by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expandedCategory,
                    onExpandedChange = {
                        expandedCategory = !expandedCategory
                    }
                ) {
                    OutlinedTextField(
                        modifier = Modifier.menuAnchor(),
                        value = selectedCategory ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = {Text("Category")},
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expandedCategory)
                        }
                    )
                    ExposedDropdownMenu(
                        expanded = expandedCategory,
                        onDismissRequest = {
                            expandedCategory = false
                        }
                    ) {
                        framesCategory?.forEach { category->

                            DropdownMenuItem(
                                text = {Text(category)},
                                onClick = {
                                    selectedCategory = category
                                    selectedSize = null
                                    expandedCategory = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.heightIn(16.dp))

                var expandedSize by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expandedSize,
                    onExpandedChange = {
                        if (selectedCategory != null) {
                            expandedSize = !expandedSize
                        }
                    }
                ) {
                    OutlinedTextField(
                        modifier = Modifier.menuAnchor(),
                        value = selectedSize ?: "",
                        onValueChange = {},
                        readOnly = true,
                        enabled = selectedCategory != null,
                        label = {Text("Size")},
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expandedSize)
                        }
                    )
                    ExposedDropdownMenu(
                        expanded = expandedSize,
                        onDismissRequest = {
                            expandedSize = false
                        }
                    ) {
                        sizes.forEach { size->

                            DropdownMenuItem(
                                text = {Text(size)},
                                onClick = {
                                    selectedSize = size
                                    expandedSize = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = selectedCategory != null && selectedSize!= null,
                onClick = {
                    onConfirm(
                        selectedCategory!!,
                        selectedSize!!
                    )
                    onDismiss()
                }
            ) {
                Text("Add".uppercase())
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss ) {
                Text("Cancel".uppercase())
            }
        },
        icon = {
            Icon(
                painterResource(R.drawable.ic_frames),
                contentDescription = ""
            )
        }
    )


}
