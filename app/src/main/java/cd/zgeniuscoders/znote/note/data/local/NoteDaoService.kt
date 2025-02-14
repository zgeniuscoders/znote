package cd.zgeniuscoders.znote.note.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDaoService {

    @Upsert
    suspend fun add(data: NoteEntity)

    @Delete
    suspend fun delete(data: NoteEntity)

    @Query("SELECT COUNT(id) FROM notes")
    fun totalItemCount(): Flow<Int>

    @Query("SELECT * FROM notes where id = :id")
    fun show(id: Int): Flow<NoteEntity>

    @Query("SELECT * FROM notes")
    fun all(): Flow<List<NoteEntity>>

}