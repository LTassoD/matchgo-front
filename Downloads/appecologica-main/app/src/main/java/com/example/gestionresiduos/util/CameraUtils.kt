package com.example.gestionresiduos.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.gestionresiduos.BuildConfig
import java.io.File


fun createImageUri(context: Context): Uri {
    val imageFile = File.createTempFile(
        "JPEG_${System.currentTimeMillis()}_",
        ".jpg",
        context.cacheDir
    )

    return FileProvider.getUriForFile(
        context,
        "${BuildConfig.APPLICATION_ID}.provider",
        imageFile
    )
}
