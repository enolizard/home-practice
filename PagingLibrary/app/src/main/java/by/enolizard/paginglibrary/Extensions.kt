package by.enolizard.paginglibrary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(resId: Int, attachToRoot: Boolean): View {
    return LayoutInflater.from(context).inflate(resId, this, attachToRoot)
}
