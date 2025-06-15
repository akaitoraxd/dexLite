package com.sonifadhilah0132.dexlite.ui.screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonifadhilah0132.dexlite.models.Pokemon
import com.sonifadhilah0132.dexlite.network.ApiStatus
import com.sonifadhilah0132.dexlite.network.PokemonApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    var data = mutableStateOf(emptyList<Pokemon>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    init {
        retrieveRandomPokemon()
    }

    private fun retrieveData(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                data.value = PokemonApi.service.getUserPokemon(userId)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Success: ${e.message}")
            }
        }
    }

    fun retrieveRandomPokemon() {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            try {
                data.value = PokemonApi.service.getRandomPokemon()
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModelRandom", "Success: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }

    fun clearMessage() { errorMessage.value = null}
}