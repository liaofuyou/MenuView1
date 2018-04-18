package me.ajax.menuview1.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by aj on 2018/4/16
 */

public class MenuView extends ViewGroup {

    Paint mPaint = new Paint();
    MainMenuView mainMenu;
    SubMenuView subLeftMenu1;
    SubMenuView subLeftMenu2;
    SubMenuView subRightMenu1;
    SubMenuView subRightMenu2;

    ValueAnimator animator;

    boolean isExpend = false;
    boolean isStart = false;

    public MenuView(Context context) {
        super(context);
        init();
    }

    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {

        //画笔
        mPaint.setColor(0XFFFF534D);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(dp2Dx(2));
        mPaint.setStyle(Paint.Style.FILL);

        ensureView();

        mainMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStart) return;
                isStart = true;
                startAnimator(animator);
            }
        });

        post(new Runnable() {
            @Override
            public void run() {
                mainMenu.performClick();
            }
        });

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = getMeasuredHeight();
        measureChild(mainMenu, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
                , MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        measureChild(subLeftMenu1, MeasureSpec.makeMeasureSpec(height / 2, MeasureSpec.EXACTLY)
                , MeasureSpec.makeMeasureSpec(height / 2, MeasureSpec.EXACTLY));
        measureChild(subLeftMenu2, MeasureSpec.makeMeasureSpec(height / 2, MeasureSpec.EXACTLY)
                , MeasureSpec.makeMeasureSpec(height / 2, MeasureSpec.EXACTLY));
        measureChild(subRightMenu1, MeasureSpec.makeMeasureSpec(height / 2, MeasureSpec.EXACTLY)
                , MeasureSpec.makeMeasureSpec(height / 2, MeasureSpec.EXACTLY));
        measureChild(subRightMenu2, MeasureSpec.makeMeasureSpec(height / 2, MeasureSpec.EXACTLY)
                , MeasureSpec.makeMeasureSpec(height / 2, MeasureSpec.EXACTLY));
    }

    void ensureView() {
        if (mainMenu == null) {
            mainMenu = new MainMenuView(getContext());
            addView(mainMenu, generateDefaultLayoutParams());
        }
        if (subLeftMenu1 == null) {
            subLeftMenu1 = new SubMenuView(getContext());
            addView(subLeftMenu1, generateDefaultLayoutParams());
        }
        if (subLeftMenu2 == null) {
            subLeftMenu2 = new SubMenuView(getContext());
            addView(subLeftMenu2, generateDefaultLayoutParams());
        }
        if (subRightMenu1 == null) {
            subRightMenu1 = new SubMenuView(getContext());
            addView(subRightMenu1, generateDefaultLayoutParams());
        }
        if (subRightMenu2 == null) {
            subRightMenu2 = new SubMenuView(getContext());
            addView(subRightMenu2, generateDefaultLayoutParams());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int mWidth = getWidth();
        int mHeight = getHeight();

        int backCircleRadius = getBackgroundCircleRadius();

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);

        float animatedValue = (float) animator.getAnimatedValue();
        canvas.drawCircle(animatedValue, 0, backCircleRadius, mPaint);
        canvas.drawCircle(-animatedValue, 0, backCircleRadius, mPaint);
        canvas.drawRect(-animatedValue, -backCircleRadius, animatedValue, backCircleRadius, mPaint);

        canvas.restore();

        super.onDraw(canvas);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int hCenter = (r - l) / 2;
        int vCenter = (b - t) / 2;
        int height = getMeasuredHeight();
        int hQuarter = height / 4;
        int hHalf = height / 2;

        float animatedValue = 0;
        float animatedFraction = 0;
        if (animator != null) {
            animatedValue = (float) animator.getAnimatedValue();
            animatedFraction = animator.getAnimatedFraction();
        }

        mainMenu.layout(hCenter - hHalf, 0, hCenter + hHalf, height);

        int subMenuTop = hHalf - hQuarter;
        int subMenuBottom = subMenuTop + subLeftMenu1.getMeasuredHeight();

        subLeftMenu1.layout((int) (hCenter - hQuarter - animatedValue * 0.5F), subMenuTop,
                (int) (hCenter + hQuarter - animatedValue * 0.5F), subMenuBottom);

        subLeftMenu2.layout((int) (hCenter - hQuarter - animatedValue * 0.9F), subMenuTop,
                (int) (hCenter + hQuarter - animatedValue * 0.9F), subMenuBottom);

        subRightMenu1.layout((int) (hCenter - hQuarter + animatedValue * 0.5F), subMenuTop,
                (int) (hCenter + hQuarter + animatedValue * 0.5F), subMenuBottom);

        subRightMenu2.layout((int) (hCenter - hQuarter + animatedValue * 0.9F), subMenuTop,
                (int) (hCenter + hQuarter + animatedValue * 0.9F), subMenuBottom);

        mainMenu.setPercent(animatedFraction);
        subLeftMenu1.setPercent(animatedFraction);
        subLeftMenu2.setPercent(animatedFraction);
        subRightMenu1.setPercent(animatedFraction);
        subRightMenu2.setPercent(animatedFraction);

    }


    void startAnimator(ValueAnimator animator) {
        if (animator != null) {
            animator.cancel();
        }

        if (isExpend) {
            if (animator != null) {
                animator.reverse();
            }
            return;
        }
        if (animator == null) {
            float maxValue = getMeasuredWidth() / 2 - getBackgroundCircleRadius();
            animator = ValueAnimator.ofFloat(0, maxValue);
            animator.setDuration(600);
            //animator.setInterpolator(new BounceInterpolator());
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    isExpend = !isExpend;
                    isStart = false;
                }
            });
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    requestLayout();
                }
            });
        }
        animator.start();
        this.animator = animator;
    }

    int getBackgroundCircleRadius() {
        return (getMeasuredHeight() - dp2Dx(30) / 2) / 2;
    }

    int dp2Dx(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp);
    }

    void l(Object o) {
        Log.e("######", o.toString());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimAndRemoveCallbacks();
    }

    private void stopAnimAndRemoveCallbacks() {

        if (animator != null) animator.end();

        Handler handler = this.getHandler();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
