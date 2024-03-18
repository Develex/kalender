package com.develex.baseapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.develex.baseapp.MainViewModel
import java.util.Date

@Composable
fun AppointmentsScreen(navController: NavController, vm: MainViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
//                Content page starts here
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, start = 20.dp),
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .clip(MaterialTheme.shapes.large)
            ) {
                Text(
                    "Page2 Screen",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SimpleAppointmentsScreenPreview() {
//    AppointmentsScreen(navController = rememberNavController(), vm = MainViewModel())
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
            .padding(horizontal = 5.dp)
            .height(200.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer),
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(10.dp).fillMaxWidth()
            )
            Row(Modifier.fillMaxWidth()) {
                Column {
                    Spacer(modifier = Modifier.height(60.dp))
                    Text(
                        text = "Datum: $date",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Text(
                        text = "Tijd: $time",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                Column(Modifier.padding(start = 30.dp)) {
                    Text(text = "Alarmen",
                        style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = "Alarm 1: \n$alarm1date $alarm1time",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Alarm 2: \n$alarm2date $alarm2time",
                        style = MaterialTheme.typography.bodyLarge
                    )
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