package com.example.superheroes.data

import retrofit2.http.GET
import retrofit2.http.Path

interface SuperheroesApiService {
    @GET("search/{name}")
    suspend fun findSuperheroesByName(@Path("name") query: String) : SuperheroResponse
}



/*
Esto es de GIT HUB del profesor:

import retrofit2.http.GET
import retrofit2.http.Path

interface SuperheroApiService {
    @GET("search/{name}")
    suspend fun findSuperheroesByName(@Path("name") query: String) : SuperheroListResponse
}*/
