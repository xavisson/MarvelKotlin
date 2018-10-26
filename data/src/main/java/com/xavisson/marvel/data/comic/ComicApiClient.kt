package com.xavisson.marvel.data.comic

import com.xavisson.marvel.data.BuildConfig
import com.xavisson.marvel.data.character.CharactersApiDefinition
import com.xavisson.marvel.data.character.SearchCharactersApiModel
import com.xavisson.marvel.data.character.toDomain
import com.xavisson.marvel.data.client.ApiClientBuilder
import com.xavisson.marvel.data.client.MarvelApiClient
import com.xavisson.marvel.data.utils.getHash
import com.xavisson.marvel.domain.comic.ComicApi
import com.xavisson.marvel.domain.comic.ComicItem
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class ComicApiClient : ComicApi {

    override fun getComicsFromCharacterId(characterId: Int): Observable<List<ComicItem>> {

        val marvelApiClient = ApiClientBuilder(
                MarvelApiClient(emptySet())
        ).buildEndpoint(ComicApiDefinition::class)

        val publicAPIKey = BuildConfig.PublicApiKey
        val timestamp = java.lang.Long.toString(System.currentTimeMillis() / 1000)
        val hash = getHash(timestamp)

        return marvelApiClient.getComics(
                characterId = characterId,
                publicKey = publicAPIKey,
                timestamp = timestamp,
                hash = hash
        ).map { it.toDomain() }
    }
}

interface ComicApiDefinition {

    @GET("/v1/public/characters/{characterId}/comics")
    fun getComics(
            @Path("characterId") characterId: Int,
            @Query("apikey") publicKey: String,
            @Query("hash") hash: String,
            @Query("ts") timestamp: String
    ): Observable<SearchComicsApiModel>
}