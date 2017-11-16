package com.hencoder.hencoderpracticedraw2.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice12PathEffectView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path path = new Path();

    public Practice12PathEffectView(Context context) {
        super(context);
    }

    public Practice12PathEffectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice12PathEffectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setStyle(Paint.Style.STROKE);

        path.moveTo(50, 100);
        path.rLineTo(50, 100);
        path.rLineTo(80, -150);
        path.rLineTo(100, 100);
        path.rLineTo(70, -120);
        path.rLineTo(150, 80);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 使用 Paint.setPathEffect() 来设置不同的 PathEffect

        // 第一处：CornerPathEffect
        PathEffect pathEffect = new CornerPathEffect(20);
        paint.setPathEffect(pathEffect);
        canvas.drawPath(path, paint);

        canvas.save();//通过旋转平移等操作画布后，会对之后绘制在画布上的东西产生影响，所以这里
        //有一个操作就是先保存操作之前画布的状态，然后在对别的元素进行绘制操作时调用canvas.restore
        //方法，取出之前保存的状态，这样就不会受到影响了。
        canvas.translate(500, 0);
        // 第二处：DiscretePathEffect
        //第一个参数是线段长度，第二个是偏移量
        PathEffect pathEffect1 = new DiscretePathEffect(10,5);
        paint.setPathEffect(pathEffect1);
        canvas.drawPath(path, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(0, 200);
        // 第三处：DashPathEffect
        //数组中的参数是线段长度，空白长度，线段长度，空白长度。第二个参数是虚线的偏移量
        PathEffect pathEffect2 = new DashPathEffect(new float[]{20,5,10,5},0);
        paint.setPathEffect(pathEffect2);
        canvas.drawPath(path, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(500, 200);
        // 第四处：PathDashPathEffect
        //先利用path画一个三角形
        Path dashPath = new Path();
        dashPath.rLineTo(20,5);
        dashPath.rLineTo(-20,20);
        dashPath.close();
        //第一个参数就是上面画三角形的path，第二个参数是两个线段起点之间的距离，第三个是偏移量
        //第四个是拐弯转变时的转弯方式
        PathEffect pathEffect3 = new PathDashPathEffect(dashPath,40,0,
                PathDashPathEffect.Style.ROTATE);
        paint.setPathEffect(pathEffect3);
        canvas.drawPath(path, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(0, 400);
        // 第五处：SumPathEffect
        //两种path效果叠加到一起
        PathEffect pathEffect4 = new DashPathEffect(new float[]{15,5,5,5},0);
        PathEffect pathEffect5 = new DiscretePathEffect(10,5);
        PathEffect pathEffect6 = new SumPathEffect(pathEffect4,pathEffect5);
        paint.setPathEffect(pathEffect6);
        canvas.drawPath(path, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(500, 400);
        // 第六处：ComposePathEffect
        //混合两种path效果，混合的方式是先执行一种，再在这种的基础上执行另一种
        //两个参数分别是outerpe，innerpe，先执行innerpe在执行outerpe，
        //所以这种是先偏移再虚线
        PathEffect pathEffect7 = new DashPathEffect(new float[]{20,5,10,5},0);
        PathEffect pathEffect8 = new DiscretePathEffect(10,5);
        PathEffect composePathEffect = new ComposePathEffect(pathEffect7,pathEffect8);
        paint.setPathEffect(composePathEffect);
        canvas.drawPath(path, paint);
        canvas.restore();
    }
}
