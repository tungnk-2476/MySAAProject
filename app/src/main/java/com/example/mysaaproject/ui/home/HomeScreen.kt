package com.example.mysaaproject.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaBackground
import com.example.mysaaproject.ui.theme.SaaButton
import com.example.mysaaproject.ui.theme.SaaOnButton

/**
 * Placeholder Home / dashboard reached after a successful login. The real dashboard
 * (Sun* Kudos, Top Project, etc.) is out of scope for the login task — this exists so
 * navigation, auto-login, and logout flows are exercisable end-to-end.
 */
@Composable
fun HomeScreen(onLogout: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SaaBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.logo_sun_award),
            contentDescription = "Sun* Annual Awards",
            modifier = Modifier.size(64.dp),
        )
        Text(
            text = appString(R.string.home_welcome),
            color = Color.White,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            modifier = Modifier.padding(top = 24.dp),
        )
        Text(
            text = appString(R.string.home_subtitle),
            color = Color.White,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 8.dp),
        )
        Button(
            onClick = onLogout,
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SaaButton,
                contentColor = SaaOnButton,
            ),
            modifier = Modifier
                .padding(top = 32.dp)
                .height(48.dp),
        ) {
            Text(
                text = appString(R.string.home_logout),
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
            )
        }
    }
}
