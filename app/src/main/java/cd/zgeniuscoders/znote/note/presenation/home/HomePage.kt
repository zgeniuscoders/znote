package cd.zgeniuscoders.znote.note.presenation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.znote.note.domain.models.Note
import cd.zgeniuscoders.znote.ui.theme.ZnoteTheme
import dev.jeziellago.compose.markdowntext.MarkdownText
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomePage(
    navHostController: NavHostController
) {

    val vm = koinViewModel<HomeViewModel>()
    val state by vm.state.collectAsStateWithLifecycle()
    val onEvent = vm::onTriggerEvent

    HomeBody(
        navHostController,
        state,
        onEvent
    )

}

@Composable
fun HomeBody(
    navHostController: NavHostController,
    state: HomeState,
    onEvent: (event: HomeEvent) -> Unit
) {


    Column {

        Column { 
            Text("Toutes les notes")
            Text("8 notes")
        }

        LazyVerticalGrid(
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
                            .height(200.dp)
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

@PreviewLightDark
@Composable
fun HomePreview(modifier: Modifier = Modifier) {
    ZnoteTheme {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {}) {
                    Icon(Icons.Rounded.EditNote, contentDescription = "")
                }
            }
        ) { innerP ->
            HomeBody(
                rememberNavController(),
                HomeState(
                    notes = (1..20).map { note }
                )
            ) { }
        }
    }
}

internal val note = Note(
    1,
    "Myself bank",
    "# lorem ipsum \nhello world commen allez vous monsiier l'agent",
    1
)