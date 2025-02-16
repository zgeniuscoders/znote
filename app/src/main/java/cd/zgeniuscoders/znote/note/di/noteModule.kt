package cd.zgeniuscoders.znote.note.di

import androidx.room.Room
import cd.zgeniuscoders.znote.note.data.local.NoteDaoService
import cd.zgeniuscoders.znote.note.data.local.NoteDatabase
import cd.zgeniuscoders.znote.note.data.repository.RoomNoteRepository
import cd.zgeniuscoders.znote.note.domain.repository.NoteRepository
import cd.zgeniuscoders.znote.note.presenation.add_note.AddNoteViewModel
import cd.zgeniuscoders.znote.note.presenation.edit_note.EditNoteViewModel
import cd.zgeniuscoders.znote.note.presenation.home.HomeViewModel
import cd.zgeniuscoders.znote.note.presenation.show_note.ShowNoteViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val noteModule = module {

    single<NoteDatabase> {
        val appContext = androidContext()
        Room.databaseBuilder(
            appContext,
            NoteDatabase::class.java,
            "notes.db"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    single<NoteDaoService> {
        val noteDb = get<NoteDatabase>()
        noteDb.noteDao()
    }

    single<NoteRepository> {
        RoomNoteRepository(get())
    }

    viewModelOf(::AddNoteViewModel)
    viewModelOf(::EditNoteViewModel)
    viewModelOf(::ShowNoteViewModel)
    viewModelOf(::HomeViewModel)

}