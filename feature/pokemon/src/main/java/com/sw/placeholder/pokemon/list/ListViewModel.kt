package com.sw.placeholder.pokemon.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sw.placeholder.domain.GetAppUseCase
import com.sw.placeholder.model.Result
import com.sw.placeholder.pokemon.list.ListScreenUIState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getListUseCase: GetAppUseCase,
) :
    ViewModel() {
    private val _listScreenUiState = MutableStateFlow<ListScreenUIState>(ListScreenUIState.None)
    val listScreenUiState: StateFlow<ListScreenUIState> = _listScreenUiState

    private var fullList = mutableListOf<Result>()

    init {
        getData()
    }

    private fun getData() {
        getListUseCase()
            .map<List<Result>, ListScreenUIState> { items ->
                items.sortedBy { model -> model.name
                    fullList.addAll(items)
                }.map { model ->
                    ListModelUIState(
                        name = model.name,
                        url = model.url,
                    )
                }.let(::Success)
            }
            .onStart { _listScreenUiState.value = ListScreenUIState.Loading }
            .catch { throwable ->
                emit(ListScreenUIState.Error(throwable.message ?: "Unknown error"))
            }
            .onEach { state -> _listScreenUiState.value = state }
            .launchIn(viewModelScope)
    }

    fun retry() {
        _listScreenUiState.value = ListScreenUIState.Loading
        getData()
    }
}