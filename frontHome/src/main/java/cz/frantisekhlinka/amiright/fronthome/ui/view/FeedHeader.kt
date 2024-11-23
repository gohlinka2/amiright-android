package cz.frantisekhlinka.amiright.fronthome.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.frantisekhlinka.amiright.coreui.R

@Composable
internal fun FeedHeader(
    onAddButtonClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .padding(horizontal = 16.dp)
            .padding(top = 22.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            stringResource(R.string.app_name_que),
            style = MaterialTheme.typography.displaySmall,
        )
        IconButton(
            onClick = onAddButtonClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
        ) {
            Icon(
                Icons.Rounded.Add,
                contentDescription = stringResource(R.string.add_post),
            )
        }
    }
}