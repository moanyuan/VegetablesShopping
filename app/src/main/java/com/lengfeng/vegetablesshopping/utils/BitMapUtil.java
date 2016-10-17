package com.lengfeng.vegetablesshopping.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 * Created by Administrator on 2016/7/5.
 */
public class BitMapUtil {

    /**
     * bitmap缩放指定大小
     *
     * @param source
     * @param wf
     * @param hf
     * @return
     */
    public static Bitmap zoom(Bitmap source, float wf, float hf) {
        Matrix matrix = new Matrix();
        float sx = wf / source.getWidth();
        float sy = hf / source.getHeight();
        matrix.postScale(sx, sy);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    ;


    /**
     * 将一个bitmap对象裁剪成圆形输出
     *
     * @param source
     * @return
     */
    public static Bitmap circle(Bitmap source) {
        Bitmap target = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        int center = source.getWidth() / 2;
        Paint paint = new Paint();
        canvas.drawCircle(center, center, center, paint);
        //取canvas上所绘制的图像的交集，同时只显示上层
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint.setAntiAlias(true);
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }
}
