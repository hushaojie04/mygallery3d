package sj.android.mygallery3d;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2015/6/24.
 */
public class GridLayer extends Layer {
    private static final int DEFAULT_ROW = 3;
    private static final float DEFAULT_INTERVAL = 0.1f;
    int numRow = 3;
    List<ResourceTexture> mList = new ArrayList<ResourceTexture>();

    @Override
    void draw(RenderView view, GL10 gl) {
        Iterator<ResourceTexture> iterator = mList.iterator();
        while (iterator.hasNext()) {
            iterator.next().draw(gl);
        }
    }

    public void setDataSource() {
        ResourceTexture mGLBitmap = new ResourceTexture(R.drawable.leftcode, false);
        mList.add(mGLBitmap);
        mGLBitmap = new ResourceTexture(R.drawable.leftcode, false);
        mList.add(mGLBitmap);
    }

    protected void load(RenderView view, GL10 gl) {
        Iterator<ResourceTexture> iterator = mList.iterator();
        int row,col;
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next().load(view, gl);
            ResourceTexture texture = iterator.next();
            texture.mX = count/numRow*(2+DEFAULT_INTERVAL)+getX();
            texture.mY = count%numRow*(2+DEFAULT_INTERVAL)+getY();
            count++;
        }
    }

}
