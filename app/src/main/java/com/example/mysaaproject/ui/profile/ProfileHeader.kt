package com.example.mysaaproject.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.data.profile.UserProfile
import com.example.mysaaproject.ui.kudos.Avatar
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaBackground
import com.example.mysaaproject.ui.theme.SaaButton
import com.example.mysaaproject.ui.theme.SaaOnButton
import com.example.mysaaproject.ui.theme.SaaOnDark

private val CONTENT_INSET = 20.dp
private val HEADER_TOP_INSET = 76.dp

/**
 * Profile hero: the decorative key-visual background with the user's avatar, name, team code +
 * achievement badge, and the icon-collection row stacked on top. Shared by the own- and
 * other-profile screens. The app [com.example.mysaaproject.ui.home.HomeHeader] is overlaid on top
 * by the screen, so content starts below [HEADER_TOP_INSET].
 */
@Composable
fun ProfileHeroSection(
    profile: UserProfile,
    iconCollectionLabel: String,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(R.drawable.keyvisual_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            modifier = Modifier.matchParentSize(),
        )
        Box(modifier = Modifier.matchParentSize().background(Brush.verticalGradient(0.6f to Color.Transparent, 1f to SaaBackground)))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = CONTENT_INSET, end = CONTENT_INSET, top = HEADER_TOP_INSET, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Avatar(name = profile.name, size = 88.dp)
            Text(
                text = profile.name,
                color = SaaButton,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
            )
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(profile.code, color = SaaOnDark, fontFamily = Montserrat, fontSize = 13.sp)
                HeroBadge(profile.hero.label)
            }
            IconCollectionRow(label = iconCollectionLabel, badges = profile.badges)
        }
    }
}

/** Gold achievement pill ("Legend Hero" / "Rising Hero"). */
@Composable
private fun HeroBadge(label: String) {
    Text(
        text = label,
        color = SaaOnButton,
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(SaaButton)
            .padding(horizontal = 8.dp, vertical = 2.dp),
    )
}
