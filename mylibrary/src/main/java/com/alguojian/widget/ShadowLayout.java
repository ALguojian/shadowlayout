package com.alguojian.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 绘制阴影
 *
 * @author alguojian
 * @date 2018.09.28
 */
public class ShadowLayout extends FrameLayout {
    public static final int ALL = 0x1111;
    public static final int LEFT = 0x0001;
    public static final int TOP = 0x0010;
    public static final int RIGHT = 0x0100;
    public static final int BOTTOM = 0x1000;
    private int mShadowColor;
    private float mShadowLength;
    private float mShadowRadius;
    private float shadowTranslationX;
    private float shadowTranslationY;
    private boolean mInvalidateShadowOnSizeChanged = true;
    private boolean mForceInvalidateShadow = false;
    private int shadowDirection = ALL;
    private int left = 0;
    private int top = 0;
    private int right = 0;
    private int bottom = 0;

    public ShadowLayout(Context context) {
        this(context, null);
    }

    public ShadowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        initAttributes(context, attrs);
        int xPadding = (int) (mShadowLength + Math.abs(shadowTranslationX));
        int yPadding = (int) (mShadowLength + Math.abs(shadowTranslationY));


        if ((shadowDirection & LEFT) == LEFT) {
            left = xPadding;
        }
        if ((shadowDirection & TOP) == TOP) {
            top = yPadding;
        }
        if ((shadowDirection & RIGHT) == RIGHT) {
            right = xPadding;
        }
        if ((shadowDirection & BOTTOM) == BOTTOM) {
            bottom = yPadding;
        }

        setPadding(left, top, right, bottom);
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray attr = getTypedArray(context, attrs, R.styleable.ShadowLayout);
        if (attr == null) {
            return;
        }
        try {
            mShadowRadius = attr.getDimension(R.styleable.ShadowLayout_shadowRadius, 0);
            mShadowLength = attr.getDimension(R.styleable.ShadowLayout_shadowLength, 4);
            shadowTranslationX = attr.getDimension(R.styleable.ShadowLayout_shadowTranslationX, 0);
            shadowTranslationY = attr.getDimension(R.styleable.ShadowLayout_shadowTranslationY, 0);
            mShadowColor = attr.getColor(R.styleable.ShadowLayout_shadowColor, Color.parseColor("#b0333333"));
            shadowDirection = attr.getInt(R.styleable.ShadowLayout_shadowDirection, ALL);
        } finally {
            attr.recycle();
        }
    }

    private TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0 && (getBackground() == null || mInvalidateShadowOnSizeChanged || mForceInvalidateShadow)) {
            mForceInvalidateShadow = false;
            setBackgroundCompat(w, h);
        }
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return 0;
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return 0;
    }

    private void setBackgroundCompat(int w, int h) {
        Bitmap bitmap = createShadowBitmap(w, h, mShadowRadius, mShadowLength, shadowTranslationX, shadowTranslationY, mShadowColor, Color.TRANSPARENT);
        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
        setBackground(drawable);
    }

    private Bitmap createShadowBitmap(int shadowWidth, int shadowHeight, float cornerRadius,
                                      float shadowRadius,
                                      float dx, float dy, int shadowColor, int fillColor) {

        Bitmap output = Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        RectF shadowRect = new RectF(
                shadowRadius,
                shadowRadius,
                shadowWidth - shadowRadius,
                shadowHeight - shadowRadius);

        if (dy > 0) {
            shadowRect.top += dy;
            shadowRect.bottom -= dy;
        } else if (dy < 0) {
            shadowRect.top += Math.abs(dy);
            shadowRect.bottom -= Math.abs(dy);
        }

        if (dx > 0) {
            shadowRect.left += dx;
            shadowRect.right -= dx;
        } else if (dx < 0) {
            shadowRect.left += Math.abs(dx);
            shadowRect.right -= Math.abs(dx);
        }

        Paint shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setColor(fillColor);
        shadowPaint.setStyle(Paint.Style.FILL);

        if (!isInEditMode()) {
            shadowPaint.setShadowLayer(shadowRadius, dx, dy, shadowColor);
        }
        canvas.drawRoundRect(shadowRect, cornerRadius, cornerRadius, shadowPaint);
        return output;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mForceInvalidateShadow) {
            mForceInvalidateShadow = false;
            setBackgroundCompat(right - left, bottom - top);
        }
    }

    public void setInvalidateShadowOnSizeChanged(boolean invalidateShadowOnSizeChanged) {
        mInvalidateShadowOnSizeChanged = invalidateShadowOnSizeChanged;
    }

    public void setShadowLength(float mShadowRadius) {
        this.mShadowLength = mShadowRadius;
        invalidateShadow();
    }

    public void invalidateShadow() {
        mForceInvalidateShadow = true;
        requestLayout();
        invalidate();
    }

}
