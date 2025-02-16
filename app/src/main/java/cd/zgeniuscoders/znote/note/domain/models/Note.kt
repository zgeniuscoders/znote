package cd.zgeniuscoders.znote.note.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val createdAt: Long
)
