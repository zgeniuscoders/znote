package cd.zgeniuscoders.znote.note.presenation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.znote.R
import cd.zgeniuscoders.znote.Routes
import cd.zgeniuscoders.znote.note.domain.models.Note
import cd.zgeniuscoders.znote.note.presenation.delete_notes.DeleteNoteEvent
import cd.zgeniuscoders.znote.note.presenation.home.components.DrawerBody
import cd.zgeniuscoders.znote.note.presenation.home.components.DrawerHeader
import cd.zgeniuscoders.znote.ui.theme.ZnoteTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomePage(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState
) {

    val vm = koinViewModel<HomeViewModel>()
    val state by vm.state.collectAsStateWithLifecycle()
    val onEvent = vm::onTriggerEvent

    HomeBody(
        snackbarHostState,
        navHostController,
        state,
        onEvent
    )

}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun HomeBody(
    snackbarHostState: SnackbarHostState,
    navHostController: NavHostController,
    state: HomeState,
    onEvent: (event: HomeEvent) -> Unit
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val lazyGridState = rememberLazyGridState()

    val context = LocalContext.current
    val motionScene = remember {
        context.resources.openRawResource(R.raw.motion_scene)
            .readBytes()
            .decodeToString()
    }

    val scrollOffset by remember {
        derivedStateOf {
            lazyGridState.firstVisibleItemIndex * 100 +
                    lazyGridState.firstVisibleItemScrollOffset
        }
    }

    var progress by remember {
        mutableFloatStateOf(0F)
    }

    LaunchedEffect(lazyGridState) {
        snapshotFlow {
            scrollOffset.toFloat()
        }.collect { offset ->
            val scrollSize = (offset / 100f).coerceIn(0f, 1f)
            progress = scrollSize
        }
    }


    ModalNavigationDrawer(drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader()
                DrawerBody(navHostController, drawerState)
            }
        }) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navHostController.navigate(Routes.AddNote)
            }) {
                Icon(Icons.Rounded.EditNote, contentDescription = "add note button")
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { innerP ->
        Column(
            modifier = Modifier.padding(innerP)
        ) {

            MotionLayout(
                motionScene = MotionScene(motionScene),
                progress = progress,
                modifier = Modifier.fillMaxWidth()
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .layoutId("box")
                )

                Text(
                    "Toutes les notes",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .layoutId("big_title")
                        .fillMaxWidth()
                )
                Text(
                    "${state.noteCount} note(s)",
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.layoutId("note_count")
                )

                IconButton(
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    },
                    modifier = Modifier.layoutId("menu_button")
                ) {
                    Icon(Icons.Rounded.Menu, contentDescription = "menu button")
                }

                IconButton(
                    onClick = {

                    },
                    modifier = Modifier.layoutId("search_button")
                ) {
                    Icon(Icons.Rounded.Search, contentDescription = "search note button")
                }



            }

            LazyVerticalGrid(
                state = lazyGridState,
                columns = GridCells.Fixed(2),
            ) {
                items(state.notes) { note ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp),
                            onClick = {
                                navHostController.navigate(Routes.ShowNote(note.id))
                            }
                        ) {
                            Text(note.content, modifier = Modifier.padding(10.dp))
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                note.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                "13 Jan",
                                color = MaterialTheme.colorScheme.secondary,
                            )
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
fun HomePreview(modifier: Modifier = Modifier) {
    ZnoteTheme {

        HomeBody(
            snackbarHostState = SnackbarHostState(),
            rememberNavController(),
            HomeState(
                notes = (1..20).map { cd.zgeniuscoders.znote.note.presenation.delete_notes.note }
            )
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