package com.example.mysaaproject.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.ui.locale.AppLanguage
import com.example.mysaaproject.ui.locale.ProvideAppLanguage
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaBackground

/**
 * Presentational login screen. All state is hoisted so it can be driven by a ViewModel
 * (production) or static values (preview). Pixel-mapped from the MoMorph "[iOS] Login" frame.
 */
@Composable
fun LoginScreen(
    language: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    isLoading: Boolean,
    isError: Boolean,
    onGoogleClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.keyvisual_bg),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp),
        ) {
            // Top bar: logo (left) + language selector (right)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_sun_award),
                    contentDescription = "Sun* Annual Awards",
                    modifier = Modifier
                        .width(48.dp)
                        .height(44.dp),
                )
                Spacer(modifier = Modifier.weight(1f))
                LanguageSelector(selected = language, onSelect = onLanguageSelected)
            }

            Spacer(modifier = Modifier.height(140.dp))

            Image(
                painter = painterResource(R.drawable.logo_root_further),
                contentDescription = "ROOT FURTHER",
                modifier = Modifier
                    .width(247.dp)
                    .height(109.dp),
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = appString(R.string.login_description),
                color = Color.White,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            )

            // Design places the button at ~77% height with a large gap above and a
            // smaller gap below (≈193px : 98px in the 375×812 frame). Distribute the
            // free vertical space 2:1 so the button is not pulled down to the footer.
            Spacer(modifier = Modifier.weight(2f))

            if (isError) {
                Text(
                    text = appString(R.string.login_error_auth),
                    color = Color(0xFFFFB3B3),
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                )
            }

            GoogleLoginButton(
                label = appString(R.string.login_google_button),
                isLoading = isLoading,
                onClick = onGoogleClick,
                modifier = Modifier.padding(horizontal = 45.dp),
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = appString(R.string.login_copyright),
                color = Color.White,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375, heightDp = 812)
@Composable
private fun LoginScreenPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        ProvideAppLanguage(language = AppLanguage.VN) {
            LoginScreen(
                language = AppLanguage.VN,
                onLanguageSelected = {},
                isLoading = false,
                isError = false,
                onGoogleClick = {},
                modifier = Modifier.background(SaaBackground),
            )
        }
    }
}
