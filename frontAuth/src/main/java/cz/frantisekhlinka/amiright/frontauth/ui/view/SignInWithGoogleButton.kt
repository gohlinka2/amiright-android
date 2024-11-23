package cz.frantisekhlinka.amiright.frontauth.ui.view

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.frantisekhlinka.amiright.coreui.R

@Composable
internal fun SignInWithGoogleButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
    ) {
        Text(stringResource(R.string.continue_with_google))
    }
}