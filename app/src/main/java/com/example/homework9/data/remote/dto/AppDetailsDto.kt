package com.example.homework9.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AppDetailsDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("iconUrl")
    val iconUrl: String?
)