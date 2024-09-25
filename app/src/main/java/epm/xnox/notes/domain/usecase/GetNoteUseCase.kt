package epm.xnox.notes.domain.usecase

import epm.xnox.notes.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(private val repository: DatabaseRepository) {
    suspend operator fun invoke(id: Int) = repository.getNote(id)
}