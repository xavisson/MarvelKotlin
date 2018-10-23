package com.xavisson.marvel.data.character

import com.xavisson.marvel.data.client.ApiClientBuilder
import com.xavisson.marvel.data.client.MarvelApiClient
import com.xavisson.marvel.domain.character.CharacterApi
import com.xavisson.marvel.domain.character.CharacterItem
import com.xavisson.marvel.domain.character.SearchCharacterItem
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class CharacterApiClient : CharacterApi {

    override fun searchForCharacter(searchText: String): Observable<List<CharacterItem>> {

        val marvelApiClient = ApiClientBuilder(
                MarvelApiClient(emptySet())
        ).buildEndpoint(BrewerydbApiDefinition::class)


        val publicAPIKey = ""
        val privateAPIKey = ""
        val ts = java.lang.Long.toString(System.currentTimeMillis() / 1000)
        var pass = ts + privateAPIKey + publicAPIKey
        var password: String? = null
        val mdEnc: MessageDigest
        try {
            mdEnc = MessageDigest.getInstance("MD5")
            mdEnc.update(pass.toByteArray(), 0, pass.length)
            pass = BigInteger(1, mdEnc.digest()).toString(16)
            while (pass.length < 32) {
                pass = "0$pass"
            }
            password = pass
        } catch (e1: NoSuchAlgorithmException) {
            e1.printStackTrace()
        }

        val hash = password
        return marvelApiClient.getCharacters(
                publicKey = publicAPIKey,
                timestamp = ts,
                hash = hash!!
        ).map { it.toDomain() }
    }
}

interface BrewerydbApiDefinition {

    @GET("/v1/public/characters")
    fun getCharacters(
            @Query("apikey") publicKey: String,
            @Query("hash") hash: String,
            @Query("ts") timestamp: String
    ): Observable<SearchCharactersApiModel>
}