package com.example.gestionresiduos.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.gestionresiduos.BuildConfig
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ComposeFileProvider : FileProvider()

fun Context.createTempImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = cacheDir.apply { if (!exists()) mkdirs() }
    return File.createTempFile("JPG_$timeStamp", ".jpg", storageDir)
}

fun Context.tempImageUri(file: File = createTempImageFile()): Uri =
    FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.provider", file)
