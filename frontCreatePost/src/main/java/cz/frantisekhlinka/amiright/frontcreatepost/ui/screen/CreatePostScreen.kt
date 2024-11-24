package cz.frantisekhlinka.amiright.frontcreatepost.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.frantisekhlinka.amiright.corefront.extensions.consume
import cz.frantisekhlinka.amiright.coreui.R
import cz.frantisekhlinka.amiright.coreui.theme.AMIRIGHTTheme
import cz.frantisekhlinka.amiright.coreui.theme.HintText
import cz.frantisekhlinka.amiright.frontcreatepost.viewmodel.CreatePostState
import cz.frantisekhlinka.amiright.frontcreatepost.viewmodel.CreatePostViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * Let's the user type and create a new AMIRIGHT post.
 */
@Composable
fun CreatePostScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
) {
    val viewModel: CreatePostViewModel = koinViewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    val state by viewModel.state.collectAsStateWithLifecycle()

    val errorText = stringResource(R.string.an_error_occurred_please_check_your_connection)
    LaunchedEffect(Unit) {
        viewModel.saveResultEvent.consume { success ->
            if (success) {
                navigateBack()
            } else {
                snackbarHostState.showSnackbar(errorText)
            }
        }
    }

    CreatePostScreenStateless(
        state = state,
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        onTextChanged = { viewModel.updateText(it) },
        onSendClicked = { viewModel.send() },
        navigateBack = navigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CreatePostScreenStateless(
    state: CreatePostState,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onTextChanged: (String) -> Unit = {},
    onSendClicked: () -> Unit = {},
    navigateBack: () -> Unit = {},
) {
    Scaffold(
        modifier.safeContentPadding(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            if (state is CreatePostState.Idle && state.isValid) {
                FloatingActionButton(
                    onClick = onSendClicked,
                    content = { Icon(Icons.AutoMirrored.Rounded.Send, stringResource(R.string.submit)) },
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.create_a_post),
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack,
                        content = { Icon(Icons.AutoMirrored.Rounded.ArrowBack, null) }
                    )
                },
            )
        }
    ) { contentPadding ->
        when (state) {
            CreatePostState.Loading -> {
                Loading(
                    Modifier
                        .padding(contentPadding)
                        .consumeWindowInsets(contentPadding)
                )
            }

            is CreatePostState.Idle -> {
                CreatePostContent(
                    text = state.text,
                    onTextChanged = onTextChanged,
                    modifier = Modifier
                        .padding(contentPadding)
                        .consumeWindowInsets(contentPadding)
                )
            }
        }
    }
}

@Composable
private fun CreatePostContent(
    text: String,
    onTextChanged: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = text,
            onValueChange = onTextChanged,
            textStyle = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(horizontal = 67.dp),
            placeholder = {
                Text(
                    stringResource(R.string.type_your_bold_statement_here),
                    style = MaterialTheme.typography.displayMedium.copy(color = HintText)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            )
        )
    }
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun CreatePostScreenPreview() {
    AMIRIGHTTheme {
        CreatePostScreenStateless(
            state = CreatePostState.Loading
        )
    }
}