@file:OptIn(ExperimentalMaterial3Api::class)

package com.develex.baseapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.develex.baseapp.MainViewModel
import com.develex.baseapp.components.CustomDatePicker
import com.develex.baseapp.components.CustomTimePicker
import com.develex.baseapp.components.TimePickerDialog
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentsScreen(navController: NavController, vm: MainViewModel, avm: AppointmentViewModel) {
//    Variables for keeping track of Appointment values
    var appointmentName by rememberSaveable { mutableStateOf("") }
    val appointmentDate = remember { mutableStateOf(LocalDate.now()) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Afspraken",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(top = 20.dp)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Afspraak naam",
                    style = MaterialTheme.typography.bodyLarge
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = appointmentName,
                    onValueChange = { appointmentName = it }
                )
                Text(
                    text = "Afspraak Datum",
                    style = MaterialTheme.typography.bodyLarge
                )
                CustomDatePicker(modifier = Modifier)
                Text(
                    text = "Afspraak Tijd",
                    style = MaterialTheme.typography.bodyLarge
                )
                CustomTimePicker()
            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 2.dp
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Alarm 1",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Row {
                    Column(
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        Text(
                            text = "Datum",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        CustomDatePicker(modifier = Modifier)
                        Text(
                            text = "Tijd",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        CustomTimePicker()
                    }
                    FilledIconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .width(100.dp)
                            .height(160.dp)
                            .padding(start = 5.dp, top = 20.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Icon(
                            Icons.TwoTone.Delete,
                            contentDescription = "Verwijderen",
                        )
                    }
                }
            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 2.dp
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Alarm 2",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Row {
                    Column(
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        Text(
                            text = "Datum",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        CustomDatePicker(modifier = Modifier)
                        Text(
                            text = "Tijd",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        CustomTimePicker()
                    }
                    FilledIconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .width(100.dp)
                            .height(160.dp)
                            .padding(start = 5.dp, top = 20.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Icon(
                            Icons.TwoTone.Delete,
                            contentDescription = "Verwijderen",
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleAppointmentsScreenPreview() {
    AppointmentsScreen(
        navController = rememberNavController(),
        vm = MainViewModel(),
        avm = AppointmentViewModel()
    )
}

//@Preview(showBackground = true)
//@Composable
//fun SimpleAppointmentCardPreview() {
//    AppointmentCard(
//        title = "First Appointment",
//        date = "21/03/1999",
//        time = "21:32",
//        alarm1date = "21/03/1999",
//        alarm1time = "21:32",
//        alarm2date = "21/03/1999",
//        alarm2time = "21:32"
//    )
//}

