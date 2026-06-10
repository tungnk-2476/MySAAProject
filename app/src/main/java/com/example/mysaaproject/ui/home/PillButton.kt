package com.example.mysaaproject.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysaaproject.R
import com.example.mysaaproject.ui.theme.Montserrat
import com.example.mysaaproject.ui.theme.SaaButton
import com.example.mysaaproject.ui.theme.SaaOnButton

/**
 * Gold call-to-action used for ABOUT AWARD / ABOUT KUDOS / Kudos "Chi tiết".
 * Label followed by an outward-arrow icon, matching the MoMorph button component
 * (gold #FFEA9E, 4dp radius, 40dp tall, 12dp inset, 8dp gap).
 */
@Composable
fun PillButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = SaaButton,
            contentColor = SaaOnButton,
        ),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        modifier = modifier.height(40.dp),
    ) {
        Text(
            text = label,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter = painterResource(R.drawable.ic_arrow_outward),
            contentDescription = null,
            tint = SaaOnButton,
            modifier = Modifier.size(16.dp),
        )
    }
}
