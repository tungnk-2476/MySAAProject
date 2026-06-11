package com.example.mysaaproject.ui.kudos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.data.kudos.Recipient
import com.example.mysaaproject.ui.components.BottomTab
import com.example.mysaaproject.ui.components.SaaBottomBar
import com.example.mysaaproject.ui.locale.AppLanguage
import com.example.mysaaproject.ui.locale.ProvideAppLanguage
import com.example.mysaaproject.ui.locale.appString
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaBackground
import com.example.mysaaproject.ui.theme.SaaCardCream
import com.example.mysaaproject.ui.theme.SaaOnDark

private val CONTENT_INSET = 16.dp

/** Presentational New Kudo form. State is hoisted (driven by [SendKudoRoute]/[SendKudoViewModel]). */
@Composable
fun SendKudoScreen(
    uiState: SendKudoUiState,
    titleOptions: List<String>,
    hashtagOptions: List<String>,
    onRecipientQueryChange: (String) -> Unit,
    onSelectRecipient: (Recipient) -> Unit,
    onSelectTitle: (String) -> Unit,
    onMessageChange: (String) -> Unit,
    onToggleHashtag: (String) -> Unit,
    onAddImage: () -> Unit,
    onRemoveImage: () -> Unit,
    onToggleAnonymous: () -> Unit,
    onNicknameChange: (String) -> Unit,
    onCancel: () -> Unit,
    onSend: () -> Unit,
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
            TopBar(title = appString(R.string.send_kudo_title), onBack = onCancel)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = CONTENT_INSET, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SaaCardCream)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Text(
                        appString(R.string.send_kudo_panel_title),
                        color = SendInk, fontFamily = Montserrat, fontWeight = FontWeight.Bold, fontSize = 16.sp,
                    )
                    FieldLabel(appString(R.string.send_kudo_recipient_label), required = true)
                    RecipientField(uiState.recipientQuery, uiState.recipientResults, uiState.recipientError, onRecipientQueryChange, onSelectRecipient)
                    FieldLabel(appString(R.string.send_kudo_award_label), required = true)
                    TitleSelector(uiState.title, titleOptions, uiState.titleError, onSelectTitle)
                    MessageField(uiState.message, onMessageChange)
                    FieldLabel(appString(R.string.send_kudo_hashtag_label), required = true)
                    HashtagField(uiState.selectedHashtags, hashtagOptions, onToggleHashtag)
                    FieldLabel(appString(R.string.send_kudo_image_label))
                    ImageStrip(uiState.imageCount, onAddImage, onRemoveImage)
                    AnonymousField(uiState.anonymous, uiState.nickname, uiState.nicknameError, onToggleAnonymous, onNicknameChange)
                }
                ActionButtons(onCancel = onCancel, onSend = onSend)
                Spacer(Modifier.height(8.dp))
            }
            SaaBottomBar(
                active = BottomTab.KUDOS,
                onSaa = onSaaTab, onAwards = onAwardsTab, onKudos = onKudosTab, onProfile = onProfileTab,
            )
        }
    }
}

@Composable
private fun TopBar(title: String, onBack: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().height(44.dp).padding(horizontal = 12.dp),
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
        Text(title, color = SaaOnDark, fontFamily = Montserrat, fontWeight = FontWeight.Medium, fontSize = 17.sp)
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, backgroundColor = 0xFF00101A, widthDp = 375, heightDp = 900)
@Composable
private fun SendKudoScreenPreview() {
    ProvideAppLanguage(language = AppLanguage.VN) {
        SendKudoScreen(
            uiState = SendKudoUiState(
                recipientQuery = "Dương Huỳnh Xuân Nhật",
                selectedHashtags = listOf("#BE OPTIMISTIC", "#WASSHOI"),
                imageCount = 3, anonymous = true, nickname = "Doraemon",
            ),
            titleOptions = listOf("NGƯỜI HÙNG CỦA LÒNG EM"),
            hashtagOptions = listOf("#BE A TEAM", "#GO FAST"),
            onRecipientQueryChange = {}, onSelectRecipient = {}, onSelectTitle = {}, onMessageChange = {},
            onToggleHashtag = {}, onAddImage = {}, onRemoveImage = {}, onToggleAnonymous = {}, onNicknameChange = {},
            onCancel = {}, onSend = {}, onSaaTab = {}, onKudosTab = {}, onAwardsTab = {}, onProfileTab = {},
        )
    }
}
