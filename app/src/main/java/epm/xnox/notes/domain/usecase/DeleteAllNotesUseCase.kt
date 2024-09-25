package epm.xnox.notes.domain.usecase

import epm.xnox.notes.domain.repository.DatabaseRepository
import javax.inject.Inject

class DeleteAllNotesUseCase @Inject constructor(private val repository: DatabaseRepository) {
    suspend operator fun invoke() = repository.deleteAllNotes()
}