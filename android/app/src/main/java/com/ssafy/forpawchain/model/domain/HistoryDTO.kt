package com.ssafy.forpawchain.model.domain

import java.io.Serializable


data class Data(
    val title: String?,
    val body: String?
): Serializable


data class HistoryDTO (
    val title: String,
    val body: String,
    val extra: ArrayList<Data>,
    var writer: String,
    val hash: String,
    val date: String
): Serializable
