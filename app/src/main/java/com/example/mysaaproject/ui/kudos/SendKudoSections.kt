package com.example.mysaaproject.ui.kudos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.example.mysaaproject.ui.locale.appString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaBorderMuted
import com.example.mysaaproject.ui.theme.SaaButton
import com.example.mysaaproject.ui.theme.SaaDropdownSurface
import com.example.mysaaproject.ui.theme.SaaOnButton
import com.example.mysaaproject.ui.theme.SaaOnDark

/** Danh hiệu (award title) picker: a read-only field that opens a dark single-select dropdown. */
@Composable
fun TitleSelector(selected: Int?, options: List<Int>, isError: Boolean, onSelect: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(SendFieldBg)
                .border(1.dp, if (isError) SendRequired else SaaBorderMuted, RoundedCornerShape(8.dp))
                .clickable { expanded = true }
                .padding(horizontal = 14.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = if (selected != null) appString(selected) else appString(R.string.send_kudo_award_hint),
                color = if (selected == null) SendInkMuted else SendInk,
                fontFamily = Montserrat,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f),
            )
            Icon(painterResource(R.drawable.ic_arrow_down), null, tint = SendInkMuted, modifier = Modifier.size(20.dp))
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = SaaDropdownSurface,
            shape = RoundedCornerShape(8.dp),
            tonalElevation = 0.dp,
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            appString(option),
                            color = SaaOnDark,
                            fontFamily = Montserrat,
                            fontWeight = if (option == selected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp,
                        )
                    },
                    onClick = { onSelect(option); expanded = false },
                )
            }
        }
    }
}

/** Message editor: a static formatting toolbar (visual only) over a plain multiline field + helper text. */
@Composable
fun MessageField(value: String, onValueChange: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ToolbarGlyph("B", bold = true)
            ToolbarGlyph("I", italic = true)
            ToolbarGlyph("S", strike = true)
            ToolbarGlyph("1.")
            Icon(painterResource(R.drawable.ic_link), null, tint = SendInkMuted, modifier = Modifier.size(18.dp))
            ToolbarGlyph("❝")
        }
        FormTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = appString(R.string.send_kudo_message_hint),
            singleLine = false,
            minLines = 4,
        )
        Text(
            appString(R.string.send_kudo_message_helper),
            color = SendInkMuted,
            fontFamily = Montserrat,
            fontSize = 11.sp,
        )
    }
}

/**
 * Attached-image placeholders (gray tiles, each removable) + a "+ Image" add button (max 5).
 * Tiles are interchangeable placeholders, so every ✕ simply removes one (the count model has no per-slot id).
 */
@Composable
fun ImageStrip(count: Int, onAdd: () -> Unit, onRemove: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (count > 0) {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(count) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(SendInk.copy(alpha = 0.18f)),
                    ) {
                        Text(
                            "✕",
                            color = SaaOnDark,
                            fontSize = 11.sp,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(2.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(SendInk.copy(alpha = 0.6f))
                                .clickable(onClick = onRemove)
                                .padding(horizontal = 4.dp),
                        )
                    }
                }
            }
        }
        if (count < SendKudoViewModel.MAX_IMAGES) {
            AddPillButton(appString(R.string.send_kudo_add_image), appString(R.string.send_kudo_max5), onAdd)
        }
    }
}

/** Anonymous toggle (checkbox + label) followed by the always-visible nickname field. */
@Composable
fun AnonymousField(
    anonymous: Boolean,
    nickname: String,
    nicknameError: Boolean,
    onToggle: () -> Unit,
    onNicknameChange: (String) -> Unit,
) {
    Row(
        modifier = Modifier.clickable(onClick = onToggle),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(if (anonymous) SaaButton else SendFieldBg)
                .border(1.dp, SaaBorderMuted, RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center,
        ) {
            if (anonymous) Text("✓", color = SaaOnButton, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
        Text(appString(R.string.send_kudo_anonymous), color = SendInk, fontFamily = Montserrat, fontSize = 13.sp)
    }
    FieldLabel(appString(R.string.send_kudo_nickname_label), required = anonymous)
    FormTextField(
        value = nickname,
        onValueChange = onNicknameChange,
        placeholder = appString(R.string.send_kudo_nickname_hint),
        isError = nicknameError,
    )
}

/** Bottom actions: "Huỷ" (dark) + "Gửi đi" (gold). */
@Composable
fun ActionButtons(onCancel: () -> Unit, onSend: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        ActionButton(appString(R.string.send_kudo_cancel), "✕", SaaOnButton, SaaOnDark, Modifier.weight(1f), onCancel)
        ActionButton(appString(R.string.send_kudo_send), "➤", SaaButton, SaaOnButton, Modifier.weight(1f), onSend)
    }
}
