package com.sw.placeholder.pokemon.detail

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.sw.placeholder.comments.detail.DetailViewModel
import com.sw.placeholder.domain.FavoriteManager
import com.sw.placeholder.domain.GetAppDetailUseCase
import com.sw.placeholder.model.detail.Ability
import com.sw.placeholder.model.detail.AppDetailResponse
import com.sw.placeholder.model.detail.Cries
import com.sw.placeholder.model.detail.Form
import com.sw.placeholder.model.detail.GameIndice
import com.sw.placeholder.model.detail.PastAbility
import com.sw.placeholder.model.detail.Species
import com.sw.placeholder.model.detail.Sprites
import com.sw.placeholder.model.detail.Stat
import com.sw.placeholder.model.detail.StatX
import com.sw.placeholder.model.detail.Type
import com.sw.placeholder.model.detail.TypeX
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest  {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var savedStateHandle: SavedStateHandle
    private val testDispatcher = StandardTestDispatcher()
    private val context = mockk<Context>(relaxed = true)
    private val getDetailUseCase:GetAppDetailUseCase = mockk()
    private lateinit var viewModel: DetailViewModel

    private val sampleDetailModel = AppDetailResponse(
        abilities = listOf(Ability(null, false, 1)),
        base_experience = 64,
        cries = Cries("https://example.com/cry.mp3", ""),
        forms = listOf(Form("Normal", "")),
        game_indices = listOf(GameIndice(1, null)),
        height = 7,
        held_items = listOf(null),
        id = 1,
        is_default = true,
        location_area_encounters = "https://pokeapi.co/api/v2/pokemon/1/encounters",
        moves = null,
        name = "bulbasaur",
        order = 1,
        past_abilities = listOf(
            PastAbility(null, null),
        ),
        past_types = listOf(null),
        species = Species("bulbasaur", "url1"),
        sprites = Sprites("", Any(), "", Any(), "", Any(), "", Any(), null, null),
        stats = listOf(Stat(1, 45, StatX("", ""))),
        types = listOf(Type(1, TypeX("", ""))),
        weight = 10,
        isFav = true
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        savedStateHandle = SavedStateHandle(mapOf("name" to "bulbasaur"))
        every { getDetailUseCase("bulbasaur") } returns flowOf(sampleDetailModel)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    private fun createViewModel(): DetailViewModel {
        every { getDetailUseCase.invoke("bulbasaur") } returns flowOf(
            sampleDetailModel
        )

        mockkObject(FavoriteManager)
        every { FavoriteManager.getFavorites(context) } returns MutableStateFlow(setOf("bulbasaur"))

        return DetailViewModel(getDetailUseCase, context, savedStateHandle)
    }
    @Test
    fun `uiState emits Success when name is available in savedStateHandle`() = runTest(testDispatcher) {


        viewModel = createViewModel()
        val result = viewModel.uiState.first { it is DetailScreenUIState.Success }
        val success = result as DetailScreenUIState.Success

        assertEquals("bulbasaur", success.detail.name)
        assertEquals(7, success.detail.height)
        assertEquals(10, success.detail.weight)
        assertTrue(success.detail.isFav)
    }
}