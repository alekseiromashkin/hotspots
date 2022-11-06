package com.hotspots.android.apps.clusterization.core

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat


fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
    var drawable: Drawable? = ContextCompat.getDrawable(context, drawableId)
    if (drawable != null) {
        drawable = DrawableCompat.wrap(drawable).mutate()
    }
    val bitmap: Bitmap = Bitmap.createBitmap(
        drawable?.intrinsicWidth ?: 0,
        drawable?.intrinsicHeight ?: 0, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable?.setBounds(0, 0, canvas.width, canvas.height)
    drawable?.draw(canvas)
    return bitmap
}
