package cd.zgeniuscoders.znote.note.presenation.search_note

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.znote.Routes
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchNotePage(navController: NavHostController, snackbarHostState: SnackbarHostState) {
    val vm = koinViewModel<SearchNoteViewModel>()
    val state by vm.state.collectAsStateWithLifecycle()
    val onEvent = vm::onTriggerEvent

    SearchNoteBody(
        navController,
        state, onEvent
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNoteBody(
    navController: NavHostController,
    state: SearchNoteState,
    onEvent: (event: SearchNoteEvent) -> Unit
) {

    var columnsPerGrid by remember {
        mutableIntStateOf(2)
    }

    val configuration = LocalConfiguration.current
    columnsPerGrid = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 4
        else -> 2
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            Icons.Rounded.ArrowBackIosNew,
                            contentDescription = "back to home page"
                        )
                    }
                },
                title = {
                    Text("Recherhe")
                }
            )
        }
    ) { innerP ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = innerP.calculateTopPadding(), horizontal = 16.dp)
        ) {

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.searchQuery,
                placeholder = {
                    Text("Recherche...")
                },
                onValueChange = {
                    onEvent(SearchNoteEvent.OnSearchTextChanged(it))
                }
            )

            when {
                state.isSearch -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                
                else -> {
                    if (state.searchQuery.isNotEmpty()) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(columnsPerGrid),
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.searchNotes) { note ->
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
                                            navController.navigate(Routes.ShowNote(note.id))
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
                                            note.createdAt.split(" ").joinToString("/"),
                                            color = MaterialTheme.colorScheme.secondary,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Hmm... Soit les notes s'est envolée, soit elles n'existent pas encore !",
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }

                }
            }

        }
    }


}

@PreviewLightDark
@Composable
fun SearchNotePagePreview(modifier: Modifier = Modifier) {
    Surface {
        SearchNoteBody(
            navController = rememberNavController(),
            state = SearchNoteState(),
            onEvent = {}
        )
    }
}