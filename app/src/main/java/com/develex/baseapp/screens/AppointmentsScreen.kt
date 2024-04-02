@file:OptIn(ExperimentalMaterial3Api::class)

package com.develex.baseapp.screens

import android.util.Log
import androidx.collection.emptyObjectList
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Close
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.develex.baseapp.MainViewModel
import com.develex.baseapp.R
import com.develex.baseapp.components.CustomDatePicker
import com.develex.baseapp.components.CustomTimePicker
import com.develex.baseapp.data.Appointment
import com.develex.baseapp.navigation.Screens
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentsScreen(
    navController: NavController,
    vm: MainViewModel,
    appointmentViewModel: AppointmentViewModel,
) {
//    TAG for debuggging
    val TAG = "AppointmentScreen()"

    //    Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
//    CoroutineScope
    val scope = rememberCoroutineScope()

    val appointmentUpdated = remember {
        mutableStateOf(false)
    }

//    get appointmentId from viewmodel and keep as state
    val vmAppointmentId by vm.currentAppointment.collectAsState(initial = 0)

////    get appointment with appointmentId
    val appointment: State<Appointment> =
        appointmentViewModel.getAppointment(vmAppointmentId)
            .collectAsState(
                initial = Appointment(
                    name = "",
                    dateTime = System.currentTimeMillis(),
                    alarm1DateTime = System.currentTimeMillis(),
                    alarm2DateTime = System.currentTimeMillis(),
                    alarm2Toggle = false,
                    alarm1Toggle = false
                )
            )
//    appointmentUpdated.value = !appointmentUpdated.value

    val appointmentId = appointmentViewModel.appointmentId.collectAsState()
    val appointmentName = appointmentViewModel.appointmentName.collectAsState()
    val appointmentDateTime = appointmentViewModel.appointmentDateTime.collectAsState()
    val appointmentAlarm1DateTime = appointmentViewModel.appointmentAlarm1DateTime.collectAsState()
    val appointmentAlarm2DateTime = appointmentViewModel.appointmentAlarm2DateTime.collectAsState()
    val appointmentAlarm1Toggle = appointmentViewModel.appointmentAlarm1Toggle.collectAsState()
    val appointmentAlarm2Toggle = appointmentViewModel.appointmentAlarm2Toggle.collectAsState()
    Log.d(TAG, "appointmentDateTime: ${appointmentDateTime.value}")

    if (appointment.value != null) {
        appointmentViewModel.setId(vmAppointmentId)
        appointmentViewModel.setName(appointment.value.name)
        appointmentViewModel.setDateTime(appointment.value.dateTime)
        appointmentViewModel.setAlarm1DateTime(appointment.value.alarm1DateTime)
        appointmentViewModel.setAlarm2DateTime(appointment.value.alarm2DateTime)
        appointmentViewModel.setAlarm1Toggle(appointment.value.alarm1Toggle)
        appointmentViewModel.setAlarm2Toggle(appointment.value.alarm2Toggle)
        Log.d(TAG, "appointment.value.dateTime: ${appointment.value.dateTime}")
    }

//    Appointment Date & Time
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = appointmentDateTime.value * 1000
    )
    val timepickerState: MutableState<LocalTime> =
        rememberSaveable {
            mutableStateOf(
                Instant.ofEpochMilli(appointmentDateTime.value * 1000)
                    .atZone(ZoneId.systemDefault())
                    .toLocalTime()
            )
        }
    Log.d(
        TAG, "AppointmentsScreen: ${
            Instant.ofEpochMilli(appointmentDateTime.value)
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
        }"
    )


//    Alarm 1 Date & Time
    val alarm1DatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = appointmentAlarm1DateTime.value * 1000
    )
    val alarm1TimepickerState: MutableState<LocalTime> =
        rememberSaveable {
            mutableStateOf(
                Instant.ofEpochMilli(appointmentAlarm1DateTime.value * 1000)
                    .atZone(ZoneId.systemDefault()).toLocalTime()
            )
        }
