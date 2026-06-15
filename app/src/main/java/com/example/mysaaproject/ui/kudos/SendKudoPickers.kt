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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.window.PopupProperties
import com.example.mysaaproject.R
import com.example.mysaaproject.data.kudos.Recipient
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaBorderMuted
import com.example.mysaaproject.ui.theme.SaaDropdownSurface
import com.example.mysaaproject.ui.theme.SaaOnDark

/** "Người nhận" search field with a dark dropdown of results (avatar + name + unit); single-select. */
@Composable
fun RecipientField(
    query: String,
    results: List<Recipient>,
    isError: Boolean,
    onQueryChange: (String) -> Unit,
    onSelect: (Recipient) -> Unit,
) {
    Box {
        FormTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = appString(R.string.send_kudo_recipient_hint),
            isError = isError,
            trailingIcon = {
                Icon(painterResource(R.drawable.ic_arrow_down), null, tint = SendInkMuted, modifier = Modifier.size(20.dp))
            },
        )
        DropdownMenu(
            expanded = results.isNotEmpty(),
            onDismissRequest = {},
            properties = PopupProperties(focusable = false),
            containerColor = SaaDropdownSurface,
            shape = RoundedCornerShape(8.dp),
            tonalElevation = 0.dp,
            modifier = Modifier.width(320.dp),
        ) {
            results.forEach { r ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Avatar(r.name, size = 36.dp)
                            Column {
                                Text(r.name, color = SaaOnDark, fontFamily = Montserrat, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                                Text(r.unit, color = SaaOnDark.copy(alpha = 0.6f), fontFamily = Montserrat, fontSize = 12.sp)
                            }
                        }
                    },
                    onClick = { onSelect(r) },
                )
            }
        }
    }
}

/** Hashtag multi-select: removable chips + a "+ Hashtag" dark checklist dropdown (max 5). */
@Composable
fun HashtagField(
    selected: List<String>,
    options: List<String>,
    onToggle: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        selected.forEach { tag -> HashtagChip(tag, onRemove = { onToggle(tag) }) }
        Box {
            AddPillButton(
                label = appString(R.string.send_kudo_add_hashtag),
                suffix = appString(R.string.send_kudo_max5),
                onClick = { expanded = true },
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = SaaDropdownSurface,
                shape = RoundedCornerShape(8.dp),
                tonalElevation = 0.dp,
                modifier = Modifier.width(280.dp),
            ) {
                options.forEach { option ->
                    val checked = option in selected
                    DropdownMenuItem(
                        text = {
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Text(option, color = SaaOnDark, fontFamily = Montserrat, fontSize = 14.sp, modifier = Modifier.weight(1f))
                                if (checked) Text("✓", color = SaaOnDark, fontSize = 16.sp)
                            }
                        },
                        onClick = { onToggle(option) },
                    )
                }
            }
        }
    }
}

@Composable
private fun HashtagChip(tag: String, onRemove: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SendFieldBg)
            .border(1.dp, SaaBorderMuted, RoundedCornerShape(8.dp))
            .padding(start = 12.dp, end = 8.dp, top = 6.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(tag, color = SendInk, fontFamily = Montserrat, fontWeight = FontWeight.Medium, fontSize = 13.sp)
        Text("✕", color = SendInkMuted, fontSize = 13.sp, modifier = Modifier.clickable(onClick = onRemove))
    }
}
