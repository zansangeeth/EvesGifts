package com.sangeeth.evesgifts.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sangeeth.evesgifts.R


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }
    val itemPosition = remember {
        mutableStateOf("Frames")
    }
    val usernames = listOf("Frames", "Cakes", "Gifts")
    Scaffold(
        floatingActionButton = { FloatingActionButton() }
    ){
        Column(modifier = Modifier.padding(20.dp).padding(it)) {
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
                state = rememberTextFieldState(),
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


            Column(modifier = Modifier.padding(top = 20.dp)) {
                ExposedDropdownMenuBox(
                    expanded = isDropDownExpanded.value,
                    onExpandedChange = { isDropDownExpanded.value = !isDropDownExpanded.value }
                ) {

                    OutlinedTextField(
                        value = itemPosition.value,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropDownExpanded.value)
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = colorResource(R.color.primary_color),
                            unfocusedIndicatorColor = colorResource(R.color.primary_color),
                            cursorColor = colorResource(R.color.primary_color),
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                    )

                    ExposedDropdownMenu(
                        expanded = isDropDownExpanded.value,
                        onDismissRequest = { isDropDownExpanded.value = false },
                    ) {
                        usernames.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(option, color = MaterialTheme.colorScheme.onSurface)
                                },
                                onClick = {
                                    itemPosition.value = option
                                    isDropDownExpanded.value = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
            }
        }
    }
}

