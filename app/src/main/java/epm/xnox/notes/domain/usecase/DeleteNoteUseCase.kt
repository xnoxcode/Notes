package epm.xnox.notes.domain.usecase

import epm.xnox.notes.domain.repository.DatabaseRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(private val repository: DatabaseRepository) {
    suspend operator fun invoke(id: Int) = repository.deleteNote(id)
}