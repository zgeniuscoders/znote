package cd.zgeniuscoders.znote

import kotlinx.serialization.Serializable

sealed interface Routes{


    @Serializable
    data object AddNote: Routes

    @Serializable
    data class EditNote(
        val noteId: Int
    ): Routes

    @Serializable
    data class ShowNote(
        val noteId: Int
    ): Routes

    @Serializable
    data object Home: Routes


}