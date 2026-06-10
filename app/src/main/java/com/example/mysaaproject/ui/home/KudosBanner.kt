package com.example.mysaaproject.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaButton

/**
 * Sun* Kudos hero banner (335×145dp): dark surface with a diagonal gold accent and the
 * Sun* mark + "KUDOS" wordmark. Stands in for the non-exportable design artwork and also
 * serves as the image-load fallback (TC_IOS_HOME_FUN_010).
 */
@Composable
fun KudosBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(145.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF050608), Color(0xFF1A1407), Color(0xFF3A2E0A)),
                    start = Offset.Zero,
                    end = Offset.Infinite,
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.logo_sun_award),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
            )
            Text(
                text = "KUDOS",
                color = SaaButton,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                fontSize = 30.sp,
                letterSpacing = 2.sp,
            )
        }
    }
}
