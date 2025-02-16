package cd.zgeniuscoders.znote.note.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoteRequest(
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("created_at")
    val createdAt: Long
)
