package epm.xnox.notes.data.sources.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import epm.xnox.notes.data.sources.database.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    suspend fun insertNote(note: NoteEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: NoteEntity)

    @Query("SELECT * FROM notes_table order by date DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("DELETE FROM notes_table")
    suspend fun deleteAllNotes()

    @Query("DELETE FROM notes_table WHERE id = :id")
    suspend fun deleteNote(id: Int)

    @Query("SELECT * FROM notes_table WHERE id = :id")
    fun getNote(id: Int): Flow<NoteEntity>
}