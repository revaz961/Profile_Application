package com.example.profileapplication

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class User(
    var name: String,
    var secondName: String,
    var email: String,
    var birthDate: Int,
    var gender: String,
):Parcelable