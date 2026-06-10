package com.example.mysaaproject.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
 * Primary CTA: "LOGIN With Google" with the Google logo.
 * Shows a spinner and disables itself while [isLoading] (TC_LOGIN_FUN_006).
 */
@Composable
fun GoogleLoginButton(
    label: String,
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        enabled = !isLoading,
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = SaaButton,
            contentColor = SaaOnButton,
            disabledContainerColor = SaaButton,
            disabledContentColor = SaaOnButton,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = SaaOnButton,
                strokeWidth = 2.dp,
                modifier = Modifier.size(20.dp),
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = label,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                )
                Image(
                    painter = painterResource(R.drawable.ic_google),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}
