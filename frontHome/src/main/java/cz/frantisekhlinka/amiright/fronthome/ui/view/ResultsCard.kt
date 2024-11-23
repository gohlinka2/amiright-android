package cz.frantisekhlinka.amiright.fronthome.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.frantisekhlinka.amiright.coreui.R
import cz.frantisekhlinka.amiright.fronthome.viewmodel.VoteData

@Composable
internal fun ResultsCard(
    voteData: VoteData,
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .padding(vertical = 12.dp, horizontal = 24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val textStyle = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color.Black
            )
            Text(
                voteData.negativeVotes.toString(),
                style = textStyle
            )
            FilledTonalButton(
                onClick = onNextClicked,
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            ) {
                Icon(
                    Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = null,
                )
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.next))
            }
            Text(
                voteData.positiveVotes.toString(),
                style = textStyle
            )
        }
    }
}