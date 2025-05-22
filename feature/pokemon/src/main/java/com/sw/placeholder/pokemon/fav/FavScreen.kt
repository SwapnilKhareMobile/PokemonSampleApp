package com.sw.placeholder.pokemon.fav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sw.placeholder.comments.detail.DetailViewModel
import com.sw.placeholder.common.MyAppTopBar
import com.sw.placeholder.pokemon.detail.DetailModelData

@Composable
fun FavScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
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
            val favorites by viewModel.favorites.collectAsStateWithLifecycle()
            if (favorites.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No Favorites Found")
                }
            } else {
                LazyColumn {
                    items(favorites.size) { index ->
                        FavoriteItemCard(pokemon = favorites[index]) {
                            viewModel.removeFavorite(favorites[index].name)
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun FavoriteItemCard(pokemon: DetailModelData, onRemoveClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = pokemon.imageURL,
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(pokemon.name, fontSize = 20.sp, modifier = Modifier.weight(1f))
            IconButton(onClick = onRemoveClick) {
                Icon(Icons.Default.Delete, contentDescription = "Remove Favorite")
            }
        }
    }
}