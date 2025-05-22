package com.sw.placeholder.pokemon.list

import app.cash.turbine.test
import com.sw.placeholder.domain.GetAppUseCase
import com.sw.placeholder.model.Result
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import java.io.IOException
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest {

    private val mainDispatcherRule = StandardTestDispatcher()

    private lateinit var viewModel: ListViewModel

    private val getListUseCase: GetAppUseCase = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainDispatcherRule)
        every { getListUseCase() } returns flowOf(sampleData)

        viewModel =ListViewModel(getListUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `ViewModel emits Success state when loaded successfully`() = runTest {
        val viewModel =ListViewModel(getListUseCase)
        viewModel.listScreenUiState.test {
            assertEquals(ListScreenUIState.None, awaitItem())
            assertEquals(ListScreenUIState.Loading, awaitItem())
            assertTrue(awaitItem() is ListScreenUIState.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `ViewModel emits Error state when fetching fails`() = runTest {
        every { getListUseCase() } returns flow { throw IOException("Network error") }

        viewModel = ListViewModel(getListUseCase)

        viewModel.listScreenUiState.test {
            assertEquals(ListScreenUIState.None, awaitItem())
            assertEquals(ListScreenUIState.Loading, awaitItem())
            val errorState = awaitItem() as ListScreenUIState.Error
            assertEquals("Network error", errorState.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `ViewModel retries and fetches data again`() = runTest {
        every { getListUseCase() } returns flowOf(emptyList())

        viewModel = ListViewModel(getListUseCase)

        viewModel.retry()

        viewModel.listScreenUiState.test {
            assertEquals(ListScreenUIState.Loading, awaitItem())
            assertTrue(awaitItem() is ListScreenUIState.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }

    companion object {
        private val sampleData = listOf(
            Result(

                name = "John",
                url = "URL 1",
            ),
            Result(
                name = "Mathew",
                url = "URL 2",
            )
        )
    }
}