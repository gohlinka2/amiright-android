package cz.frantisekhlinka.amiright.fronthome.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.frantisekhlinka.amiright.coreui.modifiers.dashedBorder
import cz.frantisekhlinka.amiright.coreui.theme.NegativeRedHint
import cz.frantisekhlinka.amiright.coreui.theme.PositiveGreenHint
import cz.frantisekhlinka.amiright.coreui.R

@Composable
internal fun VoteIndicators(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(bottom = 8.dp)
    ) {
        VoteIndicator(
            text = stringResource(R.string.vote_no),
            color = NegativeRedHint,
            modifier = Modifier.weight(1f)
        )
        Spacer(
            Modifier.width(8.dp)
        )
        VoteIndicator(
            text = stringResource(R.string.vote_yes),
            color = PositiveGreenHint,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun VoteIndicator(
    text: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .dashedBorder(3.dp, color, 10.dp)
            .height(165.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 16.sp,
                color = color
            ),
        )
    }
}
