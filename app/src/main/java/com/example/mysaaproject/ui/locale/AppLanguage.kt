package com.example.mysaaproject.ui.locale

import androidx.annotation.DrawableRes
import com.example.mysaaproject.R

/**
 * Supported display languages on the login screen.
 * [code] is the label shown in the selector; [localeTag] selects the string resources.
 */
enum class AppLanguage(
    val code: String,
    val localeTag: String,
    @param:DrawableRes val flagRes: Int,
) {
    VN("VN", "vi", R.drawable.ic_flag_vn),
    EN("EN", "en", R.drawable.ic_flag_gb);

    companion object {
        val DEFAULT = VN
    }
}
