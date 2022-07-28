package com.sweet.net_monitor.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sweet.net_monitor.data.MonitorData
import com.sweet.net_monitor.room.MonitorDao

@Database(entities = [MonitorData::class], version = 1, exportSchema = false)
abstract class MonitorDatabase : RoomDatabase() {

    abstract fun monitorDao(): MonitorDao

}