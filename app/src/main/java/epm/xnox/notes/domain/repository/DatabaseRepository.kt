package epm.xnox.notes.domain.repository

import epm.xnox.notes.data.sources.database.entities.NoteEntity
import epm.xnox.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun getAllNotes(): Flow<List<Note>>
    suspend fun getNote(id: Int): Flow<Note>
    suspend fun insertNote(note: NoteEntity)
    suspend fun updateNote(note: NoteEntity)
    suspend fun deleteNote(id: Int)
    suspend fun deleteAllNotes()
}