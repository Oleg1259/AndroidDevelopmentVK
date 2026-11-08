package com.example.homework9.data.remote

import com.example.homework9.data.remote.dto.AppDetailsDto
import com.example.homework9.data.remote.dto.AppItemDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CatalogApi {

    @GET("catalog")
    suspend fun getCatalog(): List<AppItemDto>

    @GET("{id}")
    suspend fun getAppById(
        @Path("id") id: String
    ): AppDetailsDto
}