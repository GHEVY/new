package com.example.app.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class BitmapUtils extends BitmapDrawable {
    public static   android.graphics.Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        android.graphics.Bitmap bitmap = android.graphics.Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), android.graphics.Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static android.graphics.Bitmap resizeBitmap(android.graphics.Bitmap bitmap, int newWidth, int newHeight) {
        return android.graphics.Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
    }

    public static Drawable bitmapToDrawable(Context context, android.graphics.Bitmap bitmap) {
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static Drawable resizeDrawable(Context context, Drawable drawable, int newWidth, int newHeight) {
        android.graphics.Bitmap bitmap = drawableToBitmap(drawable);
        android.graphics.Bitmap resizedBitmap = resizeBitmap(bitmap, newWidth, newHeight);
        return bitmapToDrawable(context, resizedBitmap);
    }
}
