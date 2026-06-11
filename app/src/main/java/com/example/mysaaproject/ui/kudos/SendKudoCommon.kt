package com.example.mysaaproject.ui.kudos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaBorderMuted
import com.example.mysaaproject.ui.theme.SaaOnButton

/** Shared tokens + small field building blocks for the New Kudo form (dark text on the cream panel). */
internal val SendInk = SaaOnButton
internal val SendInkMuted = SaaOnButton.copy(alpha = 0.5f)
internal val SendRequired = Color(0xFFE53935)
internal val SendFieldBg = Color(0xFFFFFFFF)

/** Bold field label with an optional required asterisk. */
@Composable
internal fun FieldLabel(text: String, required: Boolean = false) {
    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(text, color = SendInk, fontFamily = Montserrat, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        if (required) {
            Text("*", color = SendRequired, fontFamily = Montserrat, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}

/** White-on-cream outlined text field used across the form. */
@Composable
internal fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    singleLine: Boolean = true,
    minLines: Int = 1,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(placeholder, fontFamily = Montserrat, fontSize = 14.sp, color = SendInkMuted) },
        textStyle = TextStyle(fontFamily = Montserrat, fontSize = 14.sp, color = SendInk),
        isError = isError,
        singleLine = singleLine,
        minLines = minLines,
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = SendFieldBg,
            unfocusedContainerColor = SendFieldBg,
            errorContainerColor = SendFieldBg,
            focusedBorderColor = SaaBorderMuted,
            unfocusedBorderColor = SaaBorderMuted,
            cursorColor = SendInk,
        ),
    )
}

/** Bordered "+ X (Max 5)" pill used by the Hashtag and Image add actions. */
@Composable
internal fun AddPillButton(label: String, suffix: String? = null, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, SaaBorderMuted, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(label, color = SendInk, fontFamily = Montserrat, fontWeight = FontWeight.Medium, fontSize = 13.sp)
        if (suffix != null) {
            Text(suffix, color = SendInkMuted, fontFamily = Montserrat, fontSize = 12.sp)
        }
    }
}

/** Static (non-functional) formatting toolbar glyph for the message editor. */
@Composable
internal fun ToolbarGlyph(symbol: String, bold: Boolean = false, italic: Boolean = false, strike: Boolean = false) {
    Text(
        text = symbol,
        color = SendInkMuted,
        fontFamily = Montserrat,
        fontSize = 14.sp,
        fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
        fontStyle = if (italic) FontStyle.Italic else FontStyle.Normal,
        textDecoration = if (strike) TextDecoration.LineThrough else null,
    )
}

/** Filled action button (Huỷ / Gửi đi) with a trailing glyph. */
@Composable
internal fun ActionButton(label: String, glyph: String, bg: Color, fg: Color, modifier: Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bg)
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(label, color = fg, fontFamily = Montserrat, fontWeight = FontWeight.Bold, fontSize = 15.sp)
        Text("  $glyph", color = fg, fontSize = 14.sp)
    }
}
