package com.mclondi.miniticketview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.Drawable

class TicketBackgroundDrawable(typedArray: TypedArray, context: Context) : Drawable() {

    private val backgroundPaint = Paint()
    private val perforationPaint = Paint()
    private val dividerPaint = Paint()
    private val backgroundDrawablePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val arcRect = RectF()
    private val contentPath = Path()
    private val perforationPath = Path()

    private var dividerStartX: Float = 0f
    private var dividerStartY: Float = 0f
    private var dividerStopX: Float = 0f
    private var dividerStopY: Float = 0f

    private var perforationPositionPercent: Float = 0f
    private var scallopHide: Boolean = false
    private var ticketBackgroundColor: Int = 0
    private var perforationBackgroundColor: Int = 0
    private var ticketBackgroundDrawable: Drawable? = null
    private var perforationBackgroundDrawable: Drawable? = null

    private var scallopRadius: Int = 0
    private var cornerRadius: Int = 0
    private var showDivider: Boolean = false
    private var dividerDashLength: Int = 0
    private var dividerDashGap: Int = 0
    private var dividerType: Int = 0
    private var dividerWidth: Int = 0
    private var dividerColor: Int = 0
    private var dividerPadding: Int = 0
    private var dividerPositionX: Float = 0f

    init {
        ticketBackgroundColor = typedArray.getColor(
            R.styleable.MiniTicketView_miniTicketBackgroundColor,
            context.resources.getColor(android.R.color.white)
        )
        ticketBackgroundDrawable = typedArray.getDrawable(R.styleable.MiniTicketView_miniTicketBackgroundDrawable)

        perforationBackgroundColor = typedArray.getColor(
            R.styleable.MiniTicketView_miniTicketPerforationBackgroundColor,
            context.resources.getColor(android.R.color.white)
        )
        perforationBackgroundDrawable =
            typedArray.getDrawable(R.styleable.MiniTicketView_miniTicketPerforationBackgroundDrawable)
        perforationPositionPercent =
            typedArray.getFloat(R.styleable.MiniTicketView_miniTicketPerforationPositionPercent, 25f)

        scallopRadius =
            typedArray.getDimensionPixelSize(R.styleable.MiniTicketView_miniTicketScallopRadius, context.dpToPx(4f))
        scallopHide = typedArray.getBoolean(R.styleable.MiniTicketView_miniTicketScallopHide, false)

        showDivider = typedArray.getBoolean(R.styleable.MiniTicketView_miniTicketShowDivider, false)
        dividerWidth =
            typedArray.getDimensionPixelSize(R.styleable.MiniTicketView_miniTicketDividerWidth, context.dpToPx(2f))
        dividerPadding =
            typedArray.getDimensionPixelSize(R.styleable.MiniTicketView_miniTicketDividerPadding, context.dpToPx(0f))
        dividerColor = typedArray.getColor(
            R.styleable.MiniTicketView_miniTicketDividerColor,
            context.resources.getColor(android.R.color.darker_gray)
        )
        dividerType = typedArray.getInt(R.styleable.MiniTicketView_miniTicketDividerType, MiniTicketView.DividerType.NORMAL)
        dividerDashGap =
            typedArray.getDimensionPixelSize(R.styleable.MiniTicketView_miniTicketDividerDashGap, context.dpToPx(4f))
        dividerDashLength =
            typedArray.getDimensionPixelSize(R.styleable.MiniTicketView_miniTicketDividerDashLength, context.dpToPx(8f))

        cornerRadius =
            typedArray.getDimensionPixelSize(R.styleable.MiniTicketView_miniTicketCornerRadius, context.dpToPx(4f))

        backgroundDrawablePaint.color = -0x1000000
        backgroundDrawablePaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    }


    override fun draw(canvas: Canvas) {

        setBackgroundPaint()
        setPerforationBackgroundPaint()
        setDividerPaint()

        dividerPositionX = bounds.width() * perforationPositionPercent / 100
        doLayout(bounds.width().toFloat(), bounds.height().toFloat())
        canvas.drawPath(contentPath, backgroundPaint)
        canvas.drawPath(perforationPath, perforationPaint)


        if (ticketBackgroundDrawable != null && perforationBackgroundDrawable != null) {

            val bmp = Bitmap.createBitmap(bounds.width(), bounds.height(), Bitmap.Config.ARGB_8888)
            val cnv = Canvas(bmp)

            ticketBackgroundDrawable?.let {
                it.setBounds(dividerPositionX.toInt(), 0, bounds.width(), bounds.height())
                it.draw(cnv)
            }

            perforationBackgroundDrawable?.let {
                it.setBounds(0, 0, dividerPositionX.toInt(), bounds.height())
                it.draw(cnv)
            }

            canvas.drawBitmap(bmp, 0f, 0f, backgroundDrawablePaint)
            bmp.recycle()
        }


        if (showDivider) {
            canvas.drawLine(dividerStartX, dividerStartY, dividerStopX, dividerStopY, dividerPaint)
        }

    }


