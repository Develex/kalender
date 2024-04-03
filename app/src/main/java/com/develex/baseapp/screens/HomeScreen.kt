package com.develex.baseapp.screens

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.Create
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.develex.baseapp.MainViewModel
import com.develex.baseapp.R
import com.develex.baseapp.data.Appointment
import com.develex.baseapp.data.AppointmentDatabase
import com.develex.baseapp.navigation.Screens
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Date
import kotlin.math.log
import kotlin.math.truncate

@Composable
fun HomeScreen(
    navController: NavController,
    vm: MainViewModel,
    appointmentViewModel: AppointmentViewModel
) {
//    Get Appointments for current date
    val appointments = appointmentViewModel.getAllAppointments().collectAsState(
        initial = emptyList()
    )

    Log.d("HomeScreen", appointments.value.toString())

    val currentSelectedDay: MutableState<LocalDate> = remember {
        mutableStateOf(LocalDate.now())
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        vm.setCurrentAppointment(0)
                        navController.navigate(Screens.Appointments.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                ) {
                    Icon(imageVector = Icons.TwoTone.Add, contentDescription = "Toevoegen")
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
//                .padding(top = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    val currentDate = remember { LocalDate.now() }
                    val startDate = remember { currentDate.minusDays(500) }
                    val endDate = remember { currentDate.plusDays(500) }
                    var selection by remember { mutableStateOf(currentDate) }

                    val state = rememberWeekCalendarState(
                        startDate = startDate,
                        endDate = endDate,
                        firstVisibleWeekDate = currentDate,
                    )

                    WeekCalendar(
                        Modifier.background(color = MaterialTheme.colorScheme.primary),
                        state = state,
                        dayContent = { day ->
                            Day(day.date, isSelected = selection == day.date) { clicked ->
                                currentSelectedDay.value = day.date
                                Log.d(
                                    "weekCalendar",
                                    "csd: ${currentSelectedDay.value}"
                                )
                                if (selection != clicked) {
                                    selection = clicked
                                }
                            }
                        })
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn {
                        items(appointments.value.filter {
                            Instant.ofEpochMilli(it.dateTime).atZone(ZoneId.of("UTC"))
                                .toLocalDate() == currentSelectedDay.value
                        }) { appointment ->
                            Log.d("Test", appointment.id.toString())
                            AppointmentCard(
                                id = appointment.id,
                                title = appointment.name,
                                date =
                                Instant.ofEpochMilli(appointment.dateTime)
                                    .atZone(ZoneId.of("UTC"))
                                    .toLocalDate()
                                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                                time =
                                Instant.ofEpochMilli(appointment.dateTime)
                                    .atZone(ZoneId.of("UTC"))
                                    .format(DateTimeFormatter.ofPattern("HH:mm")),
                                alarm1date =
                                Instant.ofEpochMilli(appointment.alarm1DateTime)
                                    .atZone(ZoneId.of("UTC"))
                                    .toLocalDate()
                                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                                alarm1time =
                                Instant.ofEpochMilli(appointment.alarm1DateTime)
                                    .atZone(ZoneId.of("UTC"))
                                    .format(DateTimeFormatter.ofPattern("HH:mm")),
                                alarm2date =
                                Instant.ofEpochMilli(appointment.alarm2DateTime)
                                    .atZone(ZoneId.of("UTC"))
                                    .toLocalDate()
                                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                                alarm2time =
                                Instant.ofEpochMilli(appointment.alarm2DateTime)
                                    .atZone(ZoneId.of("UTC"))
                                    .format(DateTimeFormatter.ofPattern("HH:mm")),
                                navController = navController,
                                vm = vm,
                                appointmentViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppointmentCard(
    id: Int,
    title: String,
    date: String,
    time: String,
    alarm1date: String,
    alarm1time: String,
    alarm2date: String,
    alarm2time: String,
    navController: NavController,
    vm: MainViewModel,
    appointmentViewModel: AppointmentViewModel
) {
    val appointment: State<Appointment> =
        appointmentViewModel.getAppointment(id).collectAsState(
            initial = Appointment(
                name = "",
                dateTime = 0,
                alarm1DateTime = 0,
                alarm2DateTime = 0,
                alarm2Toggle = false,
                alarm1Toggle = false
            )
        )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .height(110.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(20)
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Row {
            Column(Modifier.fillMaxWidth(0.7f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                )
                Row(Modifier.fillMaxWidth()) {
                    Column(Modifier.fillMaxWidth(0.5f)) {
                        Row {
                            Icon(
                                painter = painterResource(R.drawable.calendar_month_24px),
                                contentDescription = "Datum Afspraak"
                            )
                            Text(
                                text = "$date",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                        Row {
                            Icon(
                                painter = painterResource(R.drawable.schedule_24px),
                                contentDescription = "Tijd Afspraak"
                            )
                            Text(
                                text = "$time",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }


                    }
                    Column(Modifier.fillMaxWidth()) {
                        Row {
                            Icon(
                                Icons.Outlined.Notifications,
                                contentDescription = "Alarm 1"
                            )
                            Text(
                                text = "$alarm1date $alarm1time",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Row {
                            Icon(
                                Icons.Outlined.Notifications,
                                contentDescription = "Alarm 2"
                            )
                            Text(
                                text = "$alarm2date $alarm2time",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
            Row {
                FilledIconButton(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .fillMaxHeight()
                        .padding(start = 5.dp, top = 5.dp, bottom = 5.dp),
                    shape = MaterialTheme.shapes.large,
                    onClick = {
                        vm.setCurrentAppointment(id)
                        navController.navigate(Screens.Appointments.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                ) {
                    Icon(Icons.TwoTone.Create, "aanpassen")
                }
                FilledIconButton(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .fillMaxHeight()
                        .padding(start = 5.dp, top = 5.dp, bottom = 5.dp),
                    shape = MaterialTheme.shapes.large,
                    onClick = {
                        appointmentViewModel.deleteAppointment(appointment.value)
                    }
                ) {
                    Icon(Icons.TwoTone.Delete, "verwijderen")
                }
            }
        }
    }
}

val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd")

@Composable
fun Day(date: LocalDate, isSelected: Boolean, onClick: (LocalDate) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick(date) },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = date.dayOfWeek.getDisplayName(
                    TextStyle.SHORT,
                    java.util.Locale.getDefault()
                ),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.background,
                fontWeight = FontWeight.Light,
            )
            Text(
                text = dateFormatter.format(date),
                fontSize = 14.sp,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.background,
                fontWeight = FontWeight.Bold,
            )
        }
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(MaterialTheme.colorScheme.onPrimary)
                    .align(Alignment.BottomCenter),
            )
        }
    }
}