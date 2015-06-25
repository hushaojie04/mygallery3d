package sj.android.mygallery3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2015/6/24.
 */
public class ResourceTexture extends Texture {
    @Override
    protected Bitmap load(RenderView view, GL10 gl) {
        InputStream bitmapStream = null;
        Bitmap bitmap = null;
        try {
            // 打开图片资源流
            bitmapStream = view.getContext().getResources().openRawResource(
                    mResourceId);
            // 解码图片生成 Bitmap 实例
            bitmap = BitmapFactory.decodeStream(bitmapStream);

            // 生成一个纹理对象，并将其ID保存到成员变量 texture 中
            int[] textures = new int[1];
            gl.glGenTextures(1, textures, 0);
            mId = textures[0];
            // 将生成的空纹理绑定到当前2D纹理通道
            gl.glBindTexture(GL10.GL_TEXTURE_2D, mId);

            // 设置2D纹理通道当前绑定的纹理的属性
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                    GL10.GL_NEAREST);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                    GL10.GL_LINEAR);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
                    GL10.GL_REPEAT);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
                    GL10.GL_REPEAT);

            // 将bitmap应用到2D纹理通道当前绑定的纹理中
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        } finally {
            // 释放资源
            // BTW: 期待 android 早日支持 Java 新的 try-with-resource 语法

            if (bitmap != null)
                bitmap.recycle();

            if (bitmapStream != null) {
                try {
                    bitmapStream.close();
                } catch (IOException e) {

                }
            }
        }
        return null;
    }

    private final int mResourceId;
    private final boolean mScaled;

    public boolean isCached() {
        return true;
    }

    public ResourceTexture(int resourceId, boolean scaled) {
        mResourceId = resourceId;
        mScaled = scaled;
        init();
    }

    private ByteBuffer textureBuffer; // buffer holding the texture coordinates
    private ByteBuffer vertexBuffer; // buffer holding the vertices
    private float[] data_vertices = {
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f
    };

    private float[] data_tvertices = {
            0.0000f, 1.0000f,
            0.0000f, 0.0000f,
            1.0000f, 0.0000f,
            1.0000f, 0.0000f,
            1.0000f, 1.0000f,
            0.0000f, 1.0000f,

    };
    private byte[] data_triangles = {0, 1, 2, 3, 4, 5, 6,};

    private ByteBuffer vertices;
    private ByteBuffer triangles;
    private ByteBuffer tvertices;


    public void init() {
        // 创建顶点缓冲
        vertices = ByteBuffer.allocateDirect(data_vertices.length * 4);
        vertices.order(ByteOrder.nativeOrder());
        vertices.asFloatBuffer().put(data_vertices);
        vertices.position(0);

        // 创建索引缓冲
        triangles = ByteBuffer.allocateDirect(data_triangles.length * 2);
        triangles.put(data_triangles);
        triangles.position(0);

        // 创建纹理坐标缓冲
        tvertices = ByteBuffer.allocateDirect(data_tvertices.length * 4);
        tvertices.order(ByteOrder.nativeOrder());
        tvertices.asFloatBuffer().put(data_tvertices);
        tvertices.position(0);
    }

    /**
     * The texture pointer
     */
    private int[] textures = new int[1];


    public void draw(GL10 gl) {
        // 清除颜色缓冲

        // 设置当前矩阵堆栈为模型堆栈，并重置堆栈，
        // 即随后的矩阵操作将应用到要绘制的模型上
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(mX, mY, -6.0f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        // 设置正面
        gl.glFrontFace(GL10.GL_CW);
        // 设置顶点数组指针为 ByteBuffer 对象 vertices
        // 第一个参数为每个顶点包含的数据长度（ ）
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, tvertices);
        // 绑定纹理
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mId);
        // 绘制 triangles 表示的三角形
        gl.glDrawElements(GL10.GL_TRIANGLES, triangles.remaining(),
                GL10.GL_UNSIGNED_BYTE, triangles);
        // 禁用顶点、纹理坐标数组
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//        gl.glDisable(GL10.GL_TEXTURE_2D);
    }
}