//    Switch for alarm #1
    val alarm1Checked = remember {
        mutableStateOf(true)
    }

//    Alarm 2 Date & Time
    val alarm2DatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = appointmentAlarm2DateTime.value * 1000
    )
    val alarm2TimepickerState: MutableState<LocalTime> =
        rememberSaveable {
            mutableStateOf(
                Instant.ofEpochMilli(appointmentAlarm2DateTime.value * 1000)
                    .atZone(ZoneId.systemDefault())
                    .toLocalTime()
            )
        }
//    Switch for alarm #2
    val alarm2Checked = remember {
        mutableStateOf(true)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ExtendedFloatingActionButton(
                        onClick = {
                            vm.setCurrentAppointment(0)
                            navController.navigate(Screens.Home.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.Close,
                            contentDescription = "Annuleren"
                        )
                        Text(text = "Annuleren")
                    }
                    if (appointmentName.value != "") {
                        ExtendedFloatingActionButton(
                            onClick = {
                                vm.setCurrentAppointment(0)
                                navController.navigate(Screens.Home.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.delete_24px),
                                contentDescription = "Verwijderen"
                            )
                            Text(text = "Verwijderen")
                        }
                    }
                    ExtendedFloatingActionButton(
                        onClick = {
                            appointmentViewModel.setDateTime(
                                LocalDateTime.of(
                                    datePickerState.selectedDateMillis?.let {
                                        convertMillisToLocalDate(it)
                                    },
                                    timepickerState.value
                                ).toEpochSecond(ZoneOffset.UTC) * 1000
                            )
                            appointmentViewModel.setAlarm1DateTime(
                                LocalDateTime.of(
                                    alarm1DatePickerState.selectedDateMillis?.let {
                                        convertMillisToLocalDate(it)
                                    },
                                    alarm1TimepickerState.value
                                ).toEpochSecond(ZoneOffset.UTC) * 1000
                            )
                            appointmentViewModel.setAlarm2DateTime(
                                LocalDateTime.of(
                                    alarm2DatePickerState.selectedDateMillis?.let {
                                        convertMillisToLocalDate(it)
                                    },
                                    alarm2TimepickerState.value
                                ).toEpochSecond(ZoneOffset.UTC) * 1000
                            )

                            appointmentViewModel.upsertViewModelAppointment()

//
                            navController.navigate(Screens.Home.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
//
                        },
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.save_24px),
                            contentDescription = "Opslaan"
                        )
                        Text(text = "Opslaan")
                    }
                }
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.SpaceBetween
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
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text(text = "Afspraak naam") },
                            value = appointmentName.value,
                            onValueChange = appointmentViewModel::setName
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        CustomDatePicker(
                            modifier = Modifier,
                            label = "Afspraak Datum",
                            datePickerState
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        CustomTimePicker(label = "Afspraak Tijd", timepickerState)
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
                                CustomDatePicker(
                                    modifier = Modifier,
                                    label = "Datum",
                                    alarm1DatePickerState,
                                    enabled = alarm1Checked
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                CustomTimePicker(
                                    label = "Tijd",
                                    alarm1TimepickerState,
                                    enabled = alarm1Checked
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(110.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Switch(
                                    checked = alarm1Checked.value,
                                    onCheckedChange = {
                                        alarm1Checked.value = it
                                    }
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
                                CustomDatePicker(
                                    modifier = Modifier,
                                    label = "Datum",
                                    alarm2DatePickerState,
                                    enabled = alarm2Checked
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                CustomTimePicker(
                                    label = "Tijd",
                                    alarm2TimepickerState,
                                    alarm2Checked
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(110.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Switch(
                                    checked = alarm2Checked.value,
                                    onCheckedChange = {
                                        alarm2Checked.value = it
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun convertMillisToLocalDate(millis: Long): LocalDate {
    return Instant
        .ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

