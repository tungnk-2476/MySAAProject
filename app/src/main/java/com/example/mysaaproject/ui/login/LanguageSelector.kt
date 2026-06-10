package com.example.mysaaproject.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.ui.locale.AppLanguage
import com.example.mysaaproject.ui.theme.Montserrat

/**
 * Language selector: flag + language code + dropdown arrow.
 * Opens a dropdown listing the supported languages (VN / EN).
 */
@Composable
fun LanguageSelector(
    selected: AppLanguage,
    onSelect: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.clickable { expanded = true },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        FlagIcon(selected)
        Text(
            text = selected.code,
            color = Color.White,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
        )
        Image(
            painter = painterResource(R.drawable.ic_arrow_down),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFF0A2231)),
        ) {
            AppLanguage.entries.forEach { language ->
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            FlagIcon(language)
                            Text(
                                text = language.code,
                                color = Color.White,
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                            )
                        }
                    },
                    onClick = {
                        expanded = false
                        onSelect(language)
                    },
                )
            }
        }
    }
}

@Composable
private fun FlagIcon(language: AppLanguage) {
    Image(
        painter = painterResource(language.flagRes),
        contentDescription = language.code,
        modifier = Modifier
            .width(24.dp)
            .height(16.dp)
            .clip(RoundedCornerShape(2.dp)),
    )
}
