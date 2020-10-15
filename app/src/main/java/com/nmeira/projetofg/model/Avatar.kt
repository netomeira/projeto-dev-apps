package com.nmeira.projetofg.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Avatar(
    val id: Int,
    val type: String,
    val name: String,
    var lvl: Int = 1,
    var img: Int = 0
): Parcelable
