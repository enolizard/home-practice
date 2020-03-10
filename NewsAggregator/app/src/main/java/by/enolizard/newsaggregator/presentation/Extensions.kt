package by.enolizard.newsaggregator.presentation

import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

fun ViewGroup.inflate(resId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(resId, this, attachToRoot)
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun Context.convertToPx(dp: Int): Int = this.resources.displayMetrics.convertToPx(dp)

fun DisplayMetrics.convertToPx(dp: Int): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), this
).toInt()

fun Context.convertToPx(rectDp: Rect) = with(this.resources.displayMetrics) {
    val left = convertToPx(rectDp.left)
    val right = convertToPx(rectDp.right)
    val bottom = convertToPx(rectDp.bottom)
    val top = convertToPx(rectDp.top)

    Rect(left, top, right, bottom)
}

fun Context.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}