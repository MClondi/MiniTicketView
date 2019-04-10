package com.mclondi.miniticketview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.IntDef

class MiniTicketView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var minWidth: Int = 0
    private var minHeight: Int = 0
    private var perforationPositionPercent: Float = 0f
    private var showDivider: Boolean = false
    private var dividerWidth: Int = 0
    private var dividerPadding: Int = 0

    var backgroundDrawable: TicketBackgroundDrawable? = null

    init {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MiniTicketView)

            val scallopRadius =
                typedArray.getDimensionPixelSize(R.styleable.MiniTicketView_miniTicketScallopRadius, context.dpToPx(4f))
            val cornerRadius =
                typedArray.getDimensionPixelSize(R.styleable.MiniTicketView_miniTicketCornerRadius, context.dpToPx(4f))
            showDivider = typedArray.getBoolean(R.styleable.MiniTicketView_miniTicketShowDivider, false)
            dividerWidth =
                typedArray.getDimensionPixelSize(R.styleable.MiniTicketView_miniTicketDividerWidth, context.dpToPx(2f))
            dividerPadding =
                typedArray.getDimensionPixelSize(R.styleable.MiniTicketView_miniTicketDividerPadding, context.dpToPx(0f))

            perforationPositionPercent =
                typedArray.getFloat(R.styleable.MiniTicketView_miniTicketPerforationPositionPercent, 25f)

            val biggerRadius = maxOf(scallopRadius, cornerRadius)
            minHeight = biggerRadius * 4
            minWidth = biggerRadius * 2

            backgroundDrawable = TicketBackgroundDrawable(typedArray, context)
            val stateList = ColorStateList(
                arrayOf(intArrayOf()),
                intArrayOf(context.resources.getColor(android.R.color.white))
            )
            background = RippleDrawable( stateList, backgroundDrawable,null )

            typedArray.recycle()
        }

        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {

        for (i in 0 until childCount) {
            val lp = getChildAt(i).layoutParams as FrameLayout.LayoutParams
            lp.gravity = Gravity.END
        }

        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val dividerOffset = if (showDivider) dividerWidth + (dividerPadding * 2) else 0
        val perforationWidth = maxOf(
            minWidth.toFloat(),
            (measuredWidth * 100) / (100 - perforationPositionPercent) - measuredWidth
        ) + dividerOffset
        val ticketHeight = maxOf(measuredHeight, minHeight)
        setMeasuredDimension(perforationWidth.toInt() + measuredWidth, ticketHeight)
    }

    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @IntDef(DividerType.NORMAL, DividerType.DASH)
    annotation class DividerType {
        companion object {
            const val NORMAL = 0
            const val DASH = 1
        }
    }


}