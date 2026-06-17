package com.example.mysaaproject

import com.example.mysaaproject.data.kudos.KudosRepository
import com.example.mysaaproject.ui.kudos.SearchSunnerViewModel
import com.example.mysaaproject.ui.kudos.SendKudoViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** Verifies the New Kudo form validation and the hashtag multi-select cap. */
class SendKudoViewModelTest {

    @Test
    fun validate_failsWhenRequiredFieldsMissing() {
        val vm = SendKudoViewModel()
        assertFalse(vm.validate())
        val s = vm.uiState.value
        assertTrue(s.recipientError)
        assertTrue(s.titleError)
        assertTrue(s.hashtagError)
    }

    @Test
    fun validate_passesWhenAllRequiredProvided() {
        val vm = SendKudoViewModel()
        vm.onSelectRecipient(KudosRepository.kudoRecipients.first())
        vm.onSelectTitle(KudosRepository.titleOptions.first())
        vm.onToggleHashtag(KudosRepository.sendHashtagOptions.first())
        assertTrue(vm.validate())
    }

    @Test
    fun validate_anonymousRequiresNickname() {
        val vm = SendKudoViewModel()
        vm.onSelectRecipient(KudosRepository.kudoRecipients.first())
        vm.onSelectTitle(KudosRepository.titleOptions.first())
        vm.onToggleHashtag(KudosRepository.sendHashtagOptions.first())
        vm.onToggleAnonymous()
        assertFalse(vm.validate())
        assertTrue(vm.uiState.value.nicknameError)

        vm.onNicknameChange("Doraemon")
        assertTrue(vm.validate())
    }

    @Test
    fun submit_failsAndAddsNothingWhenInvalid() {
        val before = KudosRepository.feed.value.size
        val vm = SendKudoViewModel()
        assertFalse(vm.submit())
        assertEquals(before, KudosRepository.feed.value.size)
    }

    @Test
    fun submit_prependsComposedKudoToSharedFeed() {
        val before = KudosRepository.feed.value.size
        val vm = SendKudoViewModel()
        val recipient = KudosRepository.kudoRecipients.first()
        val title = KudosRepository.titleOptions.first()
        val tag = KudosRepository.sendHashtagOptions.first()
        vm.onSelectRecipient(recipient)
        vm.onSelectTitle(title)
        vm.onToggleHashtag(tag)
        vm.onMessageChange("Cảm ơn bạn rất nhiều!")

        assertTrue(vm.submit())

        val feed = KudosRepository.feed.value
        assertEquals(before + 1, feed.size)
        val added = feed.first()
        assertEquals(recipient.name, added.receiverName)
        assertEquals(title, added.title)
        assertEquals(listOf(tag), added.hashtags)
        assertEquals("Cảm ơn bạn rất nhiều!", added.contentText)
        assertEquals(0, added.hearts)
        // The new kudo is resolvable by the View Kudo detail screen.
        assertEquals(added, KudosRepository.findById(added.id))
    }

    @Test
    fun toggleHashtag_addsRemovesAndCapsAtFive() {
        val base = listOf("a", "b", "c", "d", "e")
        assertEquals(listOf("a", "b"), SendKudoViewModel.toggleHashtag(listOf("a"), "b"))
        assertEquals(listOf("a"), SendKudoViewModel.toggleHashtag(listOf("a", "b"), "b"))
        assertEquals(base, SendKudoViewModel.toggleHashtag(base, "f")) // capped at 5
    }

    @Test
    fun recipientSearch_filtersByNameAndSelectionFillsQuery() {
        val vm = SendKudoViewModel()
        vm.onRecipientQueryChange("Dương")
        assertTrue(vm.uiState.value.recipientResults.isNotEmpty())
        assertTrue(vm.uiState.value.recipientResults.all { it.name.contains("Dương", ignoreCase = true) })

        val pick = vm.uiState.value.recipientResults.first()
        vm.onSelectRecipient(pick)
        assertEquals(pick, vm.uiState.value.selectedRecipient)
        assertEquals(pick.name, vm.uiState.value.recipientQuery)
        assertTrue(vm.uiState.value.recipientResults.isEmpty())
    }

    @Test
    fun searchSunner_removeRecent_removesOnlyThatItem() {
        val vm = SearchSunnerViewModel()
        val initial = vm.recent.value
        assertTrue(initial.size >= 2)
        val target = initial.first()
        vm.removeRecent(target.id)
        assertFalse(vm.recent.value.any { it.id == target.id })
        assertEquals(initial.size - 1, vm.recent.value.size)
    }
}
