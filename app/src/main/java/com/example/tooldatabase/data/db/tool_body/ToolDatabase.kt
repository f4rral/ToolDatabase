package com.example.tooldatabase.data.db.tool_body

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ToolBody::class],
    version = 1,
    exportSchema = false
)
abstract class ToolDatabase : RoomDatabase() {
    abstract val toolBodyDao: ToolBodyDao

    companion object {
        fun createDatabase(context: Context) : ToolDatabase {
            return Room
                .databaseBuilder(
                    context = context,
                    klass = ToolDatabase::class.java,
                    name = "ToolDatabase.dp"
                )
                .createFromAsset("database/ToolDatabase.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}