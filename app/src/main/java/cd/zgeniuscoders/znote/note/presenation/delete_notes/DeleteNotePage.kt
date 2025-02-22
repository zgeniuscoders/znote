package cd.zgeniuscoders.znote.note.presenation.delete_notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.znote.R
import cd.zgeniuscoders.znote.Routes
import cd.zgeniuscoders.znote.note.domain.models.Note
import cd.zgeniuscoders.znote.ui.theme.ZnoteTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun DeleteNotePage(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState
) {

    val vm = koinViewModel<DeleteNoteViewModel>()
    val state by vm.state.collectAsStateWithLifecycle()
    val onEvent = vm::onTriggerEvent

    LaunchedEffect(state.flashMessage) {
        if (state.flashMessage.isNotBlank()) {
            snackbarHostState.showSnackbar(state.flashMessage)
        }
    }

    DeleteNoteBody(
        snackbarHostState,
        navHostController,
        state,
        onEvent
    )

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DeleteNoteBody(
    snackbarHostState: SnackbarHostState,
    navHostController: NavHostController,
    state: DeleteNoteState,
    onEvent: (event: DeleteNoteEvent) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Corbeille")
            })
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { innerP ->

        when {

            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerP),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.notes.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerP),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        stringResource(R.string.empty_recyclerbin_msg),
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.padding(innerP),
                ) {
                    items(state.notes) { note ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                onClick = {
                                    navHostController.navigate(Routes.ShowNote(note.id))
                                }
                            ) {
                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(
                                        modifier = Modifier.padding(10.dp)
                                    ) {
                                        Text(
                                            note.title,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        if (note.content.length > 50) {
                                            Text("${note.content.subSequence(0, 50)}...")
                                        } else {
                                            Text(note.content)
                                        }
                                    }
                                    IconButton(
                                        onClick = {
                                            onEvent(DeleteNoteEvent.OnDeleteNote(note))
                                        }
                                    ) {
                                        Icon(
                                            Icons.Rounded.DeleteForever,
                                            contentDescription = "delete note forever"
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            }

        }

    }

}

@PreviewLightDark
@Composable
fun DeleteNotePreview(modifier: Modifier = Modifier) {
    ZnoteTheme {

        DeleteNoteBody(
            snackbarHostState = SnackbarHostState(),
            rememberNavController(),
            DeleteNoteState()
        ) { }

    }
}

internal val note = Note(
    1,
    "Myself bank",
    "# lorem ipsum \nhello world commen allez vous monsiier l'agent",
    1,
    false
)