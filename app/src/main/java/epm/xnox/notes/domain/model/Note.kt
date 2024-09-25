package epm.xnox.notes.domain.model

import epm.xnox.notes.data.sources.database.entities.NoteEntity

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val date: String,
    val mark: Boolean
) {
    constructor() : this(id = 0, title = "", content = "", date = "", mark = false)
}

fun NoteEntity.toDomain() = Note(
    id = id,
    title = title,
    content = content,
    date = date,
    mark = mark
)