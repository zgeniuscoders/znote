package cd.zgeniuscoders.znote.note.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cd.zgeniuscoders.znote.note.domain.models.Note

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

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
    }

    fun toModel(): Note {
        return Note(
            id,
            title,
            content,
            createdAt,
        )
    }

}