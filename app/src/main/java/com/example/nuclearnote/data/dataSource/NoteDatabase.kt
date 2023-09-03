package com.example.nuclearnote.data.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nuclearnote.data.local.ByteArrayConverter
import com.example.nuclearnote.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
@TypeConverters(ByteArrayConverter::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "nuclearNote_db"
    }
}