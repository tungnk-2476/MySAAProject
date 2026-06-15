package com.example.mysaaproject

import com.example.mysaaproject.ui.locale.AppLanguage
import org.junit.Assert.assertEquals
import org.junit.Test

class AppLanguageTest {

    @Test
    fun defaultLanguageIsVietnamese() {
        assertEquals(AppLanguage.VN, AppLanguage.DEFAULT)
    }

    @Test
    fun supportsExactlyVnEn() {
        assertEquals(listOf("VN", "EN"), AppLanguage.entries.map { it.code })
    }

    @Test
    fun localeTagsMapToResourceQualifiers() {
        assertEquals("vi", AppLanguage.VN.localeTag)
        assertEquals("en", AppLanguage.EN.localeTag)
    }

    @Test
    fun fromTag_resolvesKnownTags() {
        assertEquals(AppLanguage.VN, AppLanguage.fromTag("vi"))
        assertEquals(AppLanguage.EN, AppLanguage.fromTag("en"))
    }

    @Test
    fun fromTag_unknownOrNull_fallsBackToDefault() {
        assertEquals(AppLanguage.DEFAULT, AppLanguage.fromTag(null))
        assertEquals(AppLanguage.DEFAULT, AppLanguage.fromTag(""))
        assertEquals(AppLanguage.DEFAULT, AppLanguage.fromTag("fr"))
    }
}
