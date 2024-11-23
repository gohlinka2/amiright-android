package cz.frantisekhlinka.amiright

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import cz.frantisekhlinka.amiright.coreui.theme.AMIRIGHTTheme
import cz.frantisekhlinka.amiright.launcher.ui.Launcher
import cz.frantisekhlinka.amiright.navigation.AmirightNavHost
import org.koin.compose.KoinContext

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
    KoinContext{
        AMIRIGHTTheme {
            Launcher()
        }
    }
}
