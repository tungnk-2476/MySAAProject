package com.example.mysaaproject.ui.standards

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.ui.locale.AppLanguage
import com.example.mysaaproject.ui.locale.ProvideAppLanguage
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.locale.appStringArray
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaBackground
import com.example.mysaaproject.ui.theme.SaaButton
import com.example.mysaaproject.ui.theme.SaaDivider
import com.example.mysaaproject.ui.theme.SaaOnDark

private val CONTENT_INSET = 20.dp

/**
 * Static Community Standards page (no state): top bar, ROOT FURTHER banner, the Community
 * Standards section (intro + warning + 10 numbered criteria) and the Security Standards section.
 */
@Composable
fun CommunityStandardsScreen(onBack: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize().background(SaaBackground)) {
        Image(
            painter = painterResource(R.drawable.keyvisual_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Box(modifier = Modifier.fillMaxSize().background(SaaBackground.copy(alpha = 0.85f)))

        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            TopBar(onBack = onBack)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .navigationBarsPadding()
                    .padding(start = CONTENT_INSET, end = CONTENT_INSET, top = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // ROOT FURTHER banner — design art is 151×64dp (mm_media_Artboard), 24dp below the top bar.
                Image(
                    painter = painterResource(R.drawable.logo_root_futher_standart),
                    contentDescription = "ROOT FURTHER",
                    modifier = Modifier
                        .width(151.dp)
                        .height(64.dp),
                )

                // Community Standards section
                SectionTitle(appString(R.string.cs_community_title))
                Body(appString(R.string.cs_community_intro), color = SaaButton, bold = true)
                Body(appString(R.string.cs_community_warning))
                NumberedList(appStringArray(R.array.cs_criteria))

                HorizontalDivider(thickness = 1.dp, color = SaaDivider)

                // Security Standards section
                SectionTitle(appString(R.string.cs_security_title))
                Body(appString(R.string.cs_security_desc), bold = true)
                BulletPoint(appString(R.string.cs_security_info_privacy))
                BulletPoint(appString(R.string.cs_security_info_scope))
                Body(appString(R.string.cs_security_contact), color = SaaButton, bold = true)

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun TopBar(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        IconButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = appString(R.string.cd_back),
                tint = SaaOnDark,
                modifier = Modifier.size(24.dp),
            )
        }
        Text(
            text = appString(R.string.cs_title),
            color = SaaOnDark,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp,
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        color = SaaButton,
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
    )
}

@Composable
private fun Body(text: String, color: androidx.compose.ui.graphics.Color = SaaOnDark, bold: Boolean = false) {
    Text(
        text = text,
        color = color,
        fontFamily = Montserrat,
        fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    )
}

@Composable
private fun NumberedList(items: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items.forEachIndexed { index, item ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                RowText("${index + 1}.", modifier = Modifier.width(24.dp))
                RowText(item, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun BulletPoint(text: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        RowText("•", modifier = Modifier.width(24.dp))
        RowText(text, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun RowText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = SaaOnDark,
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        modifier = modifier,
    )
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375, heightDp = 812)
@Composable
private fun CommunityStandardsPreview() {
    ProvideAppLanguage(language = AppLanguage.VN) {
        CommunityStandardsScreen(onBack = {})
    }
}
