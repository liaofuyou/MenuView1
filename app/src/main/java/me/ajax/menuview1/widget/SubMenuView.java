package me.ajax.menuview1.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import me.ajax.menuview1.R;

/**
 * Created by aj on 2018/4/2
 */

public class SubMenuView extends View {

    Paint mPaint = new Paint();
    float percent = 0;
    Bitmap mBitmap;

    public SubMenuView(Context context) {
        super(context);
        init();
    }

    public SubMenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SubMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {

        //画笔
        mPaint.setColor(0XFFFFFFF);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mBitmap = changeBitmapSize(dp2Dx(20), dp2Dx(20));

    }

    private Bitmap changeBitmapSize(int newWidth, int newHeight) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        //计算压缩的比率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        //获取想要缩放的matrix
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        //获取新的bitmap
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int mWidth = getMeasuredWidth();
        int mHeight = getMeasuredHeight();


        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);

        canvas.drawCircle(0, 0, mHeight / 2 * percent, mPaint);
        //canvas.drawBitmap(mBitmap, -dp2Dx(10), -dp2Dx(10), mPaint);

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
