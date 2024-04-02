package com.develex.baseapp.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develex.baseapp.data.Appointment
import com.develex.baseapp.data.AppointmentDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class AppointmentViewModel(
    private val dao: AppointmentDao
) : ViewModel() {

    private var _appointmentId = MutableStateFlow(0)
    val appointmentId = _appointmentId.asStateFlow()

    private var _appointmentName = MutableStateFlow("")
    val appointmentName = _appointmentName.asStateFlow()

    private var _appointmentDateTime =
        MutableStateFlow(System.currentTimeMillis() / 1000)
    val appointmentDateTime = _appointmentDateTime.asStateFlow()

    private var _appointmentAlarm1DateTime = MutableStateFlow(System.currentTimeMillis() / 1000)
    val appointmentAlarm1DateTime = _appointmentAlarm1DateTime.asStateFlow()

    private var _appointmentAlarm2DateTime = MutableStateFlow(System.currentTimeMillis() / 1000)
    val appointmentAlarm2DateTime = _appointmentAlarm2DateTime.asStateFlow()

    private var _appointmentAlarm1Toggle = MutableStateFlow(false)
    val appointmentAlarm1Toggle = _appointmentAlarm1Toggle.asStateFlow()

    private var _appointmentAlarm2Toggle = MutableStateFlow(false)
    val appointmentAlarm2Toggle = _appointmentAlarm2Toggle.asStateFlow()

    fun setId(id: Int) {
        _appointmentId.value = id
    }

    fun setName(name: String) {
        _appointmentName.value = name
    }

    fun setDateTime(dateTime: Long) {
        _appointmentDateTime.value = dateTime
    }

    fun setAlarm1DateTime(dateTime: Long) {
        _appointmentAlarm1DateTime.value = dateTime
    }

    fun setAlarm2DateTime(dateTime: Long) {
        _appointmentAlarm2DateTime.value = dateTime
    }

    fun setAlarm1Toggle(toggled: Boolean) {
        _appointmentAlarm1Toggle.value = toggled
    }

    fun setAlarm2Toggle(toggled: Boolean) {
        _appointmentAlarm2Toggle.value = toggled
    }

    fun addData() {
        viewModelScope.launch {
            (0..10).forEach { _ ->
                val newDataToAdd = Appointment(
                    name = "Appointment ${(0..10).random()}",
                    dateTime = System.currentTimeMillis(),
                    alarm1DateTime = System.currentTimeMillis(),
                    alarm2DateTime = System.currentTimeMillis(),
                    alarm1Toggle = false,
                    alarm2Toggle = false
                )
                dao.upsertAppointmentModel(newDataToAdd)
            }
        }
    }

    fun getAllAppointments(): Flow<List<Appointment>> {
        return dao.getAllAppointments()
    }

    fun getAppointment(appointmentId: Int): Flow<Appointment> {
        return dao.getAppointment(appointmentId)
    }

    fun deleteAppointment(appointment: Appointment) {
        viewModelScope.launch {
            dao.delete(appointment)
        }
    }

    fun upsertAppointment(appointment: Appointment) {
        viewModelScope.launch {
            dao.upsertAppointmentModel(appointment)
        }
    }

    fun upsertViewModelAppointment() {
        val appointment = Appointment(
            id = _appointmentId.value,
            name = _appointmentName.value,
            dateTime = _appointmentDateTime.value,
            alarm1DateTime = _appointmentAlarm1DateTime.value,
            alarm2DateTime = _appointmentAlarm2DateTime.value,
            alarm1Toggle = _appointmentAlarm1Toggle.value,
            alarm2Toggle = _appointmentAlarm2Toggle.value,
        )
        Log.d("upsertViewModelAppointment", appointment.toString())
        viewModelScope.launch {
            dao.upsertAppointmentModel(appointment)
        }
    }
}

