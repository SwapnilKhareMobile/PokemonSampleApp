package com.sw.placeholder.pokemon.detail

sealed interface DetailScreenUIState {
    data object None : DetailScreenUIState
    data object Loading : DetailScreenUIState
    data class Success(val detail: DetailModelData) : DetailScreenUIState
    data class Error(val message: String) : DetailScreenUIState

}