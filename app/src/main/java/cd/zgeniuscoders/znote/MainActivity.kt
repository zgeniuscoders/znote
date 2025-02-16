package cd.zgeniuscoders.znote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.znote.note.presenation.add_note.AddNotePage
import cd.zgeniuscoders.znote.note.presenation.edit_note.EditNotePage
import cd.zgeniuscoders.znote.note.presenation.home.HomePage
import cd.zgeniuscoders.znote.note.presenation.show_note.ShowNotePage
import cd.zgeniuscoders.znote.ui.theme.ZnoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZnoteTheme {
                Surface(modifier = Modifier.fillMaxSize()) {

                    val navController = rememberNavController()
                    val snackbarHostState = SnackbarHostState()

                    NavHost(navController = navController, startDestination = Routes.Home) {

                        composable<Routes.Home> {
                            HomePage(navController)
                        }

                        composable<Routes.ShowNote> {
                            ShowNotePage(navController,snackbarHostState)
                        }

                        composable<Routes.EditNote> {
                            EditNotePage(navController,snackbarHostState)
                        }

                        composable<Routes.AddNote> {
                            AddNotePage(navController,snackbarHostState)
                        }

                    }

                }
            }
        }
    }
}

