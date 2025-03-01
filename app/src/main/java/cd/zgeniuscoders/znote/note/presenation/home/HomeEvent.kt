package cd.zgeniuscoders.znote.note.presenation.home

sealed interface HomeEvent {
    data object OnPullRefresh: HomeEvent
}