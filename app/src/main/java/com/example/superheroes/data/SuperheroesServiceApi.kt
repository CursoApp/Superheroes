package com.example.superheroes.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SuperheroesServiceApi {

    @GET("search/{name}")
    suspend fun searchByName(@Path("name") query:String) : Response<SuperheroesResponse>

    @GET("{id}")
    suspend fun findById(@Path("id") identifier:String) : Response<Superhero>
}



/*
Esto es de GIT HUB del profesor:

import retrofit2.http.GET
import retrofit2.http.Path

interface SuperheroApiService {
    @GET("search/{name}")
    suspend fun findSuperheroesByName(@Path("name") query: String) : SuperheroListResponse
}*/
