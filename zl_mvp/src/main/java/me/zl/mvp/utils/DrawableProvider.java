package me.zl.mvp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.media.ExifInterface;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * 处理 {@link Drawable} 和 {@link Bitmap} 的工具类
 */
public class DrawableProvider {

    private DrawableProvider() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    /**
     * 获得选择器
     *
     * @param normalDrawable
     * @param pressDrawable
     * @return
     */
    public static Drawable getStateListDrawable(Drawable normalDrawable, Drawable pressDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, pressDrawable);
        stateListDrawable.addState(new int[]{}, normalDrawable);
        return stateListDrawable;
    }

    /**
     * 将 TextView/RadioButton 中设置的 drawable 动态的缩放
     *
     * @param percent
     * @param tv
     * @return
     */
    public static Drawable getScaleDrawableForRadioButton(float percent, TextView tv) {
        Drawable[] compoundDrawables = tv.getCompoundDrawables();
        Drawable drawable = null;
        for (Drawable d : compoundDrawables) {
            if (d != null) {
                drawable = d;
            }
        }
        return getScaleDrawable(percent, drawable);
    }

    /**
     * 将 TextView/RadioButton 中设置的 drawable 动态的缩放
     *
     * @param tv
     * @return
     */
    public static Drawable getScaleDrawableForRadioButton2(float width, TextView tv) {
        Drawable[] compoundDrawables = tv.getCompoundDrawables();
        Drawable drawable = null;
        for (Drawable d : compoundDrawables) {
            if (d != null) {
                drawable = d;
            }
        }
        return getScaleDrawable2(width, drawable);
    }

    /**
     * 传入图片,将图片按传入比例缩放
     *
     * @param percent
     * @return
     */
    public static Drawable getScaleDrawable(float percent, Drawable drawable) {
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * percent + 0.5f), (int) (drawable.getIntrinsicHeight() * percent + 0.5f));
        return drawable;
    }

    /**
     * 传入图片,将图片按传入宽度和原始宽度的比例缩放
     *
     * @param width
     * @return
     */
    public static Drawable getScaleDrawable2(float width, Drawable drawable) {
        float percent = width * 1.0f / drawable.getIntrinsicWidth();
        return getScaleDrawable(percent, drawable);
    }

    /**
     * 设置左边的drawable
     *
     * @param tv
     * @param drawable
     */
    public static void setLeftDrawable(TextView tv, Drawable drawable) {
        tv.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 改变Bitmap的长宽
     *
     * @param bitmap
     * @return
     */
    public static Bitmap getReSizeBitmap(Bitmap bitmap, float targetWidth, float targetheight) {
        Bitmap returnBm = null;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(targetWidth / width, targetheight / height); //长和宽放大缩小的比例
        try {
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (returnBm == null) {
            returnBm = bitmap;
        }
        if (bitmap != returnBm) {
            bitmap.recycle();
        }
        return returnBm;
    }


    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }


    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
    public static Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        } catch (Exception e) {
            LogUtils.debugInfo(e.getMessage());
        }
        return bm;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, int i) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > i && options != 10) {
            output.reset(); //清空output
            bmp.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        return output.toByteArray();
    }

    public static byte[] bmpToByteArray(Bitmap bmp) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}