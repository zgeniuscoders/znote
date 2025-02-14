package cd.zgeniuscoders.znote.note.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import cd.zgeniuscoders.znote.note.data.local.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDaoService
}