package me.ajax.menuview1.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by aj on 2018/4/2
 */

public class MainMenuView extends View {

    Paint mPaint = new Paint();
    float percent;

    public MainMenuView(Context context) {
        super(context);
        init();
    }

    public MainMenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MainMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {

        //画笔
        mPaint.setColor(0XFFE03636);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int mWidth = getMeasuredWidth();
        int mHeight = getMeasuredHeight();
        int quarter = mWidth / 4;
        int half = mWidth / 2;

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);

        mPaint.setColor(0XFFE03636);
        canvas.drawCircle(0, 0, mHeight / 2, mPaint);

        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(dp2Dx(4));
        canvas.drawPoint(0, 0, mPaint);

        canvas.save();
        canvas.rotate(45 * percent);
        canvas.drawLine(-quarter, 0, -quarter + half * percent, 0, mPaint);
        canvas.drawPoint(-quarter, 0, mPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(-45 * percent);
        canvas.drawLine(quarter - half * percent, 0, quarter, 0, mPaint);
        canvas.drawPoint(quarter, 0, mPaint);
        canvas.restore();

        canvas.restore();
    }


    public void setPercent(float percent) {
        this.percent = percent;
        mPaint.setAlpha((int) (255 * percent));
        invalidate();
    }

    int dp2Dx(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp);
    }


    void l(Object o) {
        Log.e("######", o.toString());
    }
}
