package sj.android.mygallery3d;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2015/6/23.
 */
public abstract class Layer {
    private float mX, mY;
    private float mWidth, mHeight;
    private boolean mHidden;

    public float getX() {
        return mX;
    }

    public float getY() {
        return mY;
    }

    public float getWidth() {
        return mWidth;
    }

    public float getHeight() {
        return mHeight;
    }

    public void setPosition(float x, float y) {
        mX = x;
        mY = y;
    }

    public void setSize(float width, float height) {
        if (mWidth != width || mHeight != height) {
            mWidth = width;
            mHeight = height;
            onSizeChanged();
        }
    }

    public void setHidden(boolean hidden) {
        if (mHidden != hidden) {
            mHidden = hidden;
            onHiddenChanged();
        }
    }
    // Returns true if something is animating.
    public boolean update(RenderView view, float frameInterval) {
        return false;
    }

    public void renderOpaque(RenderView view, GL10 gl) {
    }

    public void renderBlended(RenderView view, GL10 gl) {
    }
    // Allows subclasses to further constrain the hit test defined by layer
    // bounds.
    public boolean containsPoint(float x, float y) {
        return true;
    }

    protected void onSurfaceCreated(RenderView view, GL10 gl) {
    }

    protected void onSizeChanged() {
    }

    protected void onHiddenChanged() {
    }

    abstract void draw(RenderView view, GL10 gl);

}
