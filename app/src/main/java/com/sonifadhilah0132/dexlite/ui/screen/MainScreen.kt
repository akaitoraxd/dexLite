package com.sonifadhilah0132.dexlite.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.sonifadhilah0132.dexlite.R
import com.sonifadhilah0132.dexlite.models.Pokemon
import com.sonifadhilah0132.dexlite.network.ApiStatus
import com.sonifadhilah0132.dexlite.ui.theme.DexLiteTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val viewModel: MainViewModel = viewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding -> ScreenContent(viewModel, Modifier.padding(innerPadding)) }
}

@Composable
fun ScreenContent(viewModel: MainViewModel, modifier: Modifier) {
    val data by viewModel.data
    val status by viewModel.status.collectAsState()

    when (status) {
        ApiStatus.LOADING -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        ApiStatus.SUCCESS -> {
            LazyVerticalGrid(
                modifier = modifier
                    .fillMaxSize()
                    .padding(4.dp),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(data) { ListItem(pokemon = it) }
            }
        }

        ApiStatus.FAILED -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.error))
                Button(
                    onClick = { viewModel.retrieveRandomPokemon() },
                    modifier = Modifier.padding(top = 16.dp),
                    contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
                ) {
                    Text(text = stringResource(id = R.string.try_again))
                }
            }
        }
    }
}

@Composable
fun ListItem(pokemon: Pokemon) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .border(1.dp, Color.Gray),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.sprite)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.sprite, pokemon.name),
                contentScale = ContentScale.Fit,
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.image_not_found),
                modifier = Modifier
                    .size(150.dp)
                    .padding(4.dp)
            )
            Text(text = pokemon.name, style = MaterialTheme.typography.titleMedium)
            Text(text = pokemon.types.joinToString(", "), style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    DexLiteTheme {
        MainScreen()
    }
}