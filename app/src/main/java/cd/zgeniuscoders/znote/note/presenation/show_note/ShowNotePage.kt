package cd.zgeniuscoders.znote.note.presenation.show_note

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.znote.ShareEvent
import cd.zgeniuscoders.znote.note.presenation.home.note
import cd.zgeniuscoders.znote.ui.theme.ZnoteTheme
import dev.jeziellago.compose.markdowntext.MarkdownText
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShowNotePage(
    navHostController: NavHostController,
    onShareEvent: (event: ShareEvent) -> Unit
) {

    val vm = koinViewModel<ShowNoteViewModel>()
    val state by vm.state.collectAsStateWithLifecycle()
    val onEvent = vm::onTriggerEvent

    LaunchedEffect(state.flashMessage) { }

    ShowNoteBody(
        navHostController,
        state,
        onEvent
    )

}

@Composable
fun ShowNoteBody(
    navHostController: NavHostController,
    state: ShowNoteState,
    onEvent: (event: ShowNoteEvent) -> Unit
) {
    if (state.note != null) {
        MarkdownText(
            note.content,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
fun ShowNotePagePreview(modifier: Modifier = Modifier) {
    ZnoteTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Hello")
                    },

                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                Icons.Rounded.ArrowBackIosNew,
                                contentDescription = "back in previous screen button"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Rounded.Edit, contentDescription = "edit note button")
                        }
                    }
                )
            }
        ) { innerP ->
            Box(
                modifier = Modifier.padding(innerP)
            ) {
                ShowNoteBody(
                    rememberNavController(),
                    ShowNoteState(
                        note = note
                    )
                ) { }
            }
        }
    }
}