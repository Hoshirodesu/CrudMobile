package com.example.crud

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.Serializable

data class News (
    var id: String? = null,
    var desc: String = "",
    var img: String = "",
    var short_desc: String = "",
    var title: String = ""
):Serializable