package by.enolizard.newsaggregator.presentation.views

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.enolizard.newsaggregator.presentation.convertToPx

class SpaceItemDecoration(
    context: Context,
    outRectDp: Rect
) : RecyclerView.ItemDecoration() {

    private val outRectPx = context.convertToPx(outRectDp)

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = outRectPx.bottom
        outRect.top = outRectPx.top
        outRect.left = outRectPx.left
        outRect.right = outRectPx.right
    }
}
