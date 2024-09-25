package epm.xnox.notes.domain.usecase

import epm.xnox.notes.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(private val repository: DatabaseRepository) {
    suspend operator fun invoke() = repository.getAllNotes()
}