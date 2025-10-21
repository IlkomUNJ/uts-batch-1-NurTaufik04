import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.ListContact.route
                ) {
                    composable(Screen.ListContact.route) {
                        ListContactScreen(navController = navController)
                    }

                    composable(
                        route = Screen.AddEditContact.route,
                        arguments = listOf(navArgument("contactId") {
                            type = NavType.LongType
                            defaultValue = 0L
                        })
                    ) { backStackEntry ->
                        val contactId = backStackEntry.arguments?.getLong("contactId")
                        AddEditContactScreen(navController = navController, contactId = contactId)
                    }
                }
            }
        }
    }
}