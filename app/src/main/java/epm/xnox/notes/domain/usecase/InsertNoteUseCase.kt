package epm.xnox.notes.domain.usecase

import epm.xnox.notes.data.sources.database.entities.NoteEntity
import epm.xnox.notes.domain.repository.DatabaseRepository
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(private val repository: DatabaseRepository) {
    suspend operator fun invoke(note: NoteEntity) = repository.insertNote(note)
}