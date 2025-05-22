package com.sw.placeholder.pokemon.list

sealed interface ListScreenUIState {
    data object None : ListScreenUIState
    data object Loading : ListScreenUIState
    data class Success(val list: List<ListModelUIState>) : ListScreenUIState
    data class Error(val message: String) : ListScreenUIState
}