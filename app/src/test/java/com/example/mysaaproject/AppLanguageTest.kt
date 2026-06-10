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
}
