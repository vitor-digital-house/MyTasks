package com.example.mytasks.data

import java.io.Serializable

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String,
) : Serializable
