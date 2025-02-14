package cd.zgeniuscoders.znote.note.presenation.add_note

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.znote.R
import cd.zgeniuscoders.znote.Routes
import cd.zgeniuscoders.znote.ui.theme.ZnoteTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddNotePage(
    navHostController: NavHostController
) {

    val vm = koinViewModel<AddNoteViewModel>()
    val state by vm.state.collectAsStateWithLifecycle()
    val onEvent = vm::onTriggerEvent


    LaunchedEffect(state.isAdded) {
        if (state.isAdded) {
            navHostController.navigate(Routes.Home)
        }
    }

    AddNoteBody(
        navHostController,
        state,
        onEvent
    )

}

@Composable
fun AddNoteBody(
    navHostController: NavHostController,
    state: AddNoteState,
    onEvent: (event: AddNoteEvent) -> Unit
) {

    TextField(
        state.content,
        modifier = Modifier.fillMaxSize(),
        placeholder = {
            Text(stringResource(R.string.content_lbl), style = MaterialTheme.typography.titleLarge)
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        onValueChange = {
            onEvent(AddNoteEvent.OnContentChange(it))
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
fun AddNotePagePreview(modifier: Modifier = Modifier) {
    ZnoteTheme {
        Scaffold(
            topBar = {
                LargeTopAppBar(title = {
                    TextField(
                        "",
                        placeholder = {
                            Text(
                                stringResource(R.string.title_lbl),
                                style = MaterialTheme.typography.titleLarge
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        onValueChange = {

                        }
                    )
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
                            Icon(Icons.Rounded.Check, contentDescription = "save note button")
                        }
                    })
            }
        ) { innerP ->
            Box(Modifier.padding(innerP)) {
                AddNoteBody(
                    rememberNavController(),
                    AddNoteState(),
                ) {}
            }
        }
    }
}