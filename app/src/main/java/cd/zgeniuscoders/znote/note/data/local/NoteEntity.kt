package cd.zgeniuscoders.znote.note.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cd.zgeniuscoders.znote.note.data.dto.NoteDtoData
import cd.zgeniuscoders.znote.note.domain.models.Note
import cd.zgeniuscoders.znote.note.domain.models.NoteRequest

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo("title")
    val title: String,

    @ColumnInfo("content")
    val content: String,

    @ColumnInfo("created_at")
    val createdAt: Long,

    ) {

    companion object {
        fun fromModel(data: Note): NoteEntity {
            return NoteEntity(
                id = data.id,
                title = data.title,
                content = data.content,
                createdAt = data.createdAt,
            )
        }

        fun fromModelRequest(data: NoteRequest): NoteEntity {
            return NoteEntity(
                title = data.title,
                content = data.content,
                createdAt = data.createdAt,
            )
        }
    }

    fun toNoteDtoData(): NoteDtoData {
        return NoteDtoData(
            id,
            title,
            content,
            createdAt
        )
    }

}