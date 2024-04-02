@file:OptIn(ExperimentalMaterial3Api::class)

package com.develex.baseapp.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    onAccept: (Long?) -> Unit,
    onCancel: () -> Unit,
    datePickerState: DatePickerState = rememberDatePickerState()
) {

    DatePickerDialog(
        onDismissRequest = {},
        confirmButton = {
            Button(onClick = { onAccept(datePickerState.selectedDateMillis) }) {
                Text(text = "Accepteren")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text(text = "Annuleren")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
fun CustomDatePicker(
    modifier: Modifier, label: String? = "",
    datePickerState: DatePickerState = rememberDatePickerState(),
    enabled: MutableState<Boolean> = remember {
        mutableStateOf(true)
    },
    startDate: LocalDate = LocalDate.now()
) {
    val date = remember { mutableStateOf(startDate) }
    val isOpen = remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {
//        Log.d("CustomDatePicker", date.value.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
//        Log.d("CustomDatePicker", date.value.toString())
        Log.d(
            "CustomDatePicker1",
            datePickerState.selectedDateMillis!!.convertMillisToDate()
        )
        TextField(
            readOnly = true,
            value = date.value.format(DateTimeFormatter.ofPattern("dd-MM-yyy")),
            onValueChange = {},
            label = {
                if (label != null) {
                    Text(text = label)
                } else {
                    Text(text = "")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = {
                        if (enabled.value) {
                            isOpen.value = true
                        }
                    })
                .then(modifier),
            enabled = false,
            colors = TextFieldDefaults.colors(
                disabledTextColor =
                if (enabled.value) {
                    MaterialTheme.colorScheme.onBackground
                } else {
                    TextFieldDefaults.colors().disabledTextColor
                }
            )
        )
    }

    if (isOpen.value) {
        CustomDatePickerDialog(
            datePickerState = datePickerState,
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

fun Long.convertMillisToDate(): String {
    // Create a calendar instance in the default time zone
    val calendar = Calendar.getInstance().apply {
        timeInMillis = this@convertMillisToDate
        // Adjust for the time zone offset to get the correct local date
        val zoneOffset = get(Calendar.ZONE_OFFSET)
        val dstOffset = get(Calendar.DST_OFFSET)
        add(Calendar.MILLISECOND, -(zoneOffset + dstOffset))
    }
    // Format the calendar time in the specified format
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return sdf.format(calendar.time)
}