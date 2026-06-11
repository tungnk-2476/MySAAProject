package com.example.mysaaproject.ui.kudos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.data.kudos.KudosRepository
import com.example.mysaaproject.data.kudos.Recipient
import com.example.mysaaproject.ui.components.BottomTab
import com.example.mysaaproject.ui.components.SaaBottomBar
import com.example.mysaaproject.ui.locale.AppLanguage
import com.example.mysaaproject.ui.locale.ProvideAppLanguage
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaBackground
import com.example.mysaaproject.ui.theme.SaaOnDark

private val CONTENT_INSET = 20.dp

/** Presentational Search Sunner screen. State is hoisted (driven by [SearchSunnerRoute]). */
@Composable
fun SearchSunnerScreen(
    query: String,
    recent: List<Recipient>,
    onQueryChange: (String) -> Unit,
    onBack: () -> Unit,
    onItemClick: (Recipient) -> Unit,
    onRemove: (Recipient) -> Unit,
    onViewAll: () -> Unit,
    onSaaTab: () -> Unit,
    onKudosTab: () -> Unit,
    onAwardsTab: () -> Unit,
    onProfileTab: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize().background(SaaBackground)) {
        Image(
            painter = painterResource(R.drawable.keyvisual_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Box(modifier = Modifier.fillMaxSize().background(SaaBackground.copy(alpha = 0.82f)))

        Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
            SearchTopBar(query = query, onQueryChange = onQueryChange, onBack = onBack)
            Column(modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = CONTENT_INSET)) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(appString(R.string.search_recent), color = SaaOnDark, fontFamily = Montserrat, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(
                        appString(R.string.search_view_all),
                        color = SaaOnDark,
                        fontFamily = Montserrat,
                        fontSize = 14.sp,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable(onClick = onViewAll),
                    )
                }
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(recent, key = { it.id }) { sunner ->
                        RecentRow(sunner, onClick = { onItemClick(sunner) }, onRemove = { onRemove(sunner) })
                    }
                }
            }
            SaaBottomBar(
                active = BottomTab.SAA,
                onSaa = onSaaTab, onAwards = onAwardsTab, onKudos = onKudosTab, onProfile = onProfileTab,
            )
        }
    }
}

@Composable
private fun SearchTopBar(query: String, onQueryChange: (String) -> Unit, onBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 4.dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onBack) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = appString(R.string.cd_back),
                tint = SaaOnDark,
                modifier = Modifier.size(24.dp),
            )
        }
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text(appString(R.string.search_sunner_hint), fontFamily = Montserrat, fontSize = 14.sp, color = SaaOnDark.copy(alpha = 0.5f)) },
            textStyle = TextStyle(fontFamily = Montserrat, fontSize = 14.sp, color = SaaOnDark),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0x1AFFFFFF),
                unfocusedContainerColor = Color(0x1AFFFFFF),
                focusedBorderColor = SaaOnDark.copy(alpha = 0.35f),
                unfocusedBorderColor = SaaOnDark.copy(alpha = 0.25f),
                cursorColor = SaaOnDark,
            ),
        )
    }
}

@Composable
private fun RecentRow(sunner: Recipient, onClick: () -> Unit, onRemove: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Avatar(sunner.name, size = 44.dp)
        Column(modifier = Modifier.weight(1f)) {
            Text(sunner.name, color = SaaOnDark, fontFamily = Montserrat, fontWeight = FontWeight.Medium, fontSize = 15.sp)
            Text(sunner.unit, color = SaaOnDark.copy(alpha = 0.6f), fontFamily = Montserrat, fontSize = 13.sp)
        }
        Text(
            "✕",
            color = SaaOnDark.copy(alpha = 0.6f),
            fontSize = 16.sp,
            modifier = Modifier.clickable(onClick = onRemove).padding(8.dp),
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375, heightDp = 812)
@Composable
private fun SearchSunnerScreenPreview() {
    ProvideAppLanguage(language = AppLanguage.VN) {
        SearchSunnerScreen(
            query = "", recent = KudosRepository.recentSearches,
            onQueryChange = {}, onBack = {}, onItemClick = {}, onRemove = {}, onViewAll = {},
            onSaaTab = {}, onKudosTab = {}, onAwardsTab = {}, onProfileTab = {},
        )
    }
}
