package com.sw.placeholder.pokemon.detail

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.sw.placeholder.comments.detail.DetailViewModel
import com.sw.placeholder.common.AppError
import com.sw.placeholder.common.AppLoading
import com.sw.placeholder.common.MyAppTopBar
import com.sw.placeholder.domain.FavoriteManager
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onClickFav: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    Scaffold(topBar = {
        MyAppTopBar(
            title = "Detail",
            onClick = onClick,
            isBackNavigation = true
        )
    }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            val detailUIState by viewModel.uiState.collectAsStateWithLifecycle()
            val favorites by viewModel.favorites.collectAsStateWithLifecycle()

            when (detailUIState) {
                is DetailScreenUIState.Error -> AppError(modifier) { viewModel.retry() }
                DetailScreenUIState.Loading -> AppLoading(modifier)
                is DetailScreenUIState.Success -> {
                    val detail = (detailUIState as DetailScreenUIState.Success).detail
                    val isFav = favorites.any { it.name == detail.name }
                    DetailCard(
                        modifier,
                        detail,
                        context,
                        isFav,
                        onClickFav
                    )
                }
                DetailScreenUIState.None -> {}
            }

        }
    }
}

@Composable
fun DetailCard(
    modifier: Modifier,
    value: DetailModelData,
    context: Context,
    isFavFromStore: Boolean,
    onClickFav: () -> Unit
) {
    var isFav = isFavFromStore
    val scope = rememberCoroutineScope()

    Column {
        Card(
            modifier = modifier.padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Box {
                    AsyncImage(
                        modifier = Modifier.size(200.dp),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(value.imageURL)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .build(),
                        contentDescription = "Image"
                    )
                    IconButton(
                        onClick = {
                            isFav = !isFav
                            scope.launch {
                                if (isFav) {
                                    FavoriteManager.addFavorite(context, value.name)
                                } else {
                                    FavoriteManager.removeFavorite(context, value.name)
                                }
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(32.dp)
                    ) {
                        Icon(
                            imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (isFav) "Unfavorite" else "Favorite",
                            tint = if (isFav) Color.Red else Color.Gray
                        )
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = value.name, fontSize = 24.sp)
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = "Height: ${value.height}")
                HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(vertical = 10.dp))
                Text(text = "Weight: ${value.weight}")
                HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(vertical = 10.dp))
            }
        }
        Spacer(modifier = Modifier.size(20.dp))
        Button(
            onClick = { onClickFav() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Show Favorite")
        }
        Spacer(modifier = Modifier.size(20.dp))
    }
}

