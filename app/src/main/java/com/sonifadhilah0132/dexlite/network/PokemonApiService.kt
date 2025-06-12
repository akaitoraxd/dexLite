package com.sonifadhilah0132.dexlite.network

import com.sonifadhilah0132.dexlite.models.Pokemon
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val URL = "https://terrible-pokeapi-production.up.railway.app/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(URL)
    .build()

interface PokemonApiService {
    @GET("randomPokemon")
    suspend fun getRandomPokemon(): List<Pokemon>

    @GET("pokemon/{userId}")
    suspend fun getUserPokemon(
        @retrofit2.http.Path("userId") userId: Int
    ): List<Pokemon>
}

object PokemonApi {
    val service: PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }

}

enum class ApiStatus { LOADING, SUCCESS }