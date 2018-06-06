package com.example.shotasaitou.myfeed

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

abstract class SwipeToDeleteCallback(context: Context)
    : ItemTouchHelper.SimpleCallback(0, (ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT)) {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_action_delete)
    private val deleteIconIntrinsicWidth = deleteIcon?.intrinsicWidth
    private val deleteIconIntrinsicHeight = deleteIcon?.intrinsicHeight

    private val archiveIcon = ContextCompat.getDrawable(context, R.drawable.ic_action_archive)
    private val archiveIconIntrinsicWidth = archiveIcon?.intrinsicWidth
    private val archiveIconIntrinsicHeight = archiveIcon?.intrinsicHeight

    private val background = ColorDrawable()
    private val leftBackgroundColor = Color.parseColor("#f44336")
    private val rightBackgroundColor = Color.parseColor("#25AA71")
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

    override fun onMove(
            recyclerView: RecyclerView?,
            viewHolder: RecyclerView.ViewHolder?,
            target: RecyclerView.ViewHolder?
    ): Boolean {
        return false
    }

    override fun onChildDraw(
            c: Canvas?,
            recyclerView: RecyclerView?,
            viewHolder: RecyclerView.ViewHolder?,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder?.itemView ?: return

        val isCanceled = dX == 0f && !isCurrentlyActive
        if (isCanceled) {
            clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        // Draw the red delete background
        val isLeftDirection = dX < 0
        if (isLeftDirection) {
            background.color = leftBackgroundColor
            background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        } else {
            background.color = rightBackgroundColor
            background.setBounds(itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom)
        }
        background.draw(c)

        val itemHeight = itemView.bottom - itemView.top
        if (deleteIcon != null
                && deleteIconIntrinsicWidth != null
                && deleteIconIntrinsicHeight != null
                && archiveIcon != null
                && archiveIconIntrinsicWidth != null
                && archiveIconIntrinsicHeight != null) {

            if (isLeftDirection) {
                // Calculate position of delete icon
                val deleteIconTop = itemView.top + (itemHeight - deleteIconIntrinsicHeight) / 2
                val deleteIconMargin = (itemHeight - deleteIconIntrinsicHeight) / 2
                val deleteIconLeft = itemView.right - deleteIconMargin - deleteIconIntrinsicWidth
                val deleteIconRight = itemView.right - deleteIconMargin
                val deleteIconBottom = deleteIconTop + deleteIconIntrinsicHeight

                // Draw the delete icon
                deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
                deleteIcon.draw(c)
            } else {
                // Calculate position of delete icon
                val archiveIconTop = itemView.top + (itemHeight - archiveIconIntrinsicHeight) / 2
                val archiveIconMargin = (itemHeight - archiveIconIntrinsicHeight) / 2
                val archiveIconLeft = itemView.left + archiveIconMargin
                val archiveIconRight = itemView.left + (archiveIconMargin + archiveIconIntrinsicWidth)
                val archiveIconBottom = archiveIconTop + archiveIconIntrinsicHeight

                // Draw the delete icon
                archiveIcon.setBounds(archiveIconLeft, archiveIconTop, archiveIconRight, archiveIconBottom)
                archiveIcon.draw(c)
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }
}
