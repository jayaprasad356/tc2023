package com.telugucalendar.telugupanchangamr

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

fun convertXmlToBitmap(view: View): Bitmap {
    // Measure and layout the view
    view.measure(
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    )
    view.layout(0, 0, view.measuredWidth, view.measuredHeight)

    // Create a Bitmap with the measured dimensions of the view
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)

    // Create a Canvas with the Bitmap
    val canvas = Canvas(bitmap)

    // Draw the view to the Canvas
    view.draw(canvas)

    return bitmap
}
