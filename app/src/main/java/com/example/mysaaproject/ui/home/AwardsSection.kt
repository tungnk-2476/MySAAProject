package com.example.mysaaproject.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.data.awards.Award
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaButton
import com.example.mysaaproject.ui.theme.SaaOnDark

private val CONTENT_INSET = 20.dp
private val SECTION_MIN_HEIGHT = 298.dp

/**
 * Awards section: header + horizontally scrollable cards, with Loading / Empty / Error(+Retry)
 * states (TC_IOS_HOME_GUI_002/003/004, FUN_003/004/005).
 */
@Composable
fun AwardsSection(
    state: AwardsState,
    onAwardDetails: (Award) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionHeader(
            eyebrow = appString(R.string.home_awards_eyebrow),
            title = appString(R.string.home_awards_title),
            modifier = Modifier.padding(horizontal = CONTENT_INSET),
        )

        when (state) {
            AwardsState.Loading -> CenteredArea {
                CircularProgressIndicator(color = SaaButton)
            }

            AwardsState.Empty -> CenteredArea {
                Message(appString(R.string.home_awards_empty))
            }

            AwardsState.Error -> CenteredArea {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Message(appString(R.string.home_awards_error))
                    PillButton(label = appString(R.string.home_retry), onClick = onRetry)
                }
            }

            is AwardsState.Success -> LazyRow(
                contentPadding = PaddingValues(horizontal = CONTENT_INSET),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(state.awards, key = { it.id }) { award ->
                    AwardCard(award = award, onDetailsClick = { onAwardDetails(award) })
                }
            }
        }
    }
}

@Composable
private fun CenteredArea(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(SECTION_MIN_HEIGHT)
            .padding(horizontal = CONTENT_INSET),
        contentAlignment = Alignment.Center,
    ) { content() }
}

@Composable
private fun Message(text: String) {
    Text(
        text = text,
        color = SaaOnDark.copy(alpha = 0.8f),
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
    )
}
