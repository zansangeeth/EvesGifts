package com.sangeeth.evesgifts.ui.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sangeeth.evesgifts.R
import com.sangeeth.evesgifts.data.PriceViewModel

@Composable
fun FloatingActionButton(
    viewModel: PriceViewModel
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<String?>(null) }
    var showCakesDialog by remember { mutableStateOf(false) }

    val items = listOf(
        FabItems(icon = R.drawable.ic_frames, title = "Frames"),
        FabItems(icon = R.drawable.ic_cakes, title = "Cakes"),
        FabItems(icon = R.drawable.ic_gifts, title = "Gifts"),
    )
    Column(horizontalAlignment = Alignment.End) {

        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }) + expandVertically(),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }) + shrinkVertically(),
        ) {

            LazyColumn(modifier = Modifier.padding(8.dp)) {
                items(items.size) {

                    itemUI(
                        icon = items[it].icon,
                        title = items[it].title,
                        onClick = { title ->
                            expanded = false
                            selectedItem = title
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

        }

        val transition = updateTransition(targetState = expanded, label = "transition")
        val rotation by transition.animateFloat(label = "rotation") {
            if (it) 315f else 0f
        }

        FloatingActionButton(
            onClick = {
                expanded = !expanded
            },
            modifier = Modifier.rotate(rotation),
            containerColor = colorResource(R.color.primary_color)

        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "",
                tint = Color.White
            )
        }
    }

    when (selectedItem) {
        "Frames" -> {
            FramesScreen(
                viewModel = viewModel,
                onDismiss = { selectedItem = null },
                onConfirm = { category, size ->
                    viewModel.addFrame(category, size)
                    val price = viewModel.prices
                        ?.frames
                        ?.get(category)
                        ?.get(size)
                    Log.d("selected frame", "$category $size $price")
                }
            )
        }

        "Cakes" -> {
            AddCakeScreen(
                onDismiss = { selectedItem = null },
                onConfirm = { category, subType ->
                    viewModel.addCake(category, subType)
                    selectedItem = null
                }
            )
        }

        "Gifts" -> {
            GiftsScreen(
                onDismiss = { selectedItem = null },
                onConfirm = { category ->
                    viewModel.addGift(category)
                }
            )
        }
    }
}

@Composable
fun itemUI(
    icon: Int,
    title: String,
    onClick: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = colorResource(R.color.primary_color),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(6.dp)
        ) {
            Text(text = title)
        }
        Spacer(modifier = Modifier.width(10.dp))
        FloatingActionButton(
            onClick = {
                onClick(title)
            },
            modifier = Modifier.size(45.dp),
            containerColor = colorResource(R.color.primary_color)
        ) {
            Icon(painter = painterResource(icon), contentDescription = "", tint = Color.White)
        }
    }
}

data class FabItems(
    val icon: Int,
    val title: String
)
