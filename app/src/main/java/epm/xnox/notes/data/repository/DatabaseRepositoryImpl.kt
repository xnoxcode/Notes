package epm.xnox.notes.data.repository

import epm.xnox.notes.data.sources.database.dao.NoteDao
import epm.xnox.notes.data.sources.database.entities.NoteEntity
import epm.xnox.notes.domain.model.Note
import epm.xnox.notes.domain.model.toDomain
import epm.xnox.notes.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(private val noteDao: NoteDao) : DatabaseRepository {

    override suspend fun getAllNotes(): Flow<List<Note>> = flow {
        val notes = noteDao.getAllNotes()
        emitAll(
            notes.map { notesEntity ->
                notesEntity.map { it.toDomain() }
            }
        )
    }

    override suspend fun getNote(id: Int): Flow<Note> = flow {
        val note = noteDao.getNote(id)
        emitAll(note.map { it.toDomain() })
    }

    override suspend fun insertNote(note: NoteEntity) {
        noteDao.insertNote(note)
    }

    override suspend fun updateNote(note: NoteEntity) {
        noteDao.updateNote(note)
    }

    override suspend fun deleteNote(id: Int) {
        noteDao.deleteNote(id)
    }

    override suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }
}