import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sw.placeholder.pokemon.list.ListViewModel
import com.sw.placeholder.pokemon.list.ListModelUIState
import com.sw.placeholder.pokemon.list.ListScreenUIState
import com.sw.placeholder.common.AppError
import com.sw.placeholder.common.AppLoading
import com.sw.placeholder.common.MyAppTopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
    viewModel: ListViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        MyAppTopBar(
            title = "List",
            onClick = {},
            isBackNavigation = false
        )
    }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            val uiState = viewModel.listScreenUiState.collectAsStateWithLifecycle()
            when (uiState.value) {
                is ListScreenUIState.Error -> AppError(modifier, {viewModel.retry()})
                ListScreenUIState.Loading -> AppLoading(modifier)
                is ListScreenUIState.Success -> {
                    val list = (uiState.value as ListScreenUIState.Success).list
                    Column () {
                        LazyColumn {
                            items(list) {
                                ListItem(modifier = modifier, list, onClick)
                            }
                        }
                    }

                }

                ListScreenUIState.None -> {}
            }
        }
    }
}

@Composable
fun ListItem(
    modifier: Modifier,
    list: List<ListModelUIState>,
    onClick: (String) -> Unit
) {
    for (item in list) {
        Card(
            modifier = modifier.fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    onClick(
                        item.name
                    )
                },
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Column(modifier = modifier.padding(10.dp)) {
                Text(text = item.name, fontSize = 24.sp)
            }

        }
    }
}