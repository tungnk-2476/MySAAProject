package com.example.mysaaproject

import com.example.mysaaproject.data.awards.AwardsRepository
import com.example.mysaaproject.ui.awards.AwardViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/** Verifies the Award Detail category selection (default + swap + unknown-id is ignored). */
class AwardViewModelTest {

    @Test
    fun selectedAward_defaultsToFirst() {
        val vm = AwardViewModel()
        assertEquals(AwardsRepository.MOCK_AWARDS.first(), vm.selectedAward.value)
    }

    @Test
    fun selectAward_swapsSelectedById() {
        val vm = AwardViewModel()
        val target = AwardsRepository.MOCK_AWARDS.last()
        assertTrue(AwardsRepository.MOCK_AWARDS.size >= 2)
        vm.selectAward(target.id)
        assertEquals(target, vm.selectedAward.value)
    }

    @Test
    fun selectAward_ignoresUnknownId() {
        val vm = AwardViewModel()
        val before = vm.selectedAward.value
        vm.selectAward("does-not-exist")
        assertEquals(before, vm.selectedAward.value)
    }
}
