package com.sonifadhilah0132.dexlite.ui.screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonifadhilah0132.dexlite.models.Pokemon
import com.sonifadhilah0132.dexlite.network.PokemonApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    var data = mutableStateOf(emptyList<Pokemon>())

    init {
        retrieveRandomPokemon()
    }

    fun retrieveData(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                data.value = PokemonApi.service.getUserPokemon(userId)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Success: ${e.message}")
            }
        }
    }

    private fun retrieveRandomPokemon() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                data.value = PokemonApi.service.getRandomPokemon()
            } catch (e: Exception) {
                Log.d("MainViewModelRandom", "Success: ${e.message}")
            }
        }
    }
}