package com.develex.baseapp.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.develex.baseapp.MainViewModel
import com.develex.baseapp.R
import java.util.Date
import kotlin.math.truncate

@Composable
fun AppointmentsScreen(navController: NavController, vm: MainViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .weight(1f, false),
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
                LazyColumn {
                    items(10) { index ->
                        AppointmentCard(
                            title = "$index",
                            date = "21/03/1999",
                            time = "21:32",
                            alarm1date = "21/03/1999",
                            alarm1time = "21:32",
                            alarm2date = "21/03/1999",
                            alarm2time = "21:32"
                        )
                    }
                }
            }
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(vertical = 2.dp),
            ) {
                Text(text = "Nieuwe afspraak toevoegen")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleAppointmentsScreenPreview() {
    AppointmentsScreen(navController = rememberNavController(), vm = MainViewModel())
}

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

@Preview(showBackground = true)
@Composable
fun SimpleAppointmentCardPreview() {
    AppointmentCard(
        title = "First Appointment",
        date = "21/03/1999",
        time = "21:32",
        alarm1date = "21/03/1999",
        alarm1time = "21:32",
        alarm2date = "21/03/1999",
        alarm2time = "21:32"
    )
}