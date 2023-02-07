package com.example.cardapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardResponse(
    val id: Int = 0,
    val bankName: String,
    val number: Int,
    val date1: Int,
    val date2: Int,
    val cardHolderName: String,
    val cvv: String
): Parcelable
