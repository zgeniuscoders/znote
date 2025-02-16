package cd.zgeniuscoders.znote.note.presenation.show_note

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.znote.Routes
import cd.zgeniuscoders.znote.note.presenation.home.note
import cd.zgeniuscoders.znote.ui.theme.ZnoteTheme
import dev.jeziellago.compose.markdowntext.MarkdownText
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShowNotePage(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState
) {

    val vm = koinViewModel<ShowNoteViewModel>()
    val state by vm.state.collectAsStateWithLifecycle()
    val onEvent = vm::onTriggerEvent

    LaunchedEffect(state.flashMessage) {
        if (state.flashMessage.isNotBlank()) {
            snackbarHostState.showSnackbar(state.flashMessage)
        }
    }

    LaunchedEffect(state.isNoteDeleted) {
        if (state.isNoteDeleted) {
            navHostController.navigate(Routes.Home) {
                popUpTo(navHostController.graph.id) {
                    inclusive = true
                }
            }
        }
    }

    ShowNoteBody(
        snackbarHostState,
        navHostController,
        state,
        onEvent
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowNoteBody(
    snackbarHostState: SnackbarHostState,
    navHostController: NavHostController,
    state: ShowNoteState,
    onEvent: (event: ShowNoteEvent) -> Unit
) {

    if (state.note != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(state.note.title)
                    },

                    navigationIcon = {
                        IconButton(onClick = {
                            navHostController.navigate(Routes.Home)
                        }) {
                            Icon(
                                Icons.Rounded.ArrowBackIosNew,
                                contentDescription = "back in previous screen button"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            navHostController.navigate(Routes.EditNote(state.note.id))
                        }) {
                            Icon(Icons.Rounded.Edit, contentDescription = "edit note button")
                        }
                        IconButton(onClick = {
                            onEvent(ShowNoteEvent.OnDeleteNote)
                        }) {
                            Icon(Icons.Rounded.Delete, contentDescription = "delete note button")
                        }
                    }
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerP ->
            Box(
                modifier = Modifier.padding(innerP)
            ) {

                MarkdownText(
                    state.note.content,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(10.dp)
                )

            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

}


@PreviewLightDark
@Composable
fun ShowNotePagePreview(modifier: Modifier = Modifier) {
    ZnoteTheme {

        ShowNoteBody(
            SnackbarHostState(),
            rememberNavController(),
            ShowNoteState(
                note = note
            )
        ) { }
    }

}