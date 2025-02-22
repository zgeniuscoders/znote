package cd.zgeniuscoders.znote.note.presenation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cd.zgeniuscoders.znote.Routes

@Composable
fun DrawerBody(
    navHostController: NavHostController,
    drawerState: DrawerState
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        DrawerMenuItem(onClick = {
            navHostController.navigate(Routes.DeleteNotes)
        }, icon = Icons.Default.Delete, title = "Corbeille")
    }
}