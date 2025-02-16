package cd.zgeniuscoders.znote.note.presenation.add_note

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState
) {

    val vm = koinViewModel<AddNoteViewModel>()
    val state by vm.state.collectAsStateWithLifecycle()
    val onEvent = vm::onTriggerEvent


    LaunchedEffect(state.isAdded) {
        if (state.isAdded) {
            navHostController.navigate(Routes.Home)
        }
    }

    LaunchedEffect(state.flashMessage) {
        if(state.flashMessage.isNotBlank()){
            snackbarHostState.showSnackbar(state.flashMessage)
        }
    }

    AddNoteBody(
        snackbarHostState,
        navHostController,
        state,
        onEvent
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteBody(
    snackbarHostState: SnackbarHostState,
    navHostController: NavHostController,
    state: AddNoteState,
    onEvent: (event: AddNoteEvent) -> Unit
) {

    val focusRequest = remember {
        FocusRequester()
    }

    val focusManger = LocalFocusManager.current

//    focusRequest.requestFocus()

    Scaffold(
        topBar = {
            LargeTopAppBar(title = {
                TextField(
                    state.title,
                    modifier = Modifier.focusRequester(focusRequest),
                    placeholder = {
                        Text(
                            stringResource(R.string.title_lbl),
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManger.moveFocus(FocusDirection.Down)
                        }
                    ),
                    onValueChange = {
                        onEvent(AddNoteEvent.OnTitleChange(it))
                    }
                )
            },
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.popBackStack()
                    }) {
                        Icon(
                            Icons.Rounded.ArrowBackIosNew,
                            contentDescription = "back in previous screen button"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onEvent(AddNoteEvent.OnSaveNote)
                    }) {
                        Icon(Icons.Rounded.Check, contentDescription = "save note button")
                    }
                })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerP ->
        TextField(
            state.content,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerP),
            placeholder = {
                Text(
                    stringResource(R.string.content_lbl),
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
                onEvent(AddNoteEvent.OnContentChange(it))
            }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
fun AddNotePagePreview(modifier: Modifier = Modifier) {
    ZnoteTheme {

        AddNoteBody(
            SnackbarHostState(),
            rememberNavController(),
            AddNoteState(),
        ) {}
    }
}