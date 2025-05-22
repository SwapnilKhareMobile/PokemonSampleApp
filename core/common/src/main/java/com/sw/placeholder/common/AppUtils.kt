package com.sw.placeholder.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

const val BASE_URL = "https://pokeapi.co/api/v2/"
const val POKEMON_API = "pokemon"
const val POKEMON_DETAIL_API = "pokemon/{name}"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
    isBackNavigation: Boolean
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
        title = { Text(text = title) },
        navigationIcon = {
            if (isBackNavigation) {
                BackButton(onClick = onClick)
            }
        }
    )
}

@Composable
fun BackButton(onClick: () -> Unit) {
    var clicked by remember { mutableStateOf(false) }

    IconButton(onClick = { clicked = !clicked }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back"
        )
    }
    LaunchedEffect(clicked) {
        if (clicked) {
            onClick()
        }
    }
}

@Composable
fun AppLoading(modifier: Modifier = Modifier){
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        CircularProgressIndicator()
    }
}

@Composable
fun AppError(modifier: Modifier = Modifier, onClick: () -> Unit){
    Column (modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "Something went wrong, Please try again!")
        Spacer(modifier = Modifier.size(10.dp))
        Button(onClick = onClick) {
            Text(text = "Retry")
        }
    }
}