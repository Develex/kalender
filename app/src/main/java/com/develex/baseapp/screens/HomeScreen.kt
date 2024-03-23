package com.develex.baseapp.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.develex.baseapp.MainViewModel
import com.develex.baseapp.R
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Date
import kotlin.math.truncate

@Composable
fun HomeScreen(navController: NavController, vm: MainViewModel, avm: AppointmentViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
//                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
//            Text(
//                text = "Afspraken",
//                style = MaterialTheme.typography.headlineLarge,
//                textAlign = TextAlign.Center,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(80.dp)
//                    .padding(top = 20.dp)
//            )
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
                        if (selection != clicked) {
                            selection = clicked
                        }
                    }
                })
            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn {
                items(5) { index ->
                    AppointmentCard(
                        title = "$index",
                        date = "11/05/2024",
                        time = "23:45",
                        alarm1date = "11/05/2024",
                        alarm1time = "23:45",
                        alarm2date = "11/05/2024",
                        alarm2time = "23:45"
                    )
                }
            }
        }

    }
}

//@Preview(showBackground = true)
//@Composable
//fun SimpleHomeScreenPreview() {
//    HomeScreen(navController = rememberNavController(), vm = MainViewModel(), avm = )
//}

@Composable
fun AppointmentCard(
    title: String,
    date: String,
    time: String,
    alarm1date: String,
    alarm1time: String,
    alarm2date: String,
    alarm2time: String,
) {
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
        Column {
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
                Column(Modifier.width(140.dp)) {
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
                Column(Modifier.padding(start = 30.dp)) {
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