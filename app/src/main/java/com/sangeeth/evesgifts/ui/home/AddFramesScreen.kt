package com.sangeeth.evesgifts.ui.home

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sangeeth.evesgifts.R
import com.sangeeth.evesgifts.data.PriceViewModel

@Composable
fun FramesScreen(
    onDismiss: () -> Unit,
    viewModel: PriceViewModel = viewModel()
) {
    var showDialog by remember { mutableStateOf(false) }

    val frames = viewModel.prices
    Log.i("framesdata",frames.toString())
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Add Frames",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            TextButton(onClick = { /* TODO */ }) {
                Text("Delete it".uppercase())
            }
        },
        dismissButton = {
            TextButton(onClick = { showDialog = false }) {
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
