package cz.frantisekhlinka.amiright.fronthome.ui.view

import androidx.compose.foundation.background
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
import cz.frantisekhlinka.amiright.coreui.theme.NegativeRed
import cz.frantisekhlinka.amiright.coreui.theme.PositiveGreen
import cz.frantisekhlinka.amiright.fronthome.viewmodel.VoteData

@Composable
internal fun VoteResults(voteData: VoteData) {
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (voteData.negativeVotes > 0)
            Box(
                modifier = Modifier
                    .weight(voteData.negativeVotes.toFloat())
                    .fillMaxHeight()
                    .background(color = NegativeRed)
            )
        if (voteData.positiveVotes > 0)
            Box(
                modifier = Modifier
                    .weight(voteData.positiveVotes.toFloat())
                    .fillMaxHeight()
                    .background(color = PositiveGreen)
            )
    }
}