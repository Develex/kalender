package com.develex.baseapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface AppointmentRepository {

    fun getAllAppointmentsStream(): Flow<List<Appointment>>

    fun getAppointmentStream(id: Int): Flow<Appointment?>

    suspend fun insertAppointment(appointment: Appointment)

    suspend fun deleteAppointment(appointment: Appointment)

    suspend fun updateAppointment(appointment: Appointment)
}