package com.example.mysaaproject.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaOnDark

private val CONTENT_INSET = 20.dp

/**
 * Sun* Kudos section: header, hero banner, "new in SAA" badge heading, description, and the
 * gold "Chi tiết" button. Rendered only when the Kudos feature is available (TC_IOS_HOME_GUI_005).
 */
@Composable
fun KudosSection(
    onKudosDetails: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = CONTENT_INSET),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        SectionHeader(
            eyebrow = appString(R.string.home_kudos_eyebrow),
            title = appString(R.string.home_kudos_title),
        )

        KudosBanner()

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = appString(R.string.home_kudos_badge),
                color = SaaOnDark,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                letterSpacing = 0.5.sp,
            )
            Text(
                text = appString(R.string.home_kudos_description),
                color = SaaOnDark,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            )
        }

        PillButton(label = appString(R.string.home_kudos_details), onClick = onKudosDetails)
    }
}
