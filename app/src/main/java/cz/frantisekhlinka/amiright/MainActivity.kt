package cz.frantisekhlinka.amiright

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import cz.frantisekhlinka.amiright.coreui.theme.AMIRIGHTTheme
import cz.frantisekhlinka.amiright.di.initKoin
import cz.frantisekhlinka.amiright.navigation.AmirightNavHost
import org.koin.compose.KoinApplication
import org.koin.core.KoinApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AmirightComposeApp()
        }
    }
}

@Composable
private fun AmirightComposeApp() {
    val navController = rememberNavController()
    KoinApplication(
        application = KoinApplication::initKoin
    ) {
        AMIRIGHTTheme {
            AmirightNavHost(navController = navController)
        }
    }
}
