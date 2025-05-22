package com.sw.placeholder.comments.detail

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sw.placeholder.domain.GetAppDetailUseCase
import com.sw.placeholder.pokemon.detail.DetailModelData
import com.sw.placeholder.pokemon.detail.DetailScreenUIState
import com.sw.placeholder.domain.FavoriteManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailUseCase: GetAppDetailUseCase,
    @ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val uiState: StateFlow<DetailScreenUIState> = savedStateHandle
        .getStateFlow<String?>("name", "")
        .filterNotNull()
        .flatMapLatest {  data ->
            getDetailUseCase(data)
        }.map { model ->
            DetailScreenUIState.Success(
                DetailModelData(
                name = model?.name ?: "",
                imageURL = model?.sprites?.front_default?:"",
                height = model?.height?:0,
                weight = model?.weight?:0,
                isFav = model?.isFav?:false
                )
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DetailScreenUIState.Loading)

    val favoriteNames: StateFlow<Set<String>> = FavoriteManager.getFavorites(context)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val favorites: StateFlow<List<DetailModelData>> = favoriteNames
        .flatMapLatest { names ->
            if (names.isEmpty()) flowOf(emptyList())
            else combine(names.map { name ->
                getDetailUseCase(name)
                    .map { model ->
                        DetailModelData(
                            name = model?.name ?: "",
                            imageURL = model?.sprites?.front_default ?: "",
                            height = model?.height ?: 0,
                            weight = model?.weight ?: 0,
                            isFav = true
                        )
                    }
            }) { it.toList() }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun removeFavorite(name: String) {
        viewModelScope.launch {
           getDetailUseCase.removeFavorite(name, context)
        }
    }

    fun retry() {

    }
}