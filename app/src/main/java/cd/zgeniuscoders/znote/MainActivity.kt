package cd.zgeniuscoders.znote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Routes.Home) {

                        composable<Routes.Home> {
                            HomePage(navController)
                        }

                        composable<Routes.ShowNote> {
                            ShowNotePage(navController) { }
                        }

                        composable<Routes.EditNote> {
                            EditNotePage(navController)
                        }

                        composable<Routes.AddNote> {
                            AddNotePage(navController)
                        }

                    }

                }
            }
        }
    }
}

