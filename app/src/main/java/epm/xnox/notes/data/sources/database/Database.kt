package epm.xnox.notes.data.sources.database

import androidx.room.Database
import androidx.room.RoomDatabase
import epm.xnox.notes.data.sources.database.dao.NoteDao
import epm.xnox.notes.data.sources.database.entities.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun getNotesDao(): NoteDao
}