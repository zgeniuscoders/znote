package cd.zgeniuscoders.znote.note.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoteDto(
    val data: NoteDtoData
)

@Serializable
data class NotesDto(
    val data: List<NoteDtoData>
)

@Serializable
data class NoteDtoData(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("created_at")
    val createdAt: Long
)