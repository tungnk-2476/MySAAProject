package com.example.mysaaproject.ui.kudos

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaButton
import com.example.mysaaproject.ui.theme.SaaOnDark

/** KV Kudos banner (A): tagline over the Sun* mark + "KUDOS" wordmark. */
@Composable
fun KudosPageBanner(modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = appString(R.string.kudos_tagline),
            color = SaaOnDark,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        )
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Image(
                painter = painterResource(R.drawable.logo_sun_award),
                contentDescription = null,
                modifier = Modifier.size(44.dp),
            )
            Text(
                text = "KUDOS",
                color = SaaButton,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                fontSize = 40.sp,
                letterSpacing = 2.sp,
            )
        }
    }
}
