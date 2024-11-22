package cz.frantisekhlinka.amiright.frontauth.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.frantisekhlinka.amiright.coreui.R
import cz.frantisekhlinka.amiright.coreui.theme.AMIRIGHTTheme

@Composable
fun AuthScreen(modifier: Modifier = Modifier) {
    AuthScreenContent(modifier)
}

@Composable
private fun AuthScreenContent(modifier: Modifier = Modifier) {
    Scaffold(
        modifier,
        bottomBar = {
            SignInWithGoogleButton()
        },
    ) { contentPadding ->
        Surface(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            Column(
                modifier = Modifier
                    .safeContentPadding()
                    .padding(start = 48.dp, top = 80.dp, end = 80.dp)
            ) {
                Text(
                    stringResource(R.string.app_name_que),
                    style = MaterialTheme.typography.displayLarge,
                )
                Text(
                    stringResource(R.string.tagline),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(top = 32.dp)
                )
            }
        }
    }
}

@Composable
private fun SignInWithGoogleButton() {
    ElevatedButton(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth()
            .safeContentPadding(),
    ) {
        Text(stringResource(R.string.continue_with_google))
    }
}

@Preview(showSystemUi = true)
@Composable
private fun AuthScreenContentPreview() {
    AMIRIGHTTheme {
        AuthScreenContent()
    }
}