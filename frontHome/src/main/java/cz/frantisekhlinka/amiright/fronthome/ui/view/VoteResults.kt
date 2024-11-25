package cz.frantisekhlinka.amiright.fronthome.ui.view

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cz.frantisekhlinka.amiright.coreui.theme.NegativeRed
import cz.frantisekhlinka.amiright.coreui.theme.PositiveGreen
import cz.frantisekhlinka.amiright.fronthome.viewmodel.VoteData

@Composable
internal fun VoteResults(voteData: VoteData?) {
    val transition = updateTransition(targetState = voteData != null, label = "vote animation")
    val spacerWeight by transition.animateFloat(label = "spacerWeight") { if (it) 0.0f else 1.0f }
    val negativeWeight by transition.animateFloat(label = "negativeWeight") {
        if (it && voteData != null) voteData.negativeVotes.toFloat() else 0.0f
    }
    val positiveWeight by transition.animateFloat(label = "positiveWeight") {
        if (it && voteData != null) voteData.positiveVotes.toFloat() else 0.0f
    }
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (negativeWeight > 0.0f)
            Box(
                modifier = Modifier
                    .weight(negativeWeight)
                    .fillMaxHeight()
                    .background(color = NegativeRed)
            )
        if (spacerWeight > 0.0f)
            Spacer(
                modifier = Modifier
                    .weight(spacerWeight)
                    .fillMaxHeight()
            )
        if (positiveWeight > 0.0f)
            Box(
                modifier = Modifier
                    .weight(positiveWeight)
                    .fillMaxHeight()
                    .background(color = PositiveGreen)
            )
    }
}