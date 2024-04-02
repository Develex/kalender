package com.develex.baseapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.DeleteTable
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDateTime
import java.util.Calendar

@Entity(tableName = "appointments")
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var dateTime: Long,
    var alarm1DateTime: Long,
    var alarm2DateTime: Long,
    var alarm1Toggle: Boolean,
    var alarm2Toggle: Boolean
)

@Dao
interface AppointmentDao {

    @Upsert
    suspend fun upsertAppointmentModel(appointment: Appointment): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(appointment: Appointment)

    @Update
    suspend fun update(appointment: Appointment)

    @Delete
    suspend fun delete(appointment: Appointment)

    @Query("SELECT * from appointments WHERE id = :id")
    fun getAppointment(id: Int): Flow<Appointment>

    @Query("SELECT * from appointments")
    fun getAllAppointments(): Flow<List<Appointment>>
}