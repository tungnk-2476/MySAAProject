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

        /** Resolve a persisted [localeTag] back to a language; unknown/null falls back to [DEFAULT]. */
        fun fromTag(tag: String?): AppLanguage = entries.firstOrNull { it.localeTag == tag } ?: DEFAULT
    }
}
