package cz.frantisekhlinka.amiright.fronthome.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
internal fun VoteTouchTargets(onVote: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ClickableArea(
            onClick = { onVote(false) },
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )
        ClickableArea(
            onClick = { onVote(true) },
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )
    }
}

@Composable
private fun ClickableArea(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick
            )
    )
}