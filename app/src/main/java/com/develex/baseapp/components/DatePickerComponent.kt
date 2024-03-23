@file:OptIn(ExperimentalMaterial3Api::class)

package com.develex.baseapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun CustomDatePickerDialog(
    onAccept: (Long?) -> Unit,
    onCancel: () -> Unit
) {
    val state = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = {},
        confirmButton = {
            Button(onClick = { onAccept(state.selectedDateMillis) }) {
                Text(text = "Accepteren")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text(text = "Annuleren")
            }
        }
    ) {
        DatePicker(state = state)
    }
}

@Composable
fun CustomDatePicker() {
    val date = remember { mutableStateOf(LocalDate.now()) }
    val isOpen = remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {

        TextField(
            readOnly = true,
            value = date.value.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = {
                        isOpen.value = true
                    }),
            enabled = false,
            colors = TextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onBackground
            )
        )
    }

    if (isOpen.value) {
        CustomDatePickerDialog(
            onAccept = {
//                Close Dialog
                isOpen.value = false
//                Set the date
                if (it != null) {
                    date.value = Instant
                        .ofEpochMilli(it)
                        .atZone(ZoneId.of("UTC"))
                        .toLocalDate()
                }
            },
            onCancel = {
//                Close Dialog
                isOpen.value = false
            }
        )
    }
}