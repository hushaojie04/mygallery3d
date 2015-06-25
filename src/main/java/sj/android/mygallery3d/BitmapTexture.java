package sj.android.mygallery3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2015/6/19.
 */
public class BitmapTexture extends  Texture{

    BitmapTexture(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    @Override
    protected Bitmap load(RenderView view ,GL10 gl10) {
        return mBitmap;
    }
}
