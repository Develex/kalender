package com.develex.baseapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Appointment::class], version = 4)
abstract class AppointmentDatabase : RoomDatabase() {
    abstract val appointmentDao: AppointmentDao
}