    private fun setBackgroundPaint() {
        backgroundPaint.alpha = 0
        backgroundPaint.isAntiAlias = true
        backgroundPaint.color = ticketBackgroundColor
        backgroundPaint.style = Paint.Style.FILL
    }

    private fun setPerforationBackgroundPaint() {
        perforationPaint.alpha = 0
        perforationPaint.isAntiAlias = true
        perforationPaint.color = perforationBackgroundColor
        perforationPaint.style = Paint.Style.FILL
    }

    private fun setDividerPaint() {
        dividerPaint.alpha = 0
        dividerPaint.isAntiAlias = true
        dividerPaint.color = dividerColor
        dividerPaint.strokeWidth = dividerWidth.toFloat()

        if (dividerType == MiniTicketView.DividerType.DASH)
            dividerPaint.pathEffect =
                DashPathEffect(floatArrayOf(dividerDashLength.toFloat(), dividerDashGap.toFloat()), 0.0f)
        else
            dividerPaint.pathEffect = PathEffect()
    }


    private fun doLayout(width: Float, height: Float) {

        val left = 0f
        val right = width
        val top = 0f
        val bottom = height

        val scallopPositionTop = (top + bottom) / 2 - scallopRadius
        val scallopPositionBottom = (top + bottom) / 2 + scallopRadius


        // content
        contentPath.reset()
        contentPath.moveTo(dividerPositionX, top)
        contentPath.arcTo(getTopRightCornerRoundedArc(top, right), -90.0f, 90.0f, false)
        contentPath.arcTo(getBottomRightCornerRoundedArc(bottom, right), 0.0f, 90.0f, false)
        contentPath.lineTo(dividerPositionX, bottom)
        contentPath.close()

        // perforation
        perforationPath.reset()
        perforationPath.arcTo(getTopLeftCornerRoundedArc(top, left), 180.0f, 90.0f, false)
        perforationPath.lineTo(dividerPositionX, top)
        perforationPath.lineTo(dividerPositionX, bottom)
        perforationPath.arcTo(getBottomLeftCornerRoundedArc(left, bottom), 90.0f, 90.0f, false)
        if (!scallopHide) {
            this.arcRect.set(left - scallopRadius, scallopPositionTop, left + scallopRadius, scallopPositionBottom)
            perforationPath.arcTo(this.arcRect, 90.0f, -180.0f, false)
        }
        perforationPath.close()

        // divider
        dividerStartX = dividerPositionX + dividerPadding + dividerWidth / 2
        dividerStartY = top
        dividerStopX = dividerStartX
        dividerStopY = bottom
    }


    private fun getTopLeftCornerRoundedArc(top: Float, left: Float): RectF {
        this.arcRect.set(left, top, left + cornerRadius * 2, top + cornerRadius * 2)
        return this.arcRect
    }

    private fun getTopRightCornerRoundedArc(top: Float, right: Float): RectF {
        this.arcRect.set(right - cornerRadius * 2, top, right, top + cornerRadius * 2)
        return this.arcRect
    }

    private fun getBottomLeftCornerRoundedArc(left: Float, bottom: Float): RectF {
        this.arcRect.set(left, bottom - cornerRadius * 2, left + cornerRadius * 2, bottom)
        return this.arcRect
    }

    private fun getBottomRightCornerRoundedArc(bottom: Float, right: Float): RectF {
        this.arcRect.set(right - cornerRadius * 2, bottom - cornerRadius * 2, right, bottom)
        return this.arcRect
    }

    override fun getOutline(outline: Outline) {
        outline.setRoundRect(bounds, cornerRadius.toFloat())
    }

    override fun setAlpha(alpha: Int) {
        backgroundPaint.alpha = alpha
        backgroundDrawablePaint.alpha = alpha
        perforationPaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        backgroundPaint.colorFilter = colorFilter
        backgroundDrawablePaint.colorFilter = colorFilter
        perforationPaint.colorFilter = colorFilter
    }
}