package cz.frantisekhlinka.amiright.frontauth.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.frantisekhlinka.amiright.corefront.extensions.consume
import cz.frantisekhlinka.amiright.coreui.R
import cz.frantisekhlinka.amiright.coreui.theme.AMIRIGHTTheme
import cz.frantisekhlinka.amiright.frontauth.ui.view.SignInWithGoogleButton
import cz.frantisekhlinka.amiright.frontauth.viewmodel.AuthViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthScreen(modifier: Modifier = Modifier) {
    val viewModel: AuthViewModel = koinViewModel()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    val errorText = stringResource(R.string.an_error_occurred_please_check_your_connection)
    LaunchedEffect(Unit) {
        viewModel.errorEvent.consume {
            snackbarHostState.showSnackbar(errorText)
        }
    }
    LaunchedEffect(Unit) {
        viewModel.successEvent.consume {
            // TODO replace with navigation
            snackbarHostState.showSnackbar("Success!")
        }
    }

    AuthScreenStateless(
        isLoading = isLoading,
        onAuthWithGoogleClicked = { viewModel.authWithGoogle(context) },
        contentSnackbarHostState = snackbarHostState,
        modifier = modifier
    )
}


@Composable
internal fun AuthScreenStateless(
    isLoading: Boolean,
    onAuthWithGoogleClicked: () -> Unit = {},
    contentSnackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        AuthScreenLoading(modifier)
    } else {
        AuthScreenContent(onAuthWithGoogleClicked, contentSnackbarHostState, modifier)
    }
}

@Composable
private fun AuthScreenContent(
    onAuthWithGoogleClicked: () -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            SignInWithGoogleButton(
                onAuthWithGoogleClicked,
                Modifier
                    .padding(32.dp)
                    .fillMaxWidth()
                    .safeContentPadding()
            )
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
private fun AuthScreenLoading(modifier: Modifier = Modifier) {
    Scaffold(modifier) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun AuthScreenContentPreview() {
    AMIRIGHTTheme {
        AuthScreenContent()
    }
}

@Preview(showSystemUi = true)
@Composable
private fun AuthScreenLoadingPreview() {
    AMIRIGHTTheme {
        AuthScreenLoading()
    }
}