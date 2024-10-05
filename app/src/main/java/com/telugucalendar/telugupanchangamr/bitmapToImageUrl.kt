package com.telugucalendar.telugupanchangamr

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import java.io.ByteArrayOutputStream

fun bitmapToImageUrl(bitmap: Bitmap, format: CompressFormat = CompressFormat.PNG): String {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(format, 100, outputStream)
    val byteArray = outputStream.toByteArray()
    return "data:image/png;base64,${android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)}"
}
