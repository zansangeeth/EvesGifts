package com.sangeeth.evesgifts.ui.home

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
import com.sangeeth.evesgifts.R

@Composable
fun CakesScreen(onDismiss: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest =  onDismiss,
        title = { Text("Add Cakes", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
        confirmButton = {
            TextButton(onClick = { /* TODO */}) {
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
                painterResource(R.drawable.ic_cakes),
                contentDescription = ""
            )
        }
    )
}