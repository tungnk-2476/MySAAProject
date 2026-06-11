package com.example.mysaaproject.ui.kudos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.example.mysaaproject.R
import com.example.mysaaproject.data.kudos.Kudo
import com.example.mysaaproject.ui.components.SectionHeader
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaOnDark
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

private val CONTENT_INSET = 20.dp

/** Highlight section (B): header + filter chips + a center-active swipeable carousel of kudo cards. */
@Composable
fun HighlightSection(
    kudos: List<Kudo>,
    hashtagOptions: List<String>,
    departments: List<String>,
    selectedHashtag: String?,
    selectedDepartment: String?,
    onLike: (Kudo) -> Unit,
    onCopyLink: (Kudo) -> Unit,
    onDetails: (Kudo) -> Unit,
    onSender: (Kudo) -> Unit,
    onReceiver: (Kudo) -> Unit,
    onSelectHashtag: (String) -> Unit,
    onSelectDepartment: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // Hoisted above the conditional — remember calls must run on every recomposition.
    val pagerState = rememberPagerState(pageCount = { kudos.size })
    val scope = rememberCoroutineScope()

    // Reset to the first card whenever a filter changes (keyed on the selection, not the list — so a
    // like-toggle, which mutates list content but not the active filter, never jumps the pager).
    LaunchedEffect(selectedHashtag, selectedDepartment) {
        if (kudos.isNotEmpty()) pagerState.scrollToPage(0)
    }

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionHeader(
            eyebrow = appString(R.string.kudos_eyebrow),
            title = appString(R.string.kudos_highlight_title),
            modifier = Modifier.padding(horizontal = CONTENT_INSET),
        )
        Row(
            modifier = Modifier.padding(horizontal = CONTENT_INSET),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            FilterDropdown(
                defaultLabel = appString(R.string.kudos_filter_hashtag),
                options = hashtagOptions,
                selected = selectedHashtag,
                onSelect = onSelectHashtag,
            )
            FilterDropdown(
                defaultLabel = appString(R.string.kudos_filter_department),
                options = departments,
                selected = selectedDepartment,
                onSelect = onSelectDepartment,
            )
        }

        if (kudos.isNotEmpty()) {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 36.dp),
                pageSpacing = 12.dp,
            ) { page ->
                val offset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction)
                    .absoluteValue.coerceIn(0f, 1f)
                val k = kudos[page]
                KudoCard(
                    kudo = k,
                    onLike = { onLike(k) },
                    onCopyLink = { onCopyLink(k) },
                    onDetails = { onDetails(k) },
                    onSender = { onSender(k) },
                    onReceiver = { onReceiver(k) },
                    contentMaxLines = 3,
                    modifier = Modifier.fillMaxWidth().graphicsLayer {
                        val s = lerp(0.92f, 1f, 1f - offset)
                        scaleX = s
                        scaleY = s
                        alpha = lerp(0.5f, 1f, 1f - offset)
                    },
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Chevron("‹") { scope.launch { pagerState.animateScrollToPage((pagerState.currentPage - 1).coerceAtLeast(0)) } }
                Text(
                    text = "${pagerState.currentPage + 1}/${kudos.size}",
                    color = SaaOnDark,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                Chevron("›") { scope.launch { pagerState.animateScrollToPage((pagerState.currentPage + 1).coerceAtMost(kudos.size - 1)) } }
            }
        }
    }
}

@Composable
private fun Chevron(symbol: String, onClick: () -> Unit) {
    Text(
        text = symbol,
        color = SaaOnDark,
        fontFamily = Montserrat,
        fontSize = 22.sp,
        modifier = Modifier.clickable(onClick = onClick).padding(horizontal = 12.dp, vertical = 12.dp),
    )
}
