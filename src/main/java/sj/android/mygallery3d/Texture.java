package sj.android.mygallery3d;

import android.graphics.Bitmap;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2015/6/24.
 */
public abstract class Texture {
    int mId;
    float mWidth;
    float mHeight;
    float mX;
    float mY;
    Bitmap mBitmap;

    /**
     * Returns a bitmap, or null if an error occurs.
     */
    protected abstract Bitmap load(RenderView view ,GL10 gl10);
}
