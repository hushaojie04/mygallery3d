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
            // ��ͼƬ��Դ��
            bitmapStream = view.getContext().getResources().openRawResource(
                    mResourceId);
            // ����ͼƬ���� Bitmap ʵ��
            bitmap = BitmapFactory.decodeStream(bitmapStream);

            // ����һ��������󣬲�����ID���浽��Ա���� texture ��
            int[] textures = new int[1];
            gl.glGenTextures(1, textures, 0);
            mId = textures[0];
            // �����ɵĿ�����󶨵���ǰ2D����ͨ��
            gl.glBindTexture(GL10.GL_TEXTURE_2D, mId);

            // ����2D����ͨ����ǰ�󶨵����������
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                    GL10.GL_NEAREST);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                    GL10.GL_LINEAR);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
                    GL10.GL_REPEAT);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
                    GL10.GL_REPEAT);

            // ��bitmapӦ�õ�2D����ͨ����ǰ�󶨵�������
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        } finally {
            // �ͷ���Դ
            // BTW: �ڴ� android ����֧�� Java �µ� try-with-resource �﷨

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
        // �������㻺��
        vertices = ByteBuffer.allocateDirect(data_vertices.length * 4);
        vertices.order(ByteOrder.nativeOrder());
        vertices.asFloatBuffer().put(data_vertices);
        vertices.position(0);

        // ������������
        triangles = ByteBuffer.allocateDirect(data_triangles.length * 2);
        triangles.put(data_triangles);
        triangles.position(0);

        // �����������껺��
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
        // �����ɫ����

        // ���õ�ǰ�����ջΪģ�Ͷ�ջ�������ö�ջ��
        // �����ľ��������Ӧ�õ�Ҫ���Ƶ�ģ����
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(mX, mY, -6.0f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        // ��������
        gl.glFrontFace(GL10.GL_CW);
        // ���ö�������ָ��Ϊ ByteBuffer ���� vertices
        // ��һ������Ϊÿ��������������ݳ��ȣ� ��
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, tvertices);
        // ������
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mId);
        // ���� triangles ��ʾ��������
        gl.glDrawElements(GL10.GL_TRIANGLES, triangles.remaining(),
                GL10.GL_UNSIGNED_BYTE, triangles);
        // ���ö��㡢������������
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//        gl.glDisable(GL10.GL_TEXTURE_2D);
    }
}
