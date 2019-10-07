package com.example.listapp.models

import java.io.Serializable

data class TaskList(
    val name: String,
    val tasks: ArrayList<String> = ArrayList<String>()
) : Serializable