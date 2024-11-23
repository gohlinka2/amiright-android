package cz.frantisekhlinka.amiright.coreui.views.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/***
 * A dummy screen that displays a text. Use when you need to create a new screen and don't have the
 * content yet.
 */
@Composable
fun DummyCenteredText(
    text: String
) {
    Box(
        Modifier
            .safeContentPadding()
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text)
    }
}