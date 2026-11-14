package com.example.homework10.domain.model

data class AppInfo(
    val id: String,
    val name: String,
    val developer: String,
    val category: String,
    val description: String,
    val sizeMb: Int,
    val rating: Float? = null
)