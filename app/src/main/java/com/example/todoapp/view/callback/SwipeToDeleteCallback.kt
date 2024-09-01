package com.example.todoapp.view.callback

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * スワイプで削除するためのコールバッククラス
 * @param context コンテキスト
 * @param onSwipedAction スワイプ時のアクション
 */
class SwipeToDeleteCallback(
    private val context: Context,
    private val onSwipedAction: (Int) -> Unit
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    /**
     * スワイプ時のアクション
     * @param viewHolder スワイプされたViewHolder
     * @param direction スワイプ方向
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // スワイプ時のアクションをコールバックで処理
        val position = viewHolder.adapterPosition
        onSwipedAction(position)
    }

    /**
     * スワイプ時のアイテムの描画
     * @param canvas キャンバス
     * @param recyclerView リサイクラービュー
     * @param viewHolder スワイプされたViewHolder
     * @param dX スワイプされたX座標
     * @param dY スワイプされたY座標
     * @param actionState アクションの状態
     * @param isCurrentlyActive 現在アクティブかどうか
     */
    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val maxSwipeDistance = itemView.width * 0.25f

        // スワイプ距離を制限
        val limitedDX = maxOf(dX, -maxSwipeDistance)

        val deleteIcon = getDrawable(context, android.R.drawable.ic_menu_delete)
        val background = ColorDrawable(Color.RED)

        deleteIcon?.let {
            if (limitedDX < 0) { // スワイプが左方向の時

                // ゴミ箱アイコンの位置を計算
                val iconMargin = (itemView.height - it.intrinsicHeight) / 2

                // ゴミ箱アイコンの描画範囲を設定
                val iconTop = itemView.top + iconMargin
                val iconBottom = iconTop + it.intrinsicHeight
                val iconRight = itemView.right - iconMargin / 2
                val iconLeft = iconRight - it.intrinsicWidth

                // ゴミ箱アイコンを描画
                it.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                background.setBounds(
                    itemView.right + limitedDX.toInt(),
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )

                background.draw(canvas)
                it.draw(canvas)
            } else {
                background.setBounds(0, 0, 0, 0)
            }
        }

        super.onChildDraw(
            canvas,
            recyclerView,
            viewHolder,
            limitedDX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    /**
     * スワイプの閾値を設定
     * @param viewHolder スワイプされたViewHolder
     * @return スワイプの閾値（0.0f〜1.0fの値）
     */
    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.2f // 40%のスワイプで動作するように設定
    }

    /**
     * スワイプの速度を設定
     * @param defaultValue デフォルトの速度
     * @return スワイプの速度
     */
    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return defaultValue * 0.5f // デフォルトの半分の速度で動作するように設定
    }

    /**
     * スワイプの加速度を設定
     * @param defaultValue デフォルトの加速度
     * @return スワイプの加速度
     */
    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        return defaultValue * 0.5f // デフォルトの半分の加速度で動作するように設定
    }

    /**
     * ドラッグを無効にする
     * @param recyclerView リサイクラービュー
     * @param viewHolder ドラッグされたViewHolder
     * @param target ドラッグ先のViewHolder
     */
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false
